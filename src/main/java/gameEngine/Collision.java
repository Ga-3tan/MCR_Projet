package gameEngine;

import entities.GameObject;
import entities.Shot;
import entities.ships.Enemy;
import entities.ships.Player;
import java.util.LinkedList;
import java.util.List;

/**
 * Posède toutes les méthodes relatives à
 * la gestion et la détection des collisions de GameObject
 *
 * Name     : Collision
 * File     : Collision.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public abstract class Collision {

    /**
     * Vérifie la collision du Player avec les Asteroid et Enemy
     */
    public static void playerCollisions(Player player, LinkedList<GameObject> decorElements, LinkedList<GameObject> enemies) {
        playerCollisionGameObject(player, decorElements);
        playerCollisionGameObject(player, enemies);
    }

    /**
     * Vérifie la colision des Shot avec les GameObject de gameObjectList
     *
     * @param gameObjectList dont il faut vérifier la collision avec le Player
     */
    private static void playerCollisionGameObject(Player player, List<GameObject> gameObjectList) {
        for (GameObject gameObject : gameObjectList) {
            if (player.getHitbox().intersects(gameObject.getHitbox())) {
                player.setHp(0); // kill player
            }
        }
    }

    /**
     * Vérifie la colision des Shot avec les autres GameObjects
     */
    public static void shotsCollisions(LinkedList<GameObject> shots, LinkedList<GameObject> decorElements, LinkedList<GameObject> enemies, Player player) {
        for (int i = 0; i < shots.size(); ) {
            Shot shot = (Shot) shots.get(i);
            boolean shotDestroyed = shotAsteroidCollision(shot, shots, decorElements) || shotEnemyCollision(shot, shots, enemies) || shotPlayerCollision(shot, shots, player);
            if (!shotDestroyed) i++;
        }
    }

    /**
     * Vérifie la colision d'un Shot avec un Asteroid et le retire de la list des Shot si collision
     *
     * @param shot tir dont il faut vérifier la collision
     * @return vrai s'il y a collision
     */
    private static boolean shotAsteroidCollision(Shot shot, LinkedList<GameObject> shots, LinkedList<GameObject> decorElements) {
        for (GameObject asteroid : decorElements) {
            if (asteroid.getHitbox().intersects(shot.getHitbox())) {
                shots.remove(shot);
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie la colision d'un Shot avec un Enemy et le retire de la list des Shot si collision
     *
     * @param shot tir dont il faut vérifier la collision
     * @return vrai s'il y a collision
     */
    private static boolean shotEnemyCollision(Shot shot, LinkedList<GameObject> shots, LinkedList<GameObject> enemies) {
        if (shot.isFriendly()) {
            for (GameObject enemy : enemies) {
                if (enemy.getHitbox().intersects(shot.getHitbox())) {
                    ((Enemy) enemy).reduceHP(shot.getDamage());
                    shots.remove(shot);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie la colision d'un Shot avec le Player et le retire de la list des Shot si collision
     *
     * @param shot tir dont il faut vérifier la collision
     * @return vrai s'il y a collision
     */
    private static boolean shotPlayerCollision(Shot shot, LinkedList<GameObject> shots, Player player) {
        if (!shot.isFriendly() && player.getHitbox().intersects(shot.getHitbox())) {
            player.reduceHP(shot.getDamage());
            shots.remove(shot);
            return true;
        }
        return false;
    }

}
