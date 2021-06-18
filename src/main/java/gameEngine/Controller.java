package gameEngine;

import entities.GameObject;
import entities.Shot;
import entities.ships.Enemy;
import entities.ships.Player;
import gameEngine.panels.DisplayPanel;
import gameEngine.panels.InfoPanel;
import entities.ships.Ship;
import entities.Asteroid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import static entities.ships.Enemy.*;
import static utils.RandomGenerator.randomInt;


public class Controller {
    private static final Dimension DIMENSION = new Dimension(512, 1074);
    private static Controller instance;
    private ReentrantLock mutex = new ReentrantLock();

    private final JFrame frame = new JFrame();
    private final DisplayPanel displayPanel = new DisplayPanel(this);
    private final InfoPanel infoPanel = new InfoPanel(this);

    private int score;

    private final Set<Integer> activeKeys = new HashSet<>();
    private List<Timer> timers = new LinkedList<>();


    // Elements du jeu
    private Player player;
    private boolean playerMoving;

    //List of game objects
    private final LinkedList<GameObject> enemies = new LinkedList<>();
    private final LinkedList<GameObject> decorElements = new LinkedList<>();
    private final LinkedList<GameObject> shots = new LinkedList<>();


    // Prototypes
    private final LinkedList<Ship> enemiesPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();

    /**
     *
     * @return
     */
    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();

