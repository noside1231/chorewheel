import datastructures.Entity;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ChoreArc {
    private double x, y;
    private double w, h;
    private double start, extent;
    private Color color;
    private String name;
    private double dispTheta;
    private Entity entity;

    public ChoreArc(double x, double y, double w, double h, double start, double extent, Entity entity) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.start = start;
        this.extent = extent;
        this.color = entity.getColor();
        this.name = entity.getName();
        this.entity = entity;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Entity getEntity() { return entity; }

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
        g.setFill(color);
        g.fillArc(x, y, w, h, (int)(start + dispTheta), (int)extent, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeArc(x, y, w, h, (int)(start + dispTheta), (int)extent, ArcType.ROUND);
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }

    public boolean intersects() {
        return 180 >= (start+dispTheta)%360 && 180 <= (start+extent+dispTheta)%360;
    }
}
