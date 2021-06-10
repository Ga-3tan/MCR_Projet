package gameEngine;

import entities.GameObject;
import entities.ships.Player;
import gameEngine.panels.DisplayPanel;
import gameEngine.panels.InfoPanel;
import entities.ships.Ship;
import entities.Asteroid;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.*;


public class Controller{
    private static final Dimension DIMENSION = new Dimension(512, 1074);
    private static Controller instance;

    private final JFrame frame = new JFrame();
    private final DisplayPanel displayPanel = new DisplayPanel(this);
    private final InfoPanel infoPanel = new InfoPanel(this);
    private int score;


    // Elements du jeu
    private final Ship player;
    private final LinkedList<Ship> enemies = new LinkedList<>();
    private final LinkedList<GameObject> decorElements = new LinkedList<>();

    // Prototypes
    private final LinkedList<Ship> shipPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();


    private Controller() {
        this.player = new Player(new Point(231, 920), new Point(0,0), new Dimension(50,50), 500);

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
        int delay = 10; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                // Spawn les clones d'asteroides (aléatoirement)
                spawnAsteroids();
                // Pour chaque asteroide, le supprime s'il est hors de l'interface
                decorElements.removeIf(decor -> decor.isOutOf(frame.getHeight()));
                // Bouge les asteroides
                for(GameObject decor : decorElements){
                    decor.move();
                }

                displayPanel.repaint();
            }
        };
        new Timer(delay, taskPerformer).start();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();

        return instance;
    }

    private void initializePrototypes() {
        // initialiser tous les prototypes
//        asteroid = new Asteroid();
        asteroidsPrototypes.add(new Asteroid(new Dimension(50,50)));
        asteroidsPrototypes.add(new Asteroid(new Dimension(25,25)));
    }

    /**
     * Créer des clones des prototypes d'asteroides de maniere aleatoire
     * */
    private void spawnAsteroids(){

        int chanceToSpawn = 5; // 5/1000, toutes les 10 milisecondes -> delay du Timer

        for(Asteroid asteroidPrototype : asteroidsPrototypes){
            Random rand = new Random();
            int randomSpawn = rand.nextInt(1000);
            if(randomSpawn < chanceToSpawn) {

                GameObject copy = asteroidPrototype.clone();
                copy.randomizePositionOnX(frame.getWidth());
                copy.randomizeSpeed();

                decorElements.add(copy);
            }
        }
    }

    public int score() {
        return score;
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
        return gameObjects;
    }

}