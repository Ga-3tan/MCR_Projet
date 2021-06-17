package entities.ships;

import entities.GameObject;
import entities.Shot;
import lombok.Getter;

import java.awt.*;
import java.util.Date;

@Getter
public class Enemy extends Ship{

    public static final String BLACK_ENEMY_PATH ="images\\PNG\\Enemies\\enemyBlack3.png";
    public static final String BLUE_ENEMY_PATH="images\\PNG\\Enemies\\enemyBlue5.png";
    public static final String GREEN_ENEMY_PATH="images\\PNG\\Enemies\\enemyGreen2.png";
    public static final String ORANGE_ENEMY_PATH="images\\PNG\\Enemies\\enemyRed4.png";

    private long lastShotTime = 0;
    final private int scoreValue;

    /**
     *
     * @param path
     * @param position
     * @param movementVector
     * @param size
     * @param hp
     * @param shoot_delay
     * @param shoot_speed
     * @param scoreValue
     */
    public Enemy(String path, Point position, Point movementVector, Dimension size, int hp, int shoot_delay, int shoot_speed, int scoreValue) {
        super(path, position, movementVector, size, hp, shoot_delay, shoot_speed);
        this.scoreValue = scoreValue;
    }

    /**
     *
     * @param enemy
     */
    public Enemy(Enemy enemy) {
        super(enemy.getSpritePath(), enemy.getPosition(), enemy.getMovementVector(), enemy.getSize(), enemy.getHp(), enemy.getShoot_delay(), enemy.getShoot_speed());
        this.scoreValue = enemy.scoreValue;
    }

    /**
     *
     * @return
     */
    @Override
    public GameObject clone() {
        return new Enemy(this);
    }

    /**
     *
     * @return
     */
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
