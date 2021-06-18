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
     * Constructeur de la classe abstraite Ship
     *
     * @param spritePath     chemin vers le fichier du sprite
     * @param position       position actuelle du Ship
     * @param movementVector vecteur de mouvement (vitesse et direction)
     * @param size           taille du Ship
     * @param hp             hp de base du Ship
     * @param shoot_delay    délais entre les tirs en ms
     * @param shoot_speed    vitesse des tirs tirés par le Ship
     */
    public Ship(String spritePath, Point position, Point movementVector, Dimension size, int hp, int shoot_delay, int shoot_speed) {
        super(spritePath, position, movementVector, size);
        this.hp = hp;
        this.shoot_delay = shoot_delay;
        this.shoot_speed = shoot_speed;
    }

    /**
     * Réduit les hp actuel du Ship
     *
     * @param damage dégats reçus
     */
    public void reduceHP(int damage) {
        if (this.getHp() - damage <= 0) {
            hp = 0;
        } else {
            hp -= damage;
        }
    }

    /**
     * modifie l'hp du Ship
     *
     * @param hp hp à assigner
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Créer une copie d'un Shot à partir du lié au Ship
     *
     * @return clone du prototype de Shot lié au vaisseau
     */
    public abstract Shot fire();

}
