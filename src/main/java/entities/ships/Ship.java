package entities.ships;

import entities.GameObject;
import entities.Shot;
import lombok.Getter;


import java.awt.Point;
import java.awt.Dimension;
import java.util.Date;

@Getter
abstract public class Ship extends GameObject {
    private final String RED_LASER_PATH   = "images/PNG/Lasers/laserRed13.png";
    private final String GREEN_LASER_PATH = "images/PNG/Lasers/laserGreen03.png";

    private int hp;
    private int shoot_delay;

    protected final Shot greenLaser = new Shot(GREEN_LASER_PATH, new Point(), new Point(), new Dimension(4, 15), null, 1);
    protected final Shot redLaser = new Shot(RED_LASER_PATH, new Point(), new Point(), new Dimension(4,15), null, 1);

    public Ship(String spritePath, Point position, Point movementVector, Dimension size, int hp, int shoot_delay) {
        super(spritePath, position, movementVector, size);
        this.hp = hp;
        this.shoot_delay = shoot_delay;
    }

    public void reduceHP(int points){
        if (this.getHp() - points <= 0) {
            hp = 0;
        } else {
            hp -= points;
        }
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public abstract Shot fire();

}
