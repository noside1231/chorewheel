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
    private Polygon origShape;
    private double theta = 0;
    private double angularVel = 0;
    private double angularAcc = 0;
    private double angularAccVal = PI/300;
    private boolean isHit = false;

    public ChorePin() {
//        int[] x = new int[] { 0, 1, 1, 1, 2, 0, -2, -1, -1, -1, 0 };
//        int[] y = new int[] { 0, 0, 2, 4, 4, 8,  4,  4,  2,  0, 0 };
        origShape = new Polygon(0,0,1,0,1,2,1,4,2,4,0,8,-2,4,-1,4,-1,2,-1,0,0,0);

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

    public Point2D getColorSamplePoint() {
        return new Point2D(800, 400);
    }

    public void paint(GraphicsContext g2) {

        updatePhysics();
        Affine trans = new Affine();
        trans.append(Affine.translate(800, 55));
        trans.append(Affine.scale(35, 35));
        trans.append(Affine.rotate(theta,0,0));
        origShape.getTransforms().add(trans);



        g2.setFill(Color.BLACK);

        ObservableList<Double> l = origShape.getPoints(); //fixme

        double[] xpoints = new double[11];
        double[] ypoints = new double[11];

        for(int i = 0, xI = 0, yI = 0; i < l.size(); i++) {
            if(i % 2 == 0) {
                xpoints[xI] = l.get(i);
                xI++;
            }
            else {
                ypoints[yI] = l.get(i);
                yI++;
            }
        }

        g2.fillPolygon(xpoints, ypoints, 11);

        int r = 20;
        g2.setFill(Color.LIGHTGRAY);
        g2.fillArc(800 - r/2, 55 - r/2, r, r, 0, 360, ArcType.ROUND);
        g2.setStroke(Color.BLACK);
        g2.strokeArc(800 - r/2, 55 - r/2, r, r, 0, 360, ArcType.ROUND);
    }
}
