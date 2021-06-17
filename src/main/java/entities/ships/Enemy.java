package entities.ships;

import entities.GameObject;
import entities.Shot;

import java.awt.*;
import java.util.Date;

public class Enemy extends Ship{

    public static final String BLACK_ENEMY_PATH ="images\\PNG\\Enemies\\enemyBlack3.png";
    public static final String BLUE_ENEMY_PATH="images\\PNG\\Enemies\\enemyBlue5.png";
    public static final String GREEN_ENEMY_PATH="images\\PNG\\Enemies\\enemyGreen2.png";
    public static final String ORANGE_ENEMY_PATH="images\\PNG\\Enemies\\enemyRed4.png";

    private long lastShotTime = 0;

    public Enemy(String path, Point position, Point movementVector, Dimension size, int hp, int shoot_delay) {
        super(path, position, movementVector, size, hp, shoot_delay);
    }

    public Enemy(Enemy enemy) {
        super(enemy.getSpritePath(), enemy.getPosition(), enemy.getMovementVector(), enemy.getSize(), enemy.getHp(), enemy.getShoot_delay());
    }

    @Override
    public GameObject clone() {
        return new Enemy(this);
    }

    @Override
    public Shot fire() {
        if (new Date().getTime() - lastShotTime > this.getShoot_delay()) {
            lastShotTime = new Date().getTime();

            Shot shot = (Shot) redLaser.clone();
            shot.setFriendly(false);
            shot.setMovementVector(new Point(0, getShoot_speed())); // 0, 7 => va vers le bas
            shot.setPosition(new Point(this.getPosition().x + (int) (this.getSize().getWidth() / 2), (int) (this.getPosition().y + this.getSize().getHeight())));
            shot.setDamage(1);

            return shot;
        } else {
            return null;
        }
    }
}
