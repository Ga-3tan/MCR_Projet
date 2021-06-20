package entities.ships;

import entities.GameObject;
import entities.Shot;
import gameEngine.panels.DisplayPanel;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Date;

import static utils.AppPaths.DEAD_PLAYER_PATH;
import static utils.AppPaths.PLAYER_PATH;
import static utils.ImageManager.getImage;

/**
 * Prototype concret, représente le vaisseau du joueur
 *
 * Name     : Player
 * File     : Player.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public class Player extends Ship {

    static final int SHOOT_DELAY = 300;
    static final int SHOOT_SPEED = -12;

    long lastShotTime = 0;

    /**
     * Constructeur de Player
     *
     * @param position       position actuelle du Player
     * @param movementVector vecteur de mouvement (vitesse et direction)
     * @param size           taille du Player
     * @param hp             hp de base du Player
     */
    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(PLAYER_PATH, position, movementVector, size, hp, SHOOT_DELAY, SHOOT_SPEED);
    }

    /**
     * Constructeur de copie de Player
     *
     * @param copy instance à copier
     */
    public Player(Player copy) {
        super(PLAYER_PATH, copy.getPosition(), copy.getMovementVector(), copy.getSize(), copy.getHp(), copy.getShoot_delay(), copy.getShoot_speed());
        this.lastShotTime = 0;
    }

    /**
     * Appelé quand le Player meurt
     */
    public void die() {
        this.setSprite(getImage(DEAD_PLAYER_PATH).getScaledInstance(getSize().width, getSize().height, Image.SCALE_DEFAULT));
    }

    /**
     * Clone l'instance de Player (partie du pattern prototype)
     *
     * @return copie du Player en tant que GameObject
     */
    @Override
    public GameObject clone() {
        return new Player(this);
    }

    /**
     * Créer une copie à partir du prototype greenLaser et lui assigne
     * une nouvelle position, friendliness, damage et vecteur de vitesse
     *
     * @return clone du prototype greenLaser tiré
     */
    @Override
    public Shot fire() {
        if (new Date().getTime() - lastShotTime > this.getShoot_delay()) {
            lastShotTime = new Date().getTime();

            Shot shot = (Shot) greenLaser.clone();
            shot.setFriendly(true);
            shot.setMovementVector(new Point(0, -12));
            shot.setPosition(new Point(this.getPosition().x + (int) (this.getSize().getWidth() / 2), this.getPosition().y));

            return shot;
        } else {
            return null;
        }
    }

    /**
     * Vérifie si le Player et hors des limites du displayPanel
     *
     * @param displayPanel zone où doit se trouver le Player
     * @return vrai si le Player est hors des limites du displayPanel
     */
    @Override
    public boolean isOutOf(DisplayPanel displayPanel) {
        return !(this.getPosition().getX() + this.getDeltaX() >= 0
                && this.getPosition().getX() + this.getSize().getWidth() + this.getDeltaX() <= displayPanel.getWidth()
                && this.getPosition().getY() + this.getDeltaY() >= 0
                && this.getPosition().getY() + this.getSize().getHeight() + this.getDeltaY() <= displayPanel.getHeight());
    }

}
