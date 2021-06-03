package entities;

import java.awt.*;

public class Asteroid extends Prototype{
    public Asteroid(Image sprite, Point position, Point movementVector, Dimension size) {
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
