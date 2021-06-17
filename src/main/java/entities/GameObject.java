package entities;

import gameEngine.panels.DisplayPanel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Random;

@Getter
@Setter
abstract public class GameObject {

    private Image sprite;
    private String spritePath;
    private Point position;
    private Point movementVector; //Speed vector
    private Dimension size;
    private Rectangle hitbox; // on utilise .intersects(Rectangle r) pour check la collision

    Random rand = new Random();


    public GameObject(String spritePath, Point position, Point movementVector, Dimension size) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(spritePath).getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT);;
        this.spritePath = spritePath;
        this.sprite = image;
        this.position = position;
        this.movementVector = movementVector;
        this.size = size;
        this.hitbox = new Rectangle(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
    }

    public void move() {
        this.setPosition(new Point(
                (int) (this.getPosition().getX() + this.getMovementVector().getX()),
                (int) (this.getPosition().getY() + this.getMovementVector().getY()))
        );
        this.getHitbox().setLocation(this.getPosition());
    }

    public abstract GameObject clone();

    /**
     * Vérifie si la position du gameObject est hors de l'interface verticalement
     * @return true s'il est hors de l'interface, false sinon
     * */
    public boolean isOutOf(DisplayPanel displayPanel){
        return this.getPosition().getY() > displayPanel.getHeight()/*
                || this.getPosition().getY() < 0*/;
    }

    /**
     * Donne une valeur aléatoire sur l'axe X
     * */
    public void randomizePositionOnX(int frameWidth){
        int x = rand.nextInt(frameWidth+getSize().width) - getSize().width;
        this.setPosition(new Point(x, -getSize().height));
    }

    /**
     * Donne une vitesse aléatoire
     * TODO (à modifier)
     * */
    public void randomizeSpeed(){
        // TODO changer movementVector en Point2D pour plus de précision dans l'intervalle ?
        int dy = rand.nextInt(2) + 1; // varie entre 1 et 2, ce n'est pas beaucoup
        this.setMovementVector(new Point(0, dy));
    }

    public int getDeltaX() {
        return (int) getMovementVector().getX();
    }

    public int getDeltaY() {
        return (int) getMovementVector().getY();
    }

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
}
