package gameEngine;

import entities.Prototype;
import entities.ships.Player;
import gameEngine.panels.DisplayPanel;
import gameEngine.panels.InfoPanel;
import entities.ships.Ship;
import entities.Asteroid;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.Timer;


public class Controller{

    private static Controller instance;

    private final JFrame frame = new JFrame();
    private final DisplayPanel displayPanel = new DisplayPanel(this);
    private final InfoPanel infoPanel = new InfoPanel(this);
   // private final Timer gameTimer;
    private int score;
    private static final Dimension DIMENSION = new Dimension(576, 1024);

    // Elements du jeu
    private final Ship player;
    private final LinkedList<Ship> enemies = new LinkedList<>();

    // Prototypes
    private final LinkedList<Ship> shipPrototypes = new LinkedList<>();
    private final LinkedList<Asteroid> asteroidsPrototypes = new LinkedList<>();
    private Asteroid asteroid;

    private Controller() {
        this.player = new Player(new Point(0,0), new Point(0,0),new Dimension(10,10), 500);

        // Initialise les frames et panels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1));
        frame.add(infoPanel);
        frame.add(displayPanel);

        frame.pack();
        frame.setPreferredSize(DIMENSION);
        frame.setVisible(true);

        initializePrototypes();

//        int speed = 0;
//        int pause = 0;
//        // Initialise le timer de jeu
//        gameTimer = new Timer(speed, this);
//        gameTimer.setInitialDelay(pause);
//        gameTimer.start();
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

}