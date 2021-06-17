package entities.ships;

import entities.GameObject;
import entities.Shot;
import lombok.Getter;


import java.awt.Point;
import java.awt.Dimension;
import java.util.Date;

@Getter
abstract public class Ship extends GameObject {
    private final String RED_LASER_PATH = "images/PNG/Lasers/laserRed13.png";
    private final String GREEN_LASER_PATH = "images/PNG/Lasers/laserGreen03.png";

    private int hp;
    private final int shoot_delay;
    private final int shoot_speed;

    protected final Shot greenLaser = new Shot(GREEN_LASER_PATH, new Point(), new Point(), new Dimension(4, 15), null, 1);
    protected final Shot redLaser = new Shot(RED_LASER_PATH, new Point(), new Point(), new Dimension(4, 15), null, 1);

    /**
     * Constructor of abstract class Ship
     *
     * @param spritePath     path to the ship sprite file
     * @param position       current position of the ship
     * @param movementVector vector for movement (speed and direction)
     * @param size           size of the ship (square)
     * @param hp             base hp of the ship
     * @param shoot_delay    delay between shots in ms
     * @param shoot_speed    speed of the shots fired by the ship
     */
    public Ship(String spritePath, Point position, Point movementVector, Dimension size, int hp, int shoot_delay, int shoot_speed) {
        super(spritePath, position, movementVector, size);
        this.hp = hp;
        this.shoot_delay = shoot_delay;
        this.shoot_speed = shoot_speed;
    }

    /**
     * Reduce the current HP of the ship
     *
     * @param damage damage recieved by the ship
     */
    public void reduceHP(int damage) {
        if (this.getHp() - damage <= 0) {
            hp = 0;
        } else {
            hp -= damage;
        }
    }

    /**
     * set the ship hp
     *
     * @param hp hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Create a new copy of the shot prototype linked to the ship
     *
     * @return clone of the Shot type of the ship
     */
    public abstract Shot fire();

}
