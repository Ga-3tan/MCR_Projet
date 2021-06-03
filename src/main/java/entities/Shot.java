package entities;

import java.awt.*;

public class Shot extends Prototype{
    public Shot(Image sprite, Point position, Point movementVector, Dimension size) {
        super(sprite, position, movementVector, size);
    }

    @Override
    public void move() {

    }

    @Override
    public Prototype clone() {
        return null;
    }
}
