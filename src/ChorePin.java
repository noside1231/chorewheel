import java.awt.*;
import java.awt.geom.AffineTransform;
import static java.lang.Math.*;

public class ChorePin {
    private Shape curShape;
    private Shape origShape;
    private double theta = 0;
    private double angularVel = 0;
    private double angularAcc = 0;
    private double angularAccVal = PI/300;
    private boolean isHit = false;

    public ChorePin() {
        int[] x = new int[] { 0, 1, 1, 1, 2, 0, -2, -1, -1, -1, 0 };
        int[] y = new int[] { 0, 0, 2, 4, 4, 8,  4,  4,  2,  0, 0 };
        origShape = new Polygon(x, y, 11);
        hit(PI/20);
    }

    public void hit(double angularVel) {
        if(abs(theta) < PI/4) {
            isHit = true;
            this.angularVel = angularVel;
        }
    }

    private void updatePhysics() {
        if(isHit) {
            theta += angularVel;
            if (theta%(PI) > 0.00) {
                angularAcc = -angularAccVal;
            } else {
                angularAcc = angularAccVal;
            }
            angularVel += angularAcc;
            damp();
            if(energy() < .3) {
                isHit = false;
            }
        }
    }

    private void damp() {
        angularVel /= 1.05;
    }

    private double energy() {
        return 100*abs(theta%(2*PI)) + 150*abs(angularVel)*abs(angularVel);
    }

    public Point getXY() {
        return new Point((int) (curShape.getBounds().x  + .5*curShape.getBounds().width), curShape.getBounds().y + curShape.getBounds().height);
    }

    public Point getColorSamplePoint() {
        return new Point(800, 400);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        updatePhysics();
        AffineTransform trans = new AffineTransform();
        trans.concatenate(AffineTransform.getTranslateInstance(800, 55));
        trans.concatenate(AffineTransform.getScaleInstance(35, 35));
        trans.concatenate(AffineTransform.getRotateInstance(theta));
        curShape = trans.createTransformedShape(origShape);

        g2.setColor(Color.GRAY);
        g2.fill(curShape);
        g2.setColor(Color.BLACK);
        g2.draw(curShape);

        int r = 20;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillArc(800 - r/2, 55 - r/2, r, r, 0, 360);
        g2.setColor(Color.BLACK);
        g2.drawArc(800 - r/2, 55 - r/2, r, r, 0, 360);
    }
}
