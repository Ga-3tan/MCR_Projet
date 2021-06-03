package entities;

import java.awt.*;

public class Shot extends Prototype{
    private Ship friendly = null;

    public Shot(String spritePath, Point position, Point movementVector, Dimension size, Ship friendly) {
        super(spritePath, position, movementVector, size);
        this.friendly = friendly;
    }

    @Override
    public void move() {

    }

    @Override
    public Prototype clone() {
        return null;
    }
}
