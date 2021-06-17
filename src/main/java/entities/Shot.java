package entities;

import entities.ships.Ship;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Shot extends GameObject {
    private Ship friendly = null;
    private int damage = 0;

    public Shot(String spritePath, Point position, Point movementVector, Dimension size, Ship friendly, int damage) {
        super(spritePath, position, movementVector, size);
        this.friendly = friendly;
        this.damage = damage;
    }

    public Shot(Shot copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
        this.friendly = copy.friendly;
    }

    @Override
    public GameObject clone() {
        return new Shot(this);
    }
}