        return instance;
    }

    /**
     *
     */
    private Controller() {
        //this.player = new Player(new Point(231, 920), new Point(0, 0), new Dimension(50, 50), 10);

        // Initialise les frames et panels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(displayPanel, BorderLayout.CENTER);
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBorder(new EmptyBorder(200, 10, 200, 10));
        JButton start = new JButton();
        customButton(start, "images/start.png", actionEvent -> {
            run();
            start.setVisible(false);
        });
        displayPanel.add(start, BorderLayout.CENTER);
        frame.setPreferredSize(DIMENSION);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        initializePrototypes();

//        int speed = 0;
//        int pause = 0;
        movePlayer();

        // Initialise le taimer de jeu
        timers.add(new Timer(17, evt -> update()));
        timers.add(new Timer(10000, evt -> spawnAsteroids()));
        timers.add(new Timer(4000, evt -> spawnEnemies()));

        initGame();

    }

    /**
     *
     */
    private void initGame(){
        this.player = new Player(new Point(231, 920), new Point(0, 0), new Dimension(50, 50), 10);
        enemies.clear();
        decorElements.clear();
        shots.clear();
        score = 0;
    }

    /**
     *
     */
    public void run() {
        for (Timer t : timers)
            t.start();
    }

    /**
     *
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

        // Bouge le joueur
        if (!player.isOutOf(displayPanel))
            player.move();

        // Bouge les enemies + fire()
        for (GameObject enemy : enemies) {
            enemy.move();
            Shot shot = ((Ship) enemy).fire();
            if (shot != null) shots.add(shot);
        }
        // Bouge les lasers
        for (GameObject shot : shots)
            shot.move();

        // Bouge les asteroides
        for (GameObject decor : decorElements)
            decor.move();

        // Applique les mouvements
        applyKeys();

        //Shots collision
        shotsCollisions();

        //Player collisions
        playerCollisions();

        if (player.getHp() <= 0) {
            player.die();
            // Game Over label
            Image gameOver = Toolkit.getDefaultToolkit().getImage("images/game-over.png").getScaledInstance(400, 400,Image.SCALE_FAST);
            JLabel gameOverLabel = new JLabel(new ImageIcon(gameOver));
            // Restart bouton
            JButton restart = new JButton();
            customButton(restart, "images/restart.png", actionEvent -> {
                initGame();
                displayPanel.remove(gameOverLabel);
                restart.setVisible(false);
                run();
            });

            displayPanel.add(gameOverLabel,BorderLayout.CENTER);
            displayPanel.add(restart, BorderLayout.SOUTH);

            stopTimers();
        }

        // Pour chaque enemies, le supprime s'il est mort
        enemies.removeIf(enemy -> {
            Enemy e = (Enemy) enemy;
            boolean out = e.getHp() <= 0;
            if (out) score += e.getScoreValue();
            return out;
        });

        // Pour chaque enemies, le supprime s'il est hors de l'interface
        enemies.removeIf(enemy -> enemy.isOutOf(displayPanel));

        // Pour chaque asteroide, le supprime s'il est hors de l'interface
        decorElements.removeIf(decor -> decor.isOutOf(displayPanel));

        // Pour chaque tir, le supprime s'il est hors de l'interface
        shots.removeIf(shot -> shot.isOutOf(displayPanel));

        displayPanel.repaint();
        infoPanel.repaint();
    }

    /* COLLISIONS */

    /**
     *
     */
    private void playerCollisions() {
        playerCollisionGameObject(decorElements);
        playerCollisionGameObject(enemies);
    }

    /**
     *
     * @param gameObjectList
     */
    private void playerCollisionGameObject(List<GameObject> gameObjectList) {
        for (GameObject gameObject : gameObjectList) {
            if (player.getHitbox().intersects(gameObject.getHitbox())) {
                player.setHp(0); // kill player
            }
        }
    }

    /**
     *
     */
    private void shotsCollisions() {
        for (int i = 0; i < shots.size(); ) {
            Shot shot = (Shot) shots.get(i);
            boolean shotDestroyed = shotAsteroidCollision(shot) || shotEnemyCollision(shot) || shotPlayerCollision(shot);
            if (!shotDestroyed) i++;
        }
    }

    /**
     *
     * @param shot
     * @return
     */
    private boolean shotAsteroidCollision(Shot shot) {
        for (GameObject asteroid : decorElements) {
            if (asteroid.getHitbox().intersects(shot.getHitbox())) {
                shots.remove(shot);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param shot
     * @return
     */
    private boolean shotEnemyCollision(Shot shot) {
        if (shot.getFriendly()) {
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
     *
     * @param shot
     * @return
     */
    private boolean shotPlayerCollision(Shot shot) {
        if (!shot.getFriendly() && player.getHitbox().intersects(shot.getHitbox())) {
            player.reduceHP(shot.getDamage());
            shots.remove(shot);
            return true;
        }
        return false;
    }

    /* PROTOTYPE INITIALIZATIONS */

    /**
     *
     */
    private void initializePrototypes() {

        // Initialiser tous les prototypes
        // ASTEROIDS
        asteroidsPrototypes.add(new Asteroid(new Dimension(90, 90), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(75, 75), new Point(0, 4)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(50, 50), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(25, 25), new Point(0, 4)));

        // ENEMIES
        enemiesPrototypes.add(new Enemy(GREEN_ENEMY_PATH, new Point(0, 0), new Point(0, 3), new Dimension(50, 50), 2, 1500, 5, 100));
        enemiesPrototypes.add(new Enemy(BLUE_ENEMY_PATH, new Point(0, 0), new Point(0, 2), new Dimension(50, 50), 4, 1500, 7, 150));
        enemiesPrototypes.add(new Enemy(ORANGE_ENEMY_PATH, new Point(0, 0), new Point(0, 1), new Dimension(50, 50), 5, 2000, 10, 200));
        enemiesPrototypes.add(new Enemy(BLACK_ENEMY_PATH, new Point(0, 0), new Point(0, 1), new Dimension(50, 50), 5, 2000, 12, 250));

    }

    /**
     * Créer des clones des prototypes d'enemies de maniere aleatoire
     */
    private void spawnEnemies() {

        // Get a random enemy from enemiesProtype
        int index = randomInt(0, enemiesPrototypes.size() - 1);
        GameObject copy = enemiesPrototypes.get(index).clone();

        // Set la coord x de maniere aleatoire
        int x = randomInt(50, frame.getWidth() - 50);
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

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

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
     *
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
     * Customise et défini l'action à effecture du bouton passé en paramètre
     *
     * @param button Un JButton à customiser et définir
     * @param iconPath Le path jusqu'à l'image de l'icône du bouton
     * @param actionListener Un objet implémentant l'interface ActionListener qui défini
     *                       ce que le bouton effectue sur un évènement
     */
    private void customButton(JButton button, String iconPath, ActionListener actionListener){
        Image icon = Toolkit.getDefaultToolkit().getImage(iconPath).getScaledInstance(200, 70,Image.SCALE_FAST);
        button.setIcon(new ImageIcon(icon));
        button.setBackground(new Color(58, 46,53));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.addActionListener(actionListener);
    }
}
