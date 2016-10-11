package datastructures;

import javafx.scene.paint.Color;

/**
 * Created by Edison on 10/10/16.
 */
public class Entity {

    Color color;
    String name;

    public Entity(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
