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
    private Point movementVector; //Speed vector
    private Dimension size;
    private Rectangle hitbox; // on utilise .intersects(Rectangle r) pour check la collision

    public Prototype(String spritePath, Point position, Point movementVector, Dimension size) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(spritePath);
        this.spritePath = spritePath;
        this.sprite = image;
        this.position = position;
        this.movementVector = movementVector;
        this.size = size;
        this.hitbox = new Rectangle(position.x, position.y, (int) size.getWidth(), (int) size.getHeight());
    }

    public void move() {
        this.setPosition(new Point(
                this.getPosition().x + this.getMovementVector().x,
                this.getPosition().y + this.getMovementVector().y)
        );
        hitbox.setLocation(this.getPosition());
    }

    public abstract Prototype clone();

}
