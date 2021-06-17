package entities.ships;

import entities.GameObject;
import entities.Shot;
import gameEngine.panels.DisplayPanel;

import java.awt.*;
import java.util.Date;


public class Player extends Ship {
    private static final String PLAYER_PATH = "images/PNG/playerShip2_green.png";
    private static final String DEAD_PLAYER_PATH = "images/PNG/Damage/playerShip3_damage3.png";
    static final int SHOOT_DELAY = 300;
    static final int SHOOT_SPEED = -12;

    long lastShotTime = 0;

    /**
     * Constructor for the Player
     *
     * @param position       current position of the player
     * @param movementVector vector for movement (speed and direction)
     * @param size           size of the player (square)
     * @param hp             base hp of the player
     */
    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(PLAYER_PATH, position, movementVector, size, hp, SHOOT_DELAY, SHOOT_SPEED);
    }

    /**
     * copy constructor of the player
     *
     * @param copy instance to copy from
     */
    public Player(Player copy) {
        super(PLAYER_PATH, copy.getPosition(), copy.getMovementVector(), copy.getSize(), copy.getHp(), copy.getShoot_delay(), copy.getShoot_speed());
    }

    /**
     * called when the player die
     */
    public void die() {
        this.setSprite(Toolkit.getDefaultToolkit().getImage(DEAD_PLAYER_PATH).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    }

    /**
     * clone the player instance (part of the prototype pattern)
     *
     * @return GameObject (abstract prototype) instance of the copy of Player
     */
    @Override
    public GameObject clone() {
        return new Player(this);
    }

    /**
     * Create a new copy of the greenLaser prototype
     * and set its new position, friendliness, damage and speed vector
     *
     * @return clone of the greenLaser prototype shot by the ship
     */
    @Override
    public Shot fire() {
        if (new Date().getTime() - lastShotTime > this.getShoot_delay()) {
            lastShotTime = new Date().getTime();

            Shot shot = (Shot) greenLaser.clone();
            shot.setFriendly(true);
            shot.setMovementVector(new Point(0, -12)); // 0, -12 => va vers le haut
            shot.setPosition(new Point(this.getPosition().x + (int) (this.getSize().getWidth() / 2), this.getPosition().y));
            shot.setDamage(1);

            return shot;
        } else {
            return null;
        }
    }

    /**
     * check if the Player is out of the displayPanel bound
     *
     * @param displayPanel area where the Player should be
     * @return true if the Player is out of the displayPanel bound
     */
    @Override
    public boolean isOutOf(DisplayPanel displayPanel) {
        return !(this.getPosition().getX() + this.getDeltaX() >= 0
                && this.getPosition().getX() + this.getSize().getWidth() + this.getDeltaX() <= displayPanel.getWidth()
                && this.getPosition().getY() + this.getDeltaY() >= 0
                && this.getPosition().getY() + this.getSize().getHeight() + this.getDeltaY() <= displayPanel.getHeight());
    }

}
