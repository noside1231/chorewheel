import datastructures.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
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
    private ChoreArc pointedAt;

    private ArrayList<Entity> names;
    private ArrayList<Entity> chores;

    public ChoreWheel(ChoreWheelRun choreWheelRun) {
        setAutoScale(AutoScale.FILL);
        choreArcs = new ArrayList<>();
        choreArcsSmall = new ArrayList<>();
        pin = new ChorePin();
        run = choreWheelRun;
    }

    protected void populateChores() {

            double largeExtent = 360. / chores.size();
            double smallExtent = 360. / names.size();

        double x1 = 5 * ChoreWheelRun.scale;
        double y1 = 20 * ChoreWheelRun.scale;

        double start = 5;
        for (int i = 0; i < chores.size(); i++) {
            choreArcs.add(new ChoreArc(x1, y1, start, largeExtent, chores.get(i), ChoreArc.ArcSize.BIG));
            start += largeExtent;
        }

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
        for (ChoreArc choreArc : choreArcsSmall) {
            choreArc.paint(g);
        }
        pin.paint(g);
    }

    public void update() {
        updatePhysics();
        applyRotation();
        checkCollision();

//        System.out.println("ANGVEL: " + angularVel);

        if(hasBeenSpun == true) {

            if( (int)angularVel == 0) {

                System.out.println("Landed on: " + pointedAt.getName());
                hasBeenSpun = false;

                chores.remove(pointedAt.getEntity());
                choreArcs.clear();
                System.out.println(choreArcs);

                populateChores();

                System.out.println(choreArcs);

                spin();

            }

        }



    }

    private void checkCollision() {

        for (ChoreArc arc : choreArcs) {
             if(arc.intersects()) {
                 if (pointedAt == null) {
                     pointedAt = arc;
                 } else if(!pointedAt.equals(arc)) {
                     pin.hit(PI/20);
                     pointedAt = arc;

                 }
             }
        }
    }

    public void removeArc() {

    }

    private void updatePhysics() {
        if(angularVel >= 0.1 && wheelIsSpinning) {
            angularVel += angularAcc;
            theta += angularVel;
        }
        if(angularVelSmall < -0.1 && wheelIsSpinning) {
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

    public void runGame(double t) {
        run.getGlassG().clearRect(0, 0, 1920, 1080);
        rescale();
        render(run.getGlassG());
        update();
    }

    public void setNames() {
        names = run.getConfig().getNames();
    }

    public void setChores() {
        chores = run.getConfig().getChores();
    }

    public boolean isWheelIsSpinning() {
        return wheelIsSpinning;
    }

    public ArrayList<Entity> getChores() {
        return chores;
    }

    public ArrayList<Entity> getNames() {
        return names;
    }
}
