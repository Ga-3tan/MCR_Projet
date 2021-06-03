package entities;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
abstract public class Prototype {

    private Image sprite;
    private String spritePath;
    private Point position;
    private Point movementVector;//Speed
    private Dimension size;

    public Prototype(String spritePath, Point position, Point movementVector, Dimension size) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(spritePath);
        this.spritePath = spritePath;
        this.sprite = image;
        this.position = position;
        this.movementVector = movementVector;
        this.size = size;
    }

    public void move() {
        this.setPosition(new Point(
                this.getPosition().x + this.getMovementVector().x,
                this.getPosition().y + this.getMovementVector().y)
        );
    }

    public abstract Prototype clone();

}
