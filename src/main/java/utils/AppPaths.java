package utils;

/**
 * Classe abstraite utilisée pour gérer les chemins des images du jeu
 *
 * Name     : ImageManager
 * File     : ImageManager.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public abstract class AppPaths {

    // LASER PATHS
    public static final String RED_LASER_PATH   = "assets/lasers/laserRed.png";
    public static final String GREEN_LASER_PATH = "assets/lasers/laserGreen.png";

    // ENEMIES PATHS
    public static final String BLACK_ENEMY_PATH = "assets/enemies/enemyBlack.png";
    public static final String BLUE_ENEMY_PATH = "assets/enemies/enemyBlue.png";
    public static final String GREEN_ENEMY_PATH = "assets/enemies/enemyGreen.png";
    public static final String ORANGE_ENEMY_PATH = "assets/enemies/enemyRed.png";

    // PLAYER PATH
    public static final String PLAYER_PATH = "assets/player/player_green.png";
    public static final String DEAD_PLAYER_PATH = "assets/damage/playerShip_damage.png";

    // ASTEROIDS PATHS
    public static final String ASTEROID_PATH = "assets/asteroids/meteorBrown.png";

    // MENU PATHS
    public static final String START_IMG_PATH = "assets/menu/start.png";
    public static final String RESTART_IMG_PATH = "assets/menu/restart.png";
    public static final String GAME_OVER_IMG_PATH = "assets/menu/game-over.png";

}
