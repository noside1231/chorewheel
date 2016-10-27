import com.sun.javafx.tk.Toolkit;
import datastructures.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;

public class ChoreArc {
    private ArcSize size;
    public enum ArcSize {
        SMALL,
        BIG;
    }
    private Entity entity;
    private double x, y;
    private double w, h;
    private double start, extent;
    private Color color;
    private String name;
    private double dispTheta;
    private double textDisp;
    private double intersectDisp;

    public ChoreArc(double x, double y, double start, double extent, Entity entity, ArcSize size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.start = start;
        this.extent = extent;
        this.color = entity.getColor();
        this.name = entity.getName();
        this.entity = entity;
        if (size == ArcSize.SMALL) {
            w = 70 * ChoreWheelRun.scale;
            h = 70 * ChoreWheelRun.scale;
        } else {
            w = 150 * ChoreWheelRun.scale;
            h = 150 * ChoreWheelRun.scale;
        }
        long totalArcs = Math.round(360 / extent);
        intersectDisp = (10-totalArcs) * extent/4;
        textDisp = (10-totalArcs + 2) * extent/4;
    }

    public Color getColor() {
        return color;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public Arc getShape() {
        return new Arc(x + w / 2, y + h / 2, w / 2, h / 2, start + dispTheta, extent);
    }

    public double getTextStartAngle() {
        return start + dispTheta%360 + textDisp;
    }

    public double getTextEndAngle() {
        return start + dispTheta%360 + textDisp + extent;
    }

    public double getIntersectStartAngle() {
        return start + dispTheta%360 + intersectDisp;
    }

    public double getIntersectEndAngle() {
        return start + dispTheta%360 + intersectDisp + extent;
    }

    public double getTextX(String str) {
        double width = Toolkit.getToolkit().getFontLoader().computeStringWidth(str, new Font(16));
        return -width / 2;
    }

    public double getTextY(String str) {
        float height = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(16)).getLineHeight();
        if (size == ArcSize.BIG) {
            return 50 + height/2;
        } else {
            return 190;
        }
    }

    private double getCenterX() {
        return x + w / 2;
    }

    private double getCenterY() {
        return y + h / 2;
    }

    private double getRadius() {
        return w / 2;
    }

    public void paint(GraphicsContext g) {
        Affine trans = new Affine();
        trans.append(Affine.rotate(-start - dispTheta, getCenterX(), getCenterY()));
        g.setTransform(trans);

        g.setFill(color);
        g.fillArc(x, y, w, h, -extent / 2, (int) extent, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeArc(x, y, w, h, -extent / 2, (int) extent, ArcType.ROUND);

        g.setTransform(new Affine());
    }

    public void paintName(GraphicsContext g) {
        Affine trans = new Affine();
        trans.append(Affine.rotate(-(getTextStartAngle()), getCenterX(), getCenterY()));
        double x1 = -4 + x + w/2;
        double y1 = getTextY(name) + h/8;
        trans.append(Affine.rotate(90, x1, y1));
        g.setTransform(trans);

        g.setFill(Color.WHITE);
        g.setFont(new Font(16));
        g.strokeText(name, getTextX(name) + x + w / 2, getTextY(name) + h / 8, 80);
        g.fillText(name, getTextX(name) + x + w / 2, getTextY(name) + h / 8, 80);

        g.setTransform(new Affine());
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }

    public boolean intersects() {
        return getIntersectStartAngle() < 360 && getIntersectEndAngle() >= 360 ||
                getIntersectStartAngle() < 720 && getIntersectEndAngle() >= 720;
    }

    public boolean intersectsSmall() {
        return getIntersectStartAngle() < 0 && getIntersectEndAngle() >= 0 ||
                getIntersectStartAngle() < 360 && getIntersectEndAngle() >= 360 ||
                getIntersectStartAngle() < 720 && getIntersectEndAngle() >= 720;
    }
}
