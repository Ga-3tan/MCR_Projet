package entities;

import java.awt.Point;
import java.awt.Dimension;

/**
 * Prototype concret, représente un astéroide (obstacle pour le joueur)
 *
 * Name     : Asteroid
 * File     : Asteroid.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public class Asteroid extends GameObject {

    private static final String SPRITE_PATH = "images/PNG/Meteors/meteorBrown_big1.png";

    /**
     * Constructeur de Asteroid
     *
     * @param size  taille de l'Asteroid
     * @param speed vecteur de mouvement (vitesse et direction)
     */
    public Asteroid(Dimension size, Point position, Point speed) {
        super(SPRITE_PATH, position, speed, size);
    }

    /**
     * Constructeur de copie de Asteroid
     *
     * @param copy instance à copier
     */
    public Asteroid(Asteroid copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
    }

    /**
     * Clone l'instance de Asteroid (partie du pattern prototype)
     *
     * @return copie du Asteroid en tant que GameObject
     */
    @Override
    public GameObject clone() {
        return new Asteroid(this);
    }
}
