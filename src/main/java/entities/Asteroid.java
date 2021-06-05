package entities;

import java.awt.*;

public class Asteroid extends GameObject {
    public Asteroid(String spritePath, Point position, Point movementVector, Dimension size) {
        super(spritePath, position, movementVector, size);
    }

    public Asteroid(Asteroid copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
    }

    @Override
    public GameObject clone() {
        return new Asteroid(this);
    }
}
