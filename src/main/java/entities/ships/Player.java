package entities.ships;

import entities.GameObject;
import entities.Shot;
import gameEngine.panels.DisplayPanel;

import java.awt.*;


public class Player extends Ship {

    private static final String PLAYER_PATH = "images/PNG/playerShip2_green.png";


    public Player(Point position, Point movementVector, Dimension size, int hp) {
        super(PLAYER_PATH, position, movementVector, size, hp);
    }

    public Player(Player copy) {
        super(PLAYER_PATH, copy.getPosition(), copy.getMovementVector(), copy.getSize(), copy.getHp());
    }

    @Override
    public GameObject clone() {
        return new Player(this);
    }

    @Override
    public Shot fire() {
        Shot shot = (Shot) greenLaser.clone();
        greenLaser.setMovementVector(new Point(0,-10)); // 0, -10 => va vers le haut
        greenLaser.setPosition(new Point((int) (this.getPosition().getX()+this.getSize().getWidth()/2),(int) this.getPosition().getY()));
        return shot;
    }
}
