import datastructures.Entity;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ChoreArc {
    private ArcSize size ;
    public enum ArcSize {
        BIG,
        SMALL
    }
    private double x, y;
    private double w, h;
    private double start, extent;
    private Color color;
    private String name;
    private double dispTheta;

    public ChoreArc(double x, double y, double start, double extent, Entity entity, ArcSize size) {
        this.x = x; this.y = y;
        this.size = size;
        this.start = start;
        this.extent = extent;
        this.color = entity.getColor();
        this.name = entity.getName();
        if(size == ArcSize.SMALL) {
            w = 70 * ChoreWheelRun.scale;
            h = 70 * ChoreWheelRun.scale;
        } else {
            w = 150 * ChoreWheelRun.scale;
            h = 150 * ChoreWheelRun.scale;
        }
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Arc getShape() {
        return new Arc(x + w/2, y + h/2, w/2, h/2, start + dispTheta, extent);
    }

    public Line getLeftLine() {
        Point2D point = getPointOnCircle(start);
        return new Line(point.getX(), point.getY(), point.getX(), point.getY());
    }

    private Point2D getPointOnCircle(double theta) {
        return new Point2D(getRadius()*cos(theta*PI/180) + getCenterX(), getRadius()*sin(theta*PI/180) + getCenterY());
    }

    private double getCenterX() {
        return x + w/2;
    }

    private double getCenterY() {
        return y + h/2;
    }

    private double getRadius() {
        return w/2;
    }

    public void paint(GraphicsContext g) {
        g.save();

        Affine trans = new Affine();
        trans.append(Affine.translate(x, y));
        trans.append(Affine.rotate(start + dispTheta, w/2, h/2));
        g.setTransform(trans);

        g.setFill(color);
        g.fillArc(0, 0, w, h, -extent/2, (int)extent, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeArc(0,0, w, h, -extent/2, (int)extent, ArcType.ROUND);

        g.setFont(new Font(12));
        if(size == ArcSize.SMALL) {
            g.strokeText(name, w / 2 - name.length()*1.5, 30, 60);
            g.fillText(name, w / 2 - name.length()*1.5, 30, 60);
        } else {
            g.strokeText(name, w / 2 - name.length() * 5, 30, 60);
            g.fillText(name, w / 2 - name.length()* 5, 30, 60);
        }
        g.restore();
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }

    public boolean intersects() {
        return 180 >= (start+dispTheta)%360 && 180 <= (start+extent+dispTheta)%360;
    }
}
