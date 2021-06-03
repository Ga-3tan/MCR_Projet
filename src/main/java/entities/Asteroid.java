package entities;

import java.awt.*;

public class Asteroid extends Prototype{
    public Asteroid(String spritePath, Point position, Point movementVector, Dimension size) {
        super(spritePath, position, movementVector, size);
    }

    @Override
    public void move() {

    }

    @Override
    public Prototype clone() {
        return null;
    }
}
