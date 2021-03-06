package entities.ships;

import entities.GameObject;
import entities.Shot;
import lombok.Getter;
import java.awt.Point;
import java.awt.Dimension;

import static utils.AppPaths.GREEN_LASER_PATH;
import static utils.AppPaths.RED_LASER_PATH;

/**
 * Prototype concret, représente un vaisseau du jeu
 *
 * Name     : Ship
 * File     : Ship.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
@Getter
abstract public class Ship extends GameObject {

    //Shots Prototypes
    protected final Shot greenLaser = new Shot(GREEN_LASER_PATH, new Point(), new Point(), new Dimension(4, 15), 1);
    protected final Shot redLaser = new Shot(RED_LASER_PATH, new Point(), new Point(), new Dimension(4, 15),  1);

    private int hp;
    private final int shoot_delay;
    private final int shoot_speed;

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
