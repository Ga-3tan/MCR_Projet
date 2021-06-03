package entities.ships;

import entities.Prototype;
import entities.Shot;

import java.awt.*;

public class Player extends Ship{

    public Player(Image sprite, Point position, Point movementVector, Dimension size) {
        super(sprite, position, movementVector, size);
    }

    @Override
    public void move() {

    }

    @Override
    public Prototype clone() {
        return null;
    }

    @Override
    public Shot fire() {
        return null;
    }
}
