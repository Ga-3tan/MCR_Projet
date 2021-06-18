package entities;

import lombok.Getter;
import lombok.Setter;
import java.awt.Point;
import java.awt.Dimension;

/**
 * Prototype concret, représente un tir de vaisseau
 *
 * Name     : Shot
 * File     : Shot.java
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
@Setter
public class Shot extends GameObject {
    private Boolean friendly;
    private int damage;

    /**
     * Constructeur de Shot
     *
     * @param spritePath     chemin vers le fichier du sprite
     * @param position       position actuelle du Shot
     * @param movementVector vecteur de mouvement (vitesse et direction)
     * @param size           taille du Shot
     * @param friendly       friendliness du shot (vrai si vient du Player)
     * @param damage         dégat provoqué par le Shot
     */
    public Shot(String spritePath, Point position, Point movementVector, Dimension size, Boolean friendly, int damage) {
        super(spritePath, position, movementVector, size);
        this.friendly = friendly;
        this.damage = damage;
    }

    /**
     * Constructeur de copie de Shot
     *
     * @param copy instance à copier
     */
    public Shot(Shot copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
        this.friendly = copy.friendly;
    }

    /**
     * Clone l'instance de Shot (partie du pattern prototype)
     *
     * @return copie du Shot en tant que GameObject
     */
    @Override
    public GameObject clone() {
        return new Shot(this);
    }
}

// (╯✧▽✧)╯
