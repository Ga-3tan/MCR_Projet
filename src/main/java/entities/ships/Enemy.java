package entities.ships;

import entities.GameObject;
import entities.Shot;
import lombok.Getter;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Date;

/**
 * Prototype concret, représente un vaisseau ennemi
 *
 * Name     : Enemy
 * File     : Enemy.java
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
public class Enemy extends Ship {

    public static final String BLACK_ENEMY_PATH = "images/PNG/Enemies/enemyBlack3.png";
    public static final String BLUE_ENEMY_PATH = "images/PNG/Enemies/enemyBlue5.png";
    public static final String GREEN_ENEMY_PATH = "images/PNG/Enemies/enemyGreen2.png";
    public static final String ORANGE_ENEMY_PATH = "images/PNG/Enemies/enemyRed4.png";

    private long lastShotTime = 0;
    final private int scoreValue;

    /**
     * Constructeur de Enemy
     *
     * @param spritePath     chemin vers le fichier du sprite
     * @param position       position actuelle de Enemy
     * @param movementVector vecteur de mouvement (vitesse et direction)
     * @param size           taille de Enemy
     * @param hp             hp de base de Enemy
     * @param shoot_delay    délais entre les tirs en ms
     * @param shoot_speed    vitesse des tirs tirés par Enemy
     * @param scoreValue     score reçu lors de sa déstruction
     */
    public Enemy(String spritePath, Point position, Point movementVector, Dimension size, int hp, int shoot_delay, int shoot_speed, int scoreValue) {
        super(spritePath, position, movementVector, size, hp, shoot_delay, shoot_speed);
        this.scoreValue = scoreValue;
    }

    /**
     * Constructeur de copie de Enemy
     * @param enemy instance à copier
     */
    public Enemy(Enemy enemy) {
        super(enemy.getSpritePath(), enemy.getPosition(), enemy.getMovementVector(), enemy.getSize(), enemy.getHp(), enemy.getShoot_delay(), enemy.getShoot_speed());
        this.scoreValue = enemy.scoreValue;
        this.lastShotTime = 0;
    }

    /**
     * Clone l'instance de Enemy (partie du pattern prototype)
     *
     * @return copie du Enemy en tant que GameObject
     */
    @Override
    public GameObject clone() {
        return new Enemy(this);
    }

    /**
     * Créer une copie à partir du prototype redLaser et lui assigne
     * une nouvelle position, friendliness, damage et vecteur de vitesse
     *
     * @return clone du prototype redLaser tiré
     */
    @Override
    public Shot fire() {
        if (new Date().getTime() - lastShotTime > this.getShoot_delay()) {
            lastShotTime = new Date().getTime();

            Shot shot = (Shot) redLaser.clone();
            shot.setFriendly(false);
            shot.setMovementVector(new Point(0, getShoot_speed())); 
            shot.setPosition(new Point(this.getPosition().x + (int) (this.getSize().getWidth() / 2), (int) (this.getPosition().y + this.getSize().getHeight())));

            return shot;
        } else {
            return null;
        }
    }
}
