package gameEngine;

import entities.GameObject;
import entities.Shot;
import entities.ships.Enemy;
import entities.ships.Player;
import gameEngine.panels.DisplayPanel;
import gameEngine.panels.InfoPanel;
import entities.ships.Ship;
import entities.Asteroid;
import utils.RandomGenerator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;


public class Controller{
    private static final Dimension DIMENSION = new Dimension(512, 1074);
    private static Controller instance;

    private final JFrame frame = new JFrame();
    private final DisplayPanel displayPanel = new DisplayPanel(this);
    private final InfoPanel infoPanel = new InfoPanel(this);
    private int score;
    private final Set<Integer> activeKeys = new HashSet<>();


    // Elements du jeu
    private final Ship player;
    /*private final Ship blackEnemy;
    private final Ship blueEnemy;
    private final Ship greenEnemy;
    private final Ship orangeEnemy;*/
    private boolean playerMoving;
    private final LinkedList<Ship> enemies = new LinkedList<>();
    private final LinkedList<GameObject> decorElements = new LinkedList<>();
    private final LinkedList<GameObject> shots = new LinkedList<>();


    // Prototypes
    private final LinkedList<Ship> shipPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();


    private Controller() {
        this.player = new Player(new Point(231, 920), new Point(0,0), new Dimension(50,50), 500);
        //this.blackEnemy  = new Enemy()
        //this.blueEnemy   = new Enemy()
        //this.greenEnemy  = new Enemy()
        //this.orangeEnemy = new Enemy()


        // Initialise les frames et panels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(displayPanel, BorderLayout.CENTER);
        frame.setPreferredSize(DIMENSION);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        initializePrototypes();

        int speed = 0;
        int pause = 0;

        // Initialise le timer de jeu
        new Timer(5, evt -> update()).start();
        new Timer(1000, evt -> spawnGameObject()).start();

        movePlayer();
    }

    /**
     * Fonction apelée à chaque frame
     */
    private void update() {

        // Pour chaque asteroide, le supprime s'il est hors de l'interface
        decorElements.removeIf(decor -> decor.isOutOf(frame.getHeight()));

        // Pour chaque tir, le supprime s'il est hors de l'interface
        shots.removeIf(shot -> shot.isOutOf(frame.getHeight()));

        // Freine le joueur
        if (!playerMoving)
            breakPlayer();

        // Bouge le joueur
        if (player.getPosition().getX() + player.getMovementVector().getX() >= 0
                && player.getPosition().getX() + player.getSize().getWidth() + player.getMovementVector().getX() <= displayPanel.getWidth())
            player.move();

        // Bouge les lasers
        for(GameObject shot : shots){
            shot.move();
        }

        // Bouge les asteroides
        for(GameObject decor : decorElements){
            decor.move();
        }



        // Applique les mouvements
        applyKeys();

        displayPanel.repaint();
    }

    private void spawnGameObject() {
        // Spawn les clones d'asteroides (aléatoirement)
        spawnAsteroids();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();

        return instance;
    }

    private void initializePrototypes() {
        // Initialiser tous les prototypes
        asteroidsPrototypes.add(new Asteroid(new Dimension(90,90), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(75,75), new Point(0, 4)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(50,50), new Point(0, 2)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(25,25), new Point(0, 4)));
    }

    /**
     * Créer des clones des prototypes d'asteroides de maniere aleatoire
     * */
    private void spawnAsteroids(){
        int index = RandomGenerator.randomInt(0, asteroidsPrototypes.size() - 1);
        GameObject copy = asteroidsPrototypes.get(index).clone();
        copy.randomizePositionOnX(frame.getWidth());
        decorElements.add(copy);
    }

    public int score() {
        return score;
    }

    public void movePlayer() {
        // Gestion
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerMoving = true;
                int key = Character.toUpperCase(e.getKeyChar());
                System.out.println("KEY PRESSED : " + key);
                activeKeys.add(key);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = Character.toUpperCase(e.getKeyChar());
                System.out.println("KEY UNPRESSED : " + key);
                System.out.println(activeKeys);
                activeKeys.remove(key);
                playerMoving = false;
            }
        });
    }

    private void applyKeys() {
        for (int key : activeKeys) {
            switch (key) {
                case KeyEvent.VK_SPACE:
                    Shot shot = player.fire();
                    if (shot != null) shots.add(shot);
                    break;

                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    //move down
                    player.setMovementVector(new Point(0,10));
                    break;

                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    //move up
                    player.setMovementVector(new Point(0,-10));
                    break;

                // move left
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    player.setMovementVector(new Point(-8,0));
                    break;

                // move right
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    player.setMovementVector(new Point(8,0));
                    break;

                // Exit program
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
    }

    private void breakPlayer() {
        if (player.getMovementVector().getX() > 0) {
            player.setMovementVector(new Point((int) player.getMovementVector().getX() - 1, 0));
        } else if (player.getMovementVector().getX() < 0) {
            player.setMovementVector(new Point((int) player.getMovementVector().getX() + 1, 0));
        }

        if (player.getMovementVector().getY() > 0) {
            player.setMovementVector(new Point(0, (int) player.getMovementVector().getY() - 1));
        } else if (player.getMovementVector().getY() < 0) {
            player.setMovementVector(new Point(0, (int) player.getMovementVector().getY() + 1));
        }
    }

    /**
     * Retourne tous les objets du jeu sous forme de GameObject
     * Cette méthode est apellée pour l'affichage
     * @return Une liste de GameObject
     */
    public List<GameObject> getAllGameObjects() {
        List<GameObject> gameObjects = new LinkedList<>();
        gameObjects.add(player);
        gameObjects.addAll(decorElements);
        gameObjects.addAll(shots);
        return gameObjects;
    }
}
