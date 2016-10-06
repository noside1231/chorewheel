import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

import static java.awt.Font.getFont;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.StrictMath.sqrt;

public class ChoreArc {
    private Color color;
    private String name;
    private double dispTheta;

    public ChoreArc(double centerX, double centerY, , Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Arc getShape() {
        return new Arc();
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

//        g.setColor(Color.BLACK);
//        Font f = getFont("").deriveFont(Font.BOLD, 70);
//        GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), "Hello"); // fixme
//        Shape s = v.getOutline();

//        Point p = getPointOnCircle(th);
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }
}
