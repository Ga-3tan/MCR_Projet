package entities.ships;

import entities.Prototype;
import entities.Shot;

import java.awt.*;

abstract public class Ship extends Prototype {

    private int hp;

    public Ship(Image sprite, Point position, Point movementVector, Dimension size) {
        super(sprite, position, movementVector, size);
    }

    public abstract Shot fire();
}
