import datastructures.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.random;

public class ChoreWheel extends AutoScalingStackPane {
    private ChoreWheelRun run;
    private ArrayList<ChoreArc> choreArcs;
    private ArrayList<ChoreArc> choreArcsSmall;
    private ChorePin pin;
    private double theta = 0;
    private double angularVel = 0;
    private double angularAcc = -.05;
    private double thetaSmall = 0;
    private double angularVelSmall = -20;
    private double angularAccSmall = .05;
    private boolean wheelIsSpinning;
    private boolean hasBeenSpun = false;
    private ChoreArc pointedAtBig;
    private ChoreArc pointedAtSmall;
    private ArrayList<Entity> names;
    private ArrayList<Entity> chores;
    PrintWriter writer;

    public ChoreWheel(ChoreWheelRun choreWheelRun) throws FileNotFoundException {
        setAutoScale(AutoScale.FILL);
        choreArcs = new ArrayList<>();
        choreArcsSmall = new ArrayList<>();
        pin = new ChorePin();
        run = choreWheelRun;
        PrintWriter clearer = new PrintWriter(new File(getClass().getResource("choreToName.txt").toExternalForm().substring(5)));
        clearer.write("");
        clearer.flush();
        clearer.close();
        writer = new PrintWriter(new File(getClass().getResource("choreToName.txt").toExternalForm().substring(5)));
    }

    protected void populateChores() {
        choreArcs.clear();
        choreArcsSmall.clear();

        double largeExtent = 360. / chores.size();
        double x1 = 5 * ChoreWheelRun.scale;
        double y1 = 20 * ChoreWheelRun.scale;
        double start = 5;
        for (int i = 0; i < chores.size(); i++) {
            choreArcs.add(new ChoreArc(x1, y1, start, largeExtent, chores.get(i), ChoreArc.ArcSize.BIG));
            start += largeExtent;
        }

        double smallExtent = 360. / names.size();
        double x2 = 45 * ChoreWheelRun.scale;
        double y2 = 60 * ChoreWheelRun.scale;
        start = 20;
        for (int i = 0; i < names.size(); i++) {
            choreArcsSmall.add(new ChoreArc(x2, y2, start, smallExtent, names.get(i), ChoreArc.ArcSize.SMALL));
            start += smallExtent;
        }
    }

    public void render(GraphicsContext g) {
        for (ChoreArc choreArc : choreArcs) {
            choreArc.paint(g);
        }
        for (ChoreArc choreArc : choreArcs) {
            choreArc.paintName(g);
        }
        for (ChoreArc choreArc : choreArcsSmall) {
            choreArc.paint(g);
        }
        for (ChoreArc choreArc : choreArcsSmall) {
            choreArc.paintName(g);
        }
        pin.paint(g);
    }

    public void update() throws FileNotFoundException {
        updatePhysics();
        applyRotation();
        checkCollision();

        if (hasBeenSpun) {
            if ((int) angularVel == 0 && (int) angularVelSmall == 0 && !pin.isHit()) {
                check();
                System.out.println("Landed on: " + pointedAtBig.getName());
                System.out.println("Landed on: " + pointedAtSmall.getName());
                hasBeenSpun = false;

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                chores.remove(pointedAtBig.getEntity());
                names.remove(pointedAtSmall.getEntity());

                writer.print(pointedAtSmall.getEntity().getName() + " ");
                writer.print(pointedAtBig.getEntity().getName());
                writer.println();
                writer.flush();

                choreArcs.clear();
                populateChores();
                spin();
            }
        }
    }

    private void check() {
        if(choreArcs.size() == 1) {
            pointedAtBig = choreArcs.get(0);
        } else {
            for (ChoreArc arc : choreArcs) {
//                System.out.println(arc.getName() + " START: " + arc.getIntersectStartAngle() + "   END: " + arc.getIntersectEndAngle());
                if (arc.intersects()) {
                    if (pointedAtBig == null) {
                        pointedAtBig = arc;
                    } else if (!pointedAtBig.equals(arc)) {
                        pointedAtBig = arc;
                    }
                }
            }
        }
        if(choreArcsSmall.size() == 1) {
            pointedAtSmall = choreArcsSmall.get(0);
        } else {
            for (ChoreArc arc : choreArcsSmall) {
//                System.out.println(arc.getName() + " START: " + arc.getIntersectStartAngle() + "   END: " + arc.getIntersectEndAngle());
                if (arc.intersectsSmall()) {
                    if (pointedAtSmall == null) {
                        pointedAtSmall = arc;
                    } else if (!pointedAtSmall.equals(arc)) {
                        pointedAtSmall = arc;
                    }
                }
            }
        }
    }

    private void checkCollision() {
        for (ChoreArc arc : choreArcs) {
            if (arc.intersects()) {
                if (pointedAtBig == null) {
                    pointedAtBig = arc;
                } else if (!pointedAtBig.equals(arc)) {
                    pin.hit(PI / 20);
                    pointedAtBig = arc;
                }
            }
        }
        for (ChoreArc arc : choreArcsSmall) {
            if (arc.intersectsSmall()) {
                if (pointedAtSmall == null) {
                    pointedAtSmall = arc;
                } else if (!pointedAtSmall.equals(arc)) {
                    pointedAtSmall = arc;
                }
            }
        }
    }

    private void updatePhysics() {
        if (angularVel >= 0.1 && wheelIsSpinning) {
            angularVel += angularAcc;
            theta += angularVel;
        }
        if (angularVelSmall < -0.1 && wheelIsSpinning) {
            angularVelSmall += angularAccSmall;
            thetaSmall += angularVelSmall;
        }
    }

    private void spin() {
        wheelIsSpinning = true;
        hasBeenSpun = true;
        angularVel = 15 + random() * 15;
        angularAcc = -.05;
        angularVelSmall = -15 - random() * 15;
        angularAccSmall = .05;
    }

    private void applyRotation() {
        for (ChoreArc choreArc : choreArcs) {
            choreArc.setDispTheta(theta);
        }
        for (ChoreArc choreArc : choreArcsSmall) {
            choreArc.setDispTheta(thetaSmall);
        }
    }

    public void addEventHandlers() {
        run.getScene().setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.SPACE)) {
                spin();
            }
        });
    }

    public void runGame() {
        run.getGlassG().clearRect(0, 0, 1920, 1080);
        rescale();
        render(run.getGlassG());
        try {
            update();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setNames() {
        names = run.getConfig().getNames();
    }

    public void setChores() {
        chores = run.getConfig().getChores();
    }

    public ArrayList<Entity> getChores() {
        return chores;
    }

    public ArrayList<Entity> getNames() {
        return names;
    }
}
