package entities;

import java.awt.*;

abstract public class Prototype {

    private Image sprite;
    private Point position;
    private Point movementVector;//Speed
    private Dimension size;

    public abstract void move();
    public abstract Prototype clone();

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setMovementVector(Point movementVector) {
        this.movementVector = movementVector;
    }
}
