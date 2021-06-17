package entities.ships;

import entities.GameObject;
import entities.Shot;
import gameEngine.panels.DisplayPanel;

import java.awt.*;
import java.util.Date;


public class Player extends Ship {
    private static final String PLAYER_PATH = "images/PNG/playerShip2_green.png";
    static final int SHOOT_DELAY = 300;

    long lastShotTime = 0;

    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(PLAYER_PATH, position, movementVector, size, hp, SHOOT_DELAY);
    }

    public Player(Player copy) {
        super(PLAYER_PATH, copy.getPosition(), copy.getMovementVector(), copy.getSize(), copy.getHp(), copy.getShoot_delay());
    }

    @Override
    public GameObject clone() {
        return new Player(this);
    }

    @Override
    public Shot fire() {
        if (new Date().getTime() - lastShotTime > this.getShoot_delay()) {
            lastShotTime = new Date().getTime();

            Shot shot = (Shot) greenLaser.clone();
            shot.setFriendly(true);
            shot.setMovementVector(new Point(0, -12)); // 0, -12 => va vers le haut
            shot.setPosition(new Point(this.getPosition().x + (int) (this.getSize().getWidth() / 2), this.getPosition().y));

            return shot;
        } else {
            return null;
        }
    }

    @Override
    public boolean isOutOf(DisplayPanel displayPanel) {
        return !(this.getPosition().getX() + this.getDeltaX() >= 0
                && this.getPosition().getX() + this.getSize().getWidth() + this.getDeltaX() <= displayPanel.getWidth()
                && this.getPosition().getY() + this.getDeltaY() >= 0
                && this.getPosition().getY() + this.getSize().getHeight() + this.getDeltaY() <= displayPanel.getHeight());
    }

}
