package entities;

import java.awt.*;

public class Asteroid extends GameObject {

    private static final String SPRITE_PATH = "images/PNG/Meteors/meteorBrown_big1.png";

    /**
     *
     * @param size
     * @param speed
     */
    public Asteroid(Dimension size, Point speed) {
        // Set la poisiton a (0,0) au départ, puis le randomise dans Controller
        super(SPRITE_PATH, new Point(0,0), speed, size);
    }

    /**
     *
     * @param copy
     */
    public Asteroid(Asteroid copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
    }

    /**
     *
     * @return
     */
    @Override
    public GameObject clone() {
        return new Asteroid(this);
    }
}
