package entities;

import gameEngine.panels.DisplayPanel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.net.URL;
import java.util.Random;

@Getter
@Setter
abstract public class GameObject {

    private Image sprite;
    private String spritePath;
    private Point position;
    private Point movementVector;
    private Dimension size;
    private Rectangle hitbox;

    Random rand = new Random();

    /**
     * Constructeur de la classe abstraite GameObject
     *
     * @param spritePath     chemin vers le fichier du sprite
     * @param position       position actuelle du GameObject
     * @param movementVector vecteur de mouvement (vitesse et direction)
     * @param size           taille du GameObject
     */
    public GameObject(String spritePath, Point position, Point movementVector, Dimension size) {
        this.spritePath = spritePath;
        this.sprite = getImage(spritePath).getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT);
        this.position = position;
        this.movementVector = movementVector;
        this.size = size;
        this.hitbox = new Rectangle(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
    }

    /**
     * Bouge le GameObject
     */
    public void move() {
        this.setPosition(new Point(
                (int) (this.getPosition().getX() + this.getMovementVector().getX()),
                (int) (this.getPosition().getY() + this.getMovementVector().getY()))
        );
        this.getHitbox().setLocation(this.getPosition());
    }

    /**
     * Clone l'instance de GameObject (partie du pattern prototype)
     *
     * @return copie du GameObject
     */
    public abstract GameObject clone();

    /**
     * Vérifie si le Player et hors des limites verticalement du displayPanel
     *
     * @param displayPanel zone où doit se trouver le Player
     * @return vrai si le Player est hors des limites verticale du displayPanel
     */
    public boolean isOutOf(DisplayPanel displayPanel) {
        return this.getPosition().getY() > displayPanel.getHeight()/*
                || this.getPosition().getY() < 0*/;
    }

    /**
     * Donne une valeur aléatoire sur l'axe X
     *
     * @param frameWidth larger de la zone d'apparition
     */
    public void randomizePositionOnX(int frameWidth) {
        int x = rand.nextInt(frameWidth + getSize().width) - getSize().width;
        this.setPosition(new Point(x, -getSize().height));
    }

    /**
     * Retourne le X la vitesse/direction
     *
     * @return X du vecteur de mouvement
     */
    public int getDeltaX() {
        return (int) getMovementVector().getX();
    }

    /**
     * Retourne le Y la vitesse/direction
     *
     * @return Y du vecteur de mouvement
     */
    public int getDeltaY() {
        return (int) getMovementVector().getY();
    }

    /**
     * Ralenti le GameObject (évite un arrêt brusque)
     */
    public void slowDown() {
        if (this.getDeltaX() > 0) {
            this.setMovementVector(new Point(this.getDeltaX() - 1, 0));
        } else if (this.getDeltaX() < 0) {
            this.setMovementVector(new Point(this.getDeltaX() + 1, 0));
        }

        if (this.getDeltaY() > 0) {
            this.setMovementVector(new Point(0, this.getDeltaY() - 1));
        } else if (this.getDeltaY() < 0) {
            this.setMovementVector(new Point(0, this.getDeltaY() + 1));
        }
    }

    private static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}
