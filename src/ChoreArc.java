import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.StrictMath.sqrt;

public class ChoreArc {
    private int x, y;
    private int w, h;
    private double start, extent;
    private Color color;
    private String name;
    private double dispTheta;

    public ChoreArc(int x, int y, int w, int h, double start, double extent, Color color, String name) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.start = start;
        this.extent = extent;
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Arc2D.Double getShape() {
        return new Arc2D.Double(x, y, w, h, start + dispTheta, extent, Arc2D.PIE);
    }

    public Line2D.Double getLeftLine() {
        Point2D point = getPointOnCircle(start);
        return new Line2D.Double(point.getX(), point.getY(), point.getX(), point.getY());
    }

    private Point2D.Double getPointOnCircle(double theta) {
        return new Point2D.Double(getRadius()*cos(theta*PI/180) + getCenterX(), getRadius()*sin(theta*PI/180) + getCenterY());
    }

    private int getCenterX() {
        return x + w/2;
    }

    private int getCenterY() {
        return y + h/2;
    }

    private int getRadius() {
        return w/2;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillArc(x, y, w, h, (int)(start + dispTheta), (int)extent);
        g.setColor(Color.BLACK);
        ((Graphics2D)g).setStroke(new BasicStroke(8));
        g.drawArc(x, y, w, h, (int)(start + dispTheta), (int)extent);
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }
}
