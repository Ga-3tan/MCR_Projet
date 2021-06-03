package entities.ships;

import entities.Prototype;
import entities.Shot;

import java.awt.*;

public class Enemy extends Ship{

    public Enemy(String spritePath, Point position, Point movementVector, Dimension size, int hp) {
        super(spritePath, position, movementVector, size, hp);
    }

    public Enemy(Enemy enemy) {
        this(enemy.getSpritePath(), enemy.getPosition(), enemy.getMovementVector(), enemy.getSize(), enemy.getHp());
    }

    @Override
    public Prototype clone() {
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
