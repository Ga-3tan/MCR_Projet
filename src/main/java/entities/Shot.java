package entities;

import entities.ships.Ship;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Shot extends GameObject {
    private Boolean friendly;
    private int damage;

    /**
     *
     * @param spritePath
     * @param position
     * @param movementVector
     * @param size
     * @param friendly
     * @param damage
     */
    public Shot(String spritePath, Point position, Point movementVector, Dimension size, Boolean friendly, int damage) {
        super(spritePath, position, movementVector, size);
        this.friendly = friendly;
        this.damage = damage;
    }

    /**
     *
     * @param copy
     */
    public Shot(Shot copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
        this.friendly = copy.friendly;
    }

    /**
     *
     * @return
     */
    @Override
    public GameObject clone() {
        return new Shot(this);
    }
}


