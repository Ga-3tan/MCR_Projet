package entities.ships;

import entities.GameObject;
import entities.Shot;

import java.awt.*;
import java.util.Date;


public class Player extends Ship {
    private static final String PLAYER_PATH = "images/PNG/playerShip2_green.png";
    static final int SHOOT_DELAY = 300;

    long lastShotTime = 0;

    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(PLAYER_PATH, position, movementVector, size, hp);
    }

    public Player(Player copy) {
        super(PLAYER_PATH, copy.getPosition(), copy.getMovementVector(), copy.getSize(), copy.getHp());
    }

    @Override
    public GameObject clone() {
        return new Player(this);
    }

    @Override
    public Shot fire() {
        if (new Date().getTime() - lastShotTime > SHOOT_DELAY) {
            lastShotTime = new Date().getTime();

            Shot shot = (Shot) greenLaser.clone();
            shot.setFriendly(this);
            shot.setMovementVector(new Point(0, -12)); // 0, -12 => va vers le haut
            shot.setPosition(new Point(this.getPosition().x + (int) (this.getSize().getWidth() / 2), this.getPosition().y));

            return shot;
        } else {
            return null;
        }
    }
}
