package entities.ships;

import entities.GameObject;
import entities.Shot;

import java.awt.*;

public class Enemy extends Ship{

    private static final String BLACK_ENEMY_PATH ="images\\PNG\\Enemies\\enemyBlack3.png";
    private static final String BLUE_ENEMY_PATH="images\\PNG\\Enemies\\enemyBlue5.png";
    private static final String GREEN_ENEMY_PATH="images\\PNG\\Enemies\\enemyGreen2.png";
    private static final String ORANGE_ENEMY_PATH="images\\PNG\\Enemies\\enemyRed4.png";

    public Enemy(String spritePath, Point position, Point movementVector, Dimension size, int hp) {
        super(spritePath, position, movementVector, size, hp);
    }

    public Enemy(Enemy enemy) {
        this(enemy.getSpritePath(), enemy.getPosition(), enemy.getMovementVector(), enemy.getSize(), enemy.getHp());
    }

    @Override
    public GameObject clone() {
        return new Enemy(this);
    }

    @Override
    public Shot fire() {
        Shot shot = (Shot) redLaser.clone();
        greenLaser.setMovementVector(new Point(0,6)); // 0, 6 => va vers le haut
        greenLaser.setPosition(this.getPosition());
        return shot;
    }
}
