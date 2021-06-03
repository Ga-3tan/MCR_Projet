package entities.ships;

import entities.Prototype;
import entities.Shot;

import java.awt.*;


public class Player extends Ship {

    private static final String SPRITE_PATH = "images/PNG/playerShip2_green.png";


    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(SPRITE_PATH, position, movementVector, size, hp);
    }

    public Player(Player copy) {
        super(SPRITE_PATH, copy.getPosition(), copy.getMovementVector(), copy.getSize(), copy.getHp());
    }

    /* move() appelé hors de la game loop */

    @Override
    public Prototype clone() {
        return new Player(this);
    }

    @Override
    public Shot fire() {
        Shot shot = (Shot) greenLaser.clone();
        greenLaser.setMovementVector(new Point(0,-10)); // 0, -10 => va vers le haut
        greenLaser.setPosition(this.getPosition());
        return shot;
    }
}
