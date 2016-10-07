//import java.awt.*;
//import java.awt.geom.AffineTransform;

import static java.lang.Math.*;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ChorePin {
    private double theta = 45;
    private double angularVel = 0;
    private double angularAcc = 0;
    private double angularAccVal = PI / 300 * 180;
    private boolean isHit = true;

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
            if (energy() < .3) {
                isHit = false;
            }
        }
    }

    private void damp() {
        angularVel /= 1.05;
    }

    private double energy() {
        return 100 * abs(theta % (2 * PI)) + 150 * abs(angularVel) * abs(angularVel);
    }

    public Point2D getColorSamplePoint() {
        return new Point2D(80*ChoreWheelRun.scale, 40*ChoreWheelRun.scale);
    }

    public void paint(GraphicsContext g2) {
        updatePhysics();

        g2.save();
        {
            Affine trans = new Affine();
            trans.append(Affine.translate(80 * ChoreWheelRun.scale, 5.5 * ChoreWheelRun.scale));
            trans.append(Affine.scale(10, 10));
            trans.append(Affine.rotate(theta, 0, 0));
            g2.setTransform(trans);

            g2.setFill(Color.GRAY);
            g2.setStroke(Color.BLACK);
            g2.setLineWidth(0.2);
            double[] xs = {0, 1, 1, 1, 2, 0, -2, -1, -1, -1, 0};
            double[] ys = {0, 0, 2, 4, 4, 8,  4,  4,  2,  0, 0};
            g2.fillPolygon(xs, ys, 11);
            g2.strokePolygon(xs, ys, 11);
        }
        g2.restore();

        double r = 2*ChoreWheelRun.scale;
        g2.setFill(Color.LIGHTGRAY);
        g2.fillArc(80*ChoreWheelRun.scale - r / 2, 5.5*ChoreWheelRun.scale - r / 2, r, r, 0, 360, ArcType.ROUND);
        g2.setStroke(Color.BLACK);
        g2.strokeArc(80*ChoreWheelRun.scale - r / 2, 5.5*ChoreWheelRun.scale - r / 2, r, r, 0, 360, ArcType.OPEN);
    }
}
