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

    // Prototypes
    private final LinkedList<Ship> shipPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();
    private Asteroid asteroid;

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
        asteroidsPrototypes.add(asteroid);
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
        return gameObjects;
    }

}