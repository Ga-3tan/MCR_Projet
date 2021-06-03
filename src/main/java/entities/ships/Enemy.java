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
        return null;
    }

    @Override
    public Shot fire() {
        return null;
    }
}
