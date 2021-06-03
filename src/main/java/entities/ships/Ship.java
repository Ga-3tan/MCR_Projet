package entities.ships;

import entities.Prototype;
import entities.Shot;
import lombok.Getter;


import java.awt.Point;
import java.awt.Dimension;

@Getter
abstract public class Ship extends Prototype {

    private final String RED_LASER_PATH = "";
    private final String GREEN_LASER_PATH = "";

    private int hp;

    protected final Shot greenLaser = new Shot(GREEN_LASER_PATH, new Point(), new Point(), new Dimension(2, 5), null);
    protected final Shot redLaser = new Shot(RED_LASER_PATH, new Point(), new Point(), new Dimension(2,5), null);

    public Ship(String spritePath, Point position, Point movementVector, Dimension size, int hp) {
        super(spritePath, position, movementVector, size);
        this.hp = hp;
    }

    public abstract Shot fire();
}
