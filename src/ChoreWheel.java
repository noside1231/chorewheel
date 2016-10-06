import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.random;

public class ChoreWheel {
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
    private Color color;

    public ChoreWheel(ChoreWheelRun choreWheelRun, int width, int height) {
        choreArcs = new ArrayList<>();
        choreArcsSmall = new ArrayList<>();
        pin = new ChorePin();
        run = choreWheelRun;
        populateChores();
        color = Color.RED;

        JFrame f = new JFrame("Chore Wheel ap#1022");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                run.endLoop();
            }
        });
        f.setBounds(100, 100, width, height);
        f.setContentPane(run);
        f.setVisible(true);
        f.setResizable(false);
    }

    private void populateChores() { //fixme
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 5, 45, Color.green, "wash1"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 50, 45, Color.red, "wash2"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 95, 45, Color.blue, "wash3"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 140, 45, Color.CYAN, "wash4"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 185, 45, Color.PINK, "wash5"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 230, 45, Color.ORANGE, "wash6"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 275, 45, Color.YELLOW, "wash7"));
        choreArcs.add(new ChoreArc(50, 200, 1500, 1500, 320, 45, Color.MAGENTA, "wash8"));

        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 5 + 15, 45, Color.green, roommates[0]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 50 + 15, 45, Color.red, roommates[1]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 95 + 15, 45, Color.blue, roommates[2]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 140 + 15, 45, Color.CYAN, roommates[3]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 185 + 15, 45, Color.PINK, roommates[4]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 230 + 15, 45, Color.ORANGE, roommates[5]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 275 + 15, 45, Color.YELLOW, roommates[6]));
        choreArcsSmall.add(new ChoreArc(50 + 400, 200 + 400, 700, 700, 320 + 15, 45, Color.MAGENTA, roommates[7]));
    }

    public void render(Graphics g) {
        for (ChoreArc choreArc : choreArcs) {
            choreArc.paint(g);
        }
        for (ChoreArc choreArc : choreArcsSmall) {
            choreArc.paint(g);
        }
        pin.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(800, 400, 10, 10);

//        g.
    }

    public void update() {
        updatePhysics();
        applyRotation();
        checkCollision();
    }

    private void checkCollision() {
        for (ChoreArc choreArc : choreArcs) {
            if(choreArc.getShape().contains(pin.getColorSamplePoint())) {
                if(!color.equals(choreArc.getColor())) {
                    pin.hit(PI/30);
                    color = choreArc.getColor();
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

    public void processInput(boolean[] keys) {
        if(keys[KeyEvent.VK_SPACE]) {
            spin();
        }
    }
}
