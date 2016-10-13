import static java.lang.Math.*;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ChorePin {
    private double theta = 0;
    private double angularVel = 0;
    private double angularAcc = 0;
    private double angularAccVal = PI/300;
    private boolean isHit = false;

    public ChorePin() {
        hit(PI / 20);
    }

    public void hit(double angularVel) {
        if (abs(theta) < PI / 4) {
            isHit = true;
            this.angularVel = angularVel;
        }
    }

    private void updatePhysics() {
        if (isHit) {
            theta += angularVel;
            if (theta % (PI) > 0.00) {
                angularAcc = -angularAccVal;
            } else {
                angularAcc = angularAccVal;
            }
            angularVel += angularAcc;
            damp();
            if (energy() < .4) {
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

    public Point2D getColorSamplePoint() {
        return new Point2D(80*ChoreWheelRun.scale, 40*ChoreWheelRun.scale);
    }

    public void paint(GraphicsContext g) {
        updatePhysics();
        g.save();
        {
            Affine trans = new Affine();
            trans.append(Affine.translate(80 * ChoreWheelRun.scale, 5.5 * ChoreWheelRun.scale));
            trans.append(Affine.scale(10, 10));
            trans.append(Affine.rotate(theta * 180/PI, 0, 0));
            g.setTransform(trans);

            g.setFill(Color.GRAY);
            g.setStroke(Color.BLACK);
            g.setLineWidth(0.2);
            double[] xs = { 0, 1, 1, 1, 2, 0, -2, -1, -1, -1, 0 };
            double[] ys = { 0, 0, 2, 4, 4, 8,  4,  4,  2,  0, 0 };
            g.fillPolygon(xs, ys, 11);
            g.strokePolygon(xs, ys, 11);
        }
        g.restore();

        double r = 2*ChoreWheelRun.scale;
        g.setFill(Color.LIGHTGRAY);
        g.fillArc(80*ChoreWheelRun.scale - r/2, 5.5*ChoreWheelRun.scale - r/2, r, r, 0, 360, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.strokeArc(80*ChoreWheelRun.scale - r/2, 5.5*ChoreWheelRun.scale - r/2, r, r, 0, 360, ArcType.OPEN);
    }
}
