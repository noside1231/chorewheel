import com.sun.glass.ui.Size;
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
    public ChoreArc(double x, double y, double start, double extent, Entity entity, ArcSize size) {
        this.x = x; this.y = y;
        this.size = size;
        this.start = start;
        this.extent = extent;
        this.color = entity.getColor();
        this.name = entity.getName();
        this.entity = entity;
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

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public Arc getShape() {
        return new Arc(x + w/2, y + h/2, w/2, h/2, start + dispTheta, extent);
    }

    public double getTextX(String str) {
        double width = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(str, new Font(12));
        return getCenterXBeforeDrawn() - width/2;
    }

    public double getTextY(String str) {
        float height = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(12)).getLineHeight();
        return getCenterYBeforeDrawn() - getRadius()*sin(extent/2) + height/2;
    }

    private double getCenterX() {
        return x + w/2;
    }

    public double getCenterXBeforeDrawn() {
        return w/2;
    }

    private double getCenterY() {
        return y + h/2;
    }

    public double getCenterYBeforeDrawn() {
        return h/2;
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
        g.strokeArc(0, 0, w, h, -extent/2, (int)extent, ArcType.ROUND);

        g.setFont(new Font(12));
        g.strokeText(name, getTextX(name), getTextY(name), 60);
        g.fillText(name, getTextX(name), getTextY(name), 60);
        g.restore();
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }

    public boolean intersects() {
        return 180 >= (start+dispTheta)%360 && 180 <= (start+extent+dispTheta)%360;
    }
}
