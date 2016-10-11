import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;

import java.util.ArrayList;
import static java.lang.Math.PI;
import static java.lang.Math.random;

public class ChoreWheel extends AutoScalingStackPane {
    private ChoreWheelRun run;
    private ArrayList<ChoreArc> choreArcs;
    private ArrayList<ChoreArc> choreArcsSmall;
    private ChorePin pin;
    private String[] roommates = { "Alex", "Adrianna", "Ariana", "Crystal", "Tim", "Netty", "Edison", "Mathew" };
    private double theta = 0;
    private double angularVel = 20;
    private double angularAcc = -.05;
    private double thetaSmall = 0;
    private double angularVelSmall = -20;
    private double angularAccSmall = .05;
    private boolean wheelIsSpinning;
    private ChoreArc pointedAt;
    private double angle = 0;

    public ChoreWheel(ChoreWheelRun choreWheelRun) {
        setAutoScale(AutoScale.FILL);
        choreArcs = new ArrayList<>();
        choreArcsSmall = new ArrayList<>();
        pin = new ChorePin();
        run = choreWheelRun;
        populateChores();
    }

    private void populateChores() { //fixme
        double x1 = 5 * ChoreWheelRun.scale;
        double y1 = 20 * ChoreWheelRun.scale;
        double r1 = 150 * ChoreWheelRun.scale;
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 5, 45, Color.GREEN, "wash1"));
        ChoreArc arc = new ChoreArc(x1, y1, r1, r1, 50, 45, Color.RED, "wash2");
        pointedAt = arc;
        choreArcs.add(arc);
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 95, 45, Color.BLUE, "wash3"));
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 140, 45, Color.CYAN, "wash4"));
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 185, 45, Color.PINK, "wash5"));
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 230, 45, Color.ORANGE, "wash6"));
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 275, 45, Color.YELLOW, "wash7"));
        choreArcs.add(new ChoreArc(x1, y1, r1, r1, 320, 45, Color.MAGENTA, "wash8"));


        double x2 = 45 * ChoreWheelRun.scale;
        double y2 = 60 * ChoreWheelRun.scale;
        double r2 = 70 * ChoreWheelRun.scale;
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 5 + 15, 45, Color.GREEN, roommates[0]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 50 + 15, 45, Color.RED, roommates[1]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 95 + 15, 45, Color.BLUE, roommates[2]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 140 + 15, 45, Color.CYAN, roommates[3]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 185 + 15, 45, Color.PINK, roommates[4]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 230 + 15, 45, Color.ORANGE, roommates[5]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 275 + 15, 45, Color.YELLOW, roommates[6]));
        choreArcsSmall.add(new ChoreArc(x2, y2, r2, r2, 320 + 15, 45, Color.MAGENTA, roommates[7]));
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
    }

    private void checkCollision() {
        for (ChoreArc arc : choreArcs) {
            if(arc.intersects(angle)) {
                if(!pointedAt.equals(arc)) {
                    pin.hit(PI/20);
                    pointedAt = arc;
                }
            }
        }
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
}
