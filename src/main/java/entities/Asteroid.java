package entities;

import java.awt.*;

public class Asteroid extends GameObject {

    private static final String SPRITE_PATH = "images/PNG/Meteors/meteorBrown_big1.png";

    public Asteroid(Dimension size) {
        // set la poisiton a (0,0) au départ, puis le randomise dans Controller
        super(SPRITE_PATH, new Point(0,0), new Point(0,0), size);
    }

    public Asteroid(Asteroid copy) {
        super(copy.getSpritePath(), copy.getPosition(), copy.getMovementVector(), copy.getSize());
    }

    @Override
    public GameObject clone() {
        return new Asteroid(this);
    }
}
