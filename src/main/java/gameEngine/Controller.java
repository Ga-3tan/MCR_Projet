package gameEngine;

import entities.GameObject;
import entities.Shot;
import entities.ships.Enemy;
import entities.ships.Player;
import gameEngine.panels.DisplayPanel;
import gameEngine.panels.InfoPanel;
import entities.ships.Ship;
import entities.Asteroid;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import static utils.AppPaths.*;
import static utils.ImageManager.getImage;
import static utils.RandomGenerator.randomInt;

/**
 * Gestion principale de la partie, contient toute la logique
 * et la boucle de jeu
 *
 * Name     : Controller
 * File     : Controller.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public class Controller {

    /**
     * Classe Instance pour le singleton
     */
    private static class Instance
    {
        static final Controller instance = new Controller();
    }

    private final JFrame frame = new JFrame();
    private static final Dimension FRAME_DIMENSION = new Dimension(512, 1074);
    private DisplayPanel displayPanel;
    private InfoPanel infoPanel;
    private static final int GAME_OVER_IMG_SIZE = 400;

    private final Set<Integer> activeKeys = new HashSet<>();
    private final List<Timer> timers = new LinkedList<>();

    // Elements du jeu
    private Player player;
    private boolean playerMoving;
    private int score;

    //List of game objects
    private final LinkedList<GameObject> enemies = new LinkedList<>();
    private final LinkedList<GameObject> asteroids = new LinkedList<>();
    private final LinkedList<GameObject> shots = new LinkedList<>();

    // Prototypes
    private final LinkedList<Ship> enemiesPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();

    /**
     * Retourne l'instance unique du Controller (Singleton)
     *
     * @return l'instance unique du Controller
     */
    public static Controller getInstance()
    {
        return Instance.instance;
    }

    /**
     * Constructeur privé du Controller (privé car Controller = Singleton)
     */
    private Controller() {
    }

    /**
     * Démarre les éléments du jeu
     */
    public void initController(){
        initGame();

        // Initialise les frames et panels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        displayPanel = new DisplayPanel();
        infoPanel = new InfoPanel();
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(displayPanel, BorderLayout.CENTER);
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBorder(new EmptyBorder(200, 10, 200, 10));
        JButton start = new JButton();
        customizeButton(start, "images/start.png", actionEvent -> {
            run();
            start.setVisible(false);
        });
        displayPanel.add(start, BorderLayout.CENTER);
        frame.setPreferredSize(FRAME_DIMENSION);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        initializePrototypes();

        movePlayer();

        // Initialise le timer de jeu
        timers.add(new Timer(17, evt -> update()));
        timers.add(new Timer(10000, evt -> spawn(asteroidsPrototypes, asteroids)));
        timers.add(new Timer(4000, evt -> spawn(enemiesPrototypes, enemies)));

    }

    /**
     * Initialise le jeu
     */
    private void initGame() {
        this.player = new Player(new Point(231, 920), new Point(0, 0),  new Dimension(50, 50), 5);
        enemies.clear();
        asteroids.clear();
        shots.clear();
        score = 0;
    }

    /**
     * Lance le jeu (lance les timers)
     */
    public void run() {
        for (Timer t : timers)
            t.start();
    }

    /**
     * Arrête les timers (permet de mettre le jeu en pause / l'arrêter)
     */
    private void stopTimers() {
        for (Timer t : timers)
            t.stop();
    }

    /**
     * Fonction apelée à chaque frame
     */
    private void update() {

        // Freine le joueur
        if (!playerMoving)
            player.slowDown();

        // Mouvements des GameObjects
        if (!player.isOutOf(displayPanel))
            player.move();
        for (GameObject enemy : enemies) {
            enemy.move();
            Shot shot = ((Ship) enemy).fire();
            if (shot != null) shots.add(shot);
        }
        for (GameObject shot : shots)
            shot.move();
        for (GameObject decor : asteroids)
            decor.move();

        // Applique les mouvements
        applyKeys();

        // Collisions
        Collision.shotsCollisions(shots, asteroids, enemies, player);
        Collision.playerCollisions(player, asteroids, enemies);

        // Game over
        if (player.getHp() <= 0) {
            gameOver();
        }

        // Suppression des GameObjets morts ou sortis de l'interface
        enemies.removeIf(enemy -> {
            Enemy e = (Enemy) enemy;
            boolean out = e.getHp() <= 0;
            if (out) score += e.getScoreValue();
            return out;
        });
        enemies.removeIf(enemy -> enemy.isOutOf(displayPanel));
        asteroids.removeIf(decor -> decor.isOutOf(displayPanel));
        shots.removeIf(shot -> shot.isOutOf(displayPanel));

        // Update affichage
        displayPanel.repaint();
        infoPanel.repaint();
    }

    /**
     * Gère le game over (arrêt du jeu et affichage)
     */
    private void gameOver() {
        player.die();
        // Game Over label
        Image gameOver = getImage(GAME_OVER_IMG_PATH).getScaledInstance(GAME_OVER_IMG_SIZE, GAME_OVER_IMG_SIZE, Image.SCALE_FAST);
        JLabel gameOverLabel = new JLabel(new ImageIcon(gameOver));
        // Restart bouton
        JButton restart = new JButton();
        customizeButton(restart, RESTART_IMG_PATH, actionEvent -> {
            initGame();
            displayPanel.remove(gameOverLabel);
            restart.setVisible(false);
            run();
        });

        displayPanel.add(gameOverLabel, BorderLayout.CENTER);
        displayPanel.add(restart, BorderLayout.SOUTH);

        stopTimers();
    }

    /**
     * Initialise tous les prototypes
     */
    private void initializePrototypes() {
        // ASTEROIDS
        asteroidsPrototypes.add(new Asteroid(new Dimension(90, 90), new Point(), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(75, 75), new Point(), new Point(0, 4)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(50, 50), new Point(), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(25, 25), new Point(), new Point(0, 4)));

        // ENEMIES
        enemiesPrototypes.add(new Enemy(GREEN_ENEMY_PATH,  new Point(), new Point(0, 5), new Dimension(30, 30), 1, 1200, 10, 100));
        enemiesPrototypes.add(new Enemy(BLUE_ENEMY_PATH,   new Point(), new Point(0, 2), new Dimension(50, 50), 3, 1500, 7, 150));
        enemiesPrototypes.add(new Enemy(ORANGE_ENEMY_PATH, new Point(), new Point(0, 1), new Dimension(70, 70), 5, 2500, 10, 200));
        enemiesPrototypes.add(new Enemy(BLACK_ENEMY_PATH,  new Point(), new Point(0, 1), new Dimension(160, 120), 8, 200, 5, 500));
    }

    /**
     * Créer des clones de prototypes de position X aleatoire
     * @param listPrototypes La liste des protitypes à cloner
     * @param listDestination La liste de GameObject ou placer le clone
     */
    private void spawn(List<? extends GameObject> listPrototypes, List<GameObject> listDestination) {
        int index = randomInt(0, listPrototypes.size() - 1);
        GameObject copy = listPrototypes.get(index).clone();
        int x = randomInt(0, frame.getWidth() - copy.getSize().width);
        copy.setPosition(new Point(x, 0));
        listDestination.add(copy);
    }

    /**
     * @return le joueur
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return le score actuel
     */
    public int getScore() {
        return score;
    }

    /**
     * Bouge le joueur lors de la pression d'une touche
     */
    public void movePlayer() {
        // Gestion
        frame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                activeKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                activeKeys.remove(e.getKeyCode());
                playerMoving = false;
            }
        });
    }

    /**
     * Applique l'action des touches qui ont été enregistrées
     */
    private void applyKeys() {
        for (int key : activeKeys) {
            switch (key) {

                // Shot
                case KeyEvent.VK_SPACE:
                    Shot shot = player.fire();
                    if (shot != null) shots.add(shot);
                    break;

                // Move down
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:

                    player.setMovementVector(new Point(0, 10));
                    playerMoving = true;
                    break;

                // Move up
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    player.setMovementVector(new Point(0, -10));
                    playerMoving = true;
                    break;

                // Move left
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    player.setMovementVector(new Point(-8, 0));
                    playerMoving = true;
                    break;

                // Move right
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    player.setMovementVector(new Point(8, 0));
                    playerMoving = true;
                    break;

                // Exit program
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Retourne tous les objets du jeu sous forme de GameObject
     * Cette méthode est apellée pour l'affichage
     *
     * @return Une liste de GameObject
     */
    public List<GameObject> getAllGameObjects() {
        List<GameObject> gameObjects = new LinkedList<>();
        gameObjects.addAll(asteroids);
        gameObjects.add(player);
        gameObjects.addAll(enemies);
        gameObjects.addAll(shots);
        return gameObjects;
    }

    /**
     * Customise et défini l'action à effectuer du bouton passé en paramètre
     *
     * @param button         Un JButton à customiser et définir
     * @param iconPath       Le path jusqu'à l'image de l'icône du bouton
     * @param actionListener Un objet implémentant l'interface ActionListener qui défini
     *                       ce que le bouton effectue sur un évènement
     */
    private void customizeButton(JButton button, String iconPath, ActionListener actionListener) {
        Image icon = Toolkit.getDefaultToolkit()
                .getImage(Thread.currentThread()
                        .getContextClassLoader()
                        .getResource(iconPath))
                .getScaledInstance(200, 70, Image.SCALE_FAST);
        button.setIcon(new ImageIcon(icon));
        button.setBackground(new Color(58, 46, 53));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.addActionListener(actionListener);
    }
}
