package entities.ships;

import entities.Prototype;
import entities.Shot;

import java.awt.*;


public class Player extends Ship {

    private static final String SPRITE_PATH = "images/PNG/playerShip2_green.png";


    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(SPRITE_PATH, position, movementVector, size, hp);
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
