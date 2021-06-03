package entities;

import java.awt.*;

abstract public class Prototype {

    private Image sprite;
    private Point position;
    private Point movementVector;//Speed
    private Dimension size;

    public Prototype(Image sprite, Point position, Point movementVector, Dimension size) {
        this.sprite = sprite;
        this.position = position;
        this.movementVector = movementVector;
        this.size = size;
    }


    public abstract void move();
    public abstract Prototype clone();

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setMovementVector(Point movementVector) {
        this.movementVector = movementVector;
    }
}
