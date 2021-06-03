package entities.ships;

import entities.Prototype;
import entities.Shot;

abstract public class Ship extends Prototype {

    private int hp;

    public abstract Shot fire();
}
