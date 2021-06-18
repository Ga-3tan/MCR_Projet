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
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import static entities.ships.Enemy.*;
import static utils.RandomGenerator.randomInt;


public class Controller {

    private static Controller instance;

    private final JFrame frame = new JFrame();
    private static final Dimension FRAME_DIMENSION = new Dimension(512, 1074);
    private final DisplayPanel displayPanel = new DisplayPanel(this);
    private final InfoPanel infoPanel = new InfoPanel(this);
    private static final String RESTART_IMG_PATH = "images/restart.png";
    private static final String GAMME_OVER_IMG_PATH = "images/game-over.png";
    private static final int GAMME_OVER_IMG_SIZE = 400;

    private final Set<Integer> activeKeys = new HashSet<>();
    private List<Timer> timers = new LinkedList<>();

    // Elements du jeu
    private Player player;
    private final int playerSize = 50;
    private final Point playerInitPosition = new Point(231, 920);
    private final int playerHp = 5;
    private boolean playerMoving;
    private int score;

    //List of game objects
    private final LinkedList<GameObject> enemies = new LinkedList<>();
    private final LinkedList<GameObject> decorElements = new LinkedList<>();
    private final LinkedList<GameObject> shots = new LinkedList<>();

    // Prototypes
    private final LinkedList<Ship> enemiesPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();

    /**
     * Retourne l'instance unique du Controller (Singleton)
     *
     * @return l'instance unique du Controller
     */
    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();

        return instance;
    }

    /**
     * Constructeur privé du Controller (privé car Controller = Singleton)
     */
    private Controller() {
        initGame();

        // Initialise les frames et panels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
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
        timers.add(new Timer(10000, evt -> spawnAsteroids()));
        timers.add(new Timer(4000, evt -> spawnEnemies()));
    }

    /**
     * Initialise le jeu
     */
    private void initGame() {
        this.player = new Player(playerInitPosition, new Point(0, 0), new Dimension(playerSize, playerSize), playerHp);
        enemies.clear();
        decorElements.clear();
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
    public void stopTimers() {
        for (Timer t : timers)
            t.stop();
    }

    /**
     * Fonction apelée à chaque frame
     */
    private void update() {

        // Freine le joueur
        if (!playerMoving) // TODO sûr de la condition ?
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
        for (GameObject decor : decorElements)
            decor.move();

        // Applique les mouvements
        applyKeys();

        // Collisions
        Collision.shotsCollisions(shots, decorElements, enemies, player);
        Collision.playerCollisions(player, decorElements, enemies);

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
        decorElements.removeIf(decor -> decor.isOutOf(displayPanel));
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
        Image gameOver = Toolkit.getDefaultToolkit()
                .getImage(Thread.currentThread()
                        .getContextClassLoader()
                        .getResource(GAMME_OVER_IMG_PATH))
                .getScaledInstance(GAMME_OVER_IMG_SIZE, GAMME_OVER_IMG_SIZE, Image.SCALE_FAST);
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
        asteroidsPrototypes.add(new Asteroid(new Dimension(90, 90), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(75, 75), new Point(0, 4)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(50, 50), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(25, 25), new Point(0, 4)));

        // ENEMIES
        enemiesPrototypes.add(new Enemy(GREEN_ENEMY_PATH, new Point(0, 0), new Point(0, 5), new Dimension(30, 30), 1, 1200, 10, 100));
        enemiesPrototypes.add(new Enemy(BLUE_ENEMY_PATH, new Point(0, 0), new Point(0, 2), new Dimension(50, 50), 3, 1500, 7, 150));
        enemiesPrototypes.add(new Enemy(ORANGE_ENEMY_PATH, new Point(0, 0), new Point(0, 1), new Dimension(70, 70), 5, 2500, 10, 200));
        enemiesPrototypes.add(new Enemy(BLACK_ENEMY_PATH, new Point(0, 0), new Point(0, 1), new Dimension(160, 120), 8, 200, 5, 500));
    }

    /**
     * Créer des clones des prototypes d'enemies de maniere aleatoire
     */
    private void spawnEnemies() {
        // Récupère un enemi au hasard depuis la liste de prototypes d'enemis (enemiesProtype)
        int index = randomInt(0, enemiesPrototypes.size() - 1);
        GameObject copy = enemiesPrototypes.get(index).clone();

        // Modifie les coordonnées de x de maniere aleatoire
        int x = randomInt(50, frame.getWidth() - copy.getSize().width);
        copy.setPosition(new Point(x, 0));

        //Ajoute dans la liste d'enemies
        enemies.add(copy);
    }

    /**
     * Créer des clones des prototypes d'asteroides de maniere aleatoire
     */
    private void spawnAsteroids() {
        int index = randomInt(0, asteroidsPrototypes.size() - 1);
        GameObject copy = asteroidsPrototypes.get(index).clone();
        copy.randomizePositionOnX(frame.getWidth());
        decorElements.add(copy);
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
     * Bouge le joueur lors de la pression d'une touche TODO
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
                System.out.println(activeKeys);
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
        gameObjects.add(player);
        gameObjects.addAll(enemies);
        gameObjects.addAll(decorElements);
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
