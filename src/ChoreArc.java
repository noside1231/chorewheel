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
        return -width/2;
    }

    public double getTextY(String str) {
        if(size == ArcSize.BIG) {
            return 50;
        } else {
            return 200;
        }
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
        Affine trans = new Affine();
        trans.append(Affine.rotate(-start - dispTheta, getCenterX(), getCenterY()));
        g.setTransform(trans);

        g.setFill(color);
        g.fillArc(x, y, w, h, -extent/2, (int)extent, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeArc(x, y, w, h, -extent/2, (int)extent, ArcType.ROUND);

        g.setTransform(new Affine());
    }

    public void paintName(GraphicsContext g) {
        Affine trans = new Affine();
        if((Math.round(360/extent))%2 == 0) {
            trans.append(Affine.rotate(-(start + dispTheta + 360/4), getCenterX(), getCenterY()));
        } else if((Math.round(360/extent))%3 == 0){
            trans.append(Affine.rotate(-(start + dispTheta - 360/4), getCenterX(), getCenterY()));
        } else if((Math.round(360/extent))%2 != 0){
            trans.append(Affine.rotate(-(start + dispTheta -360/4), getCenterX(), getCenterY()));
        }
        g.setTransform(trans);

        g.setFont(new Font(12));
        g.strokeText(name, getTextX(name) + x + w/2, getTextY(name) + h/8, 60);
        g.fillText(name, getTextX(name) + x + w/2, getTextY(name) + h/8, 60);

        g.setTransform(new Affine());
    }

    public void setDispTheta(double dispTheta) {
        this.dispTheta = dispTheta;
    }

    public boolean intersects() {
        return start+dispTheta%360 < 360 && start+extent+dispTheta%360 > 360;
    }

}
