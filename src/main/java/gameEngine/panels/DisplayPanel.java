package gameEngine.panels;

import entities.GameObject;
import gameEngine.Controller;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 * Pannel ou sont affichés tous les éléments (GameObject) du jeu
 * et le background
 *
 * Name     : DisplayPanel
 * File     : DisplayPanel.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public class DisplayPanel extends JPanel {
    private static final Image BACKGROUND = Toolkit.getDefaultToolkit()
            .getImage(Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("images/backgrounds/background_loop.png"));
    private static final Dimension DIMENTION = new Dimension(512, 1024);
    private static final int BACKGROUND_SPEED = 1;
    private final Controller controller;
    private final int[] backgroundYPositions = new int[2];

    /**
     * Constructeur du DisplayPanel
     *
     * @param controller
     */
    public DisplayPanel(Controller controller) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Initialisation des positions du background
        backgroundYPositions[0] = 0;
        backgroundYPositions[1] = -DIMENTION.height;
    }

    /**
     * Paint le Component
     *
     * @param g graphics où peindre
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Moves the background
        g.drawImage(BACKGROUND, 0, backgroundYPositions[0] += BACKGROUND_SPEED, this);
        g.drawImage(BACKGROUND, 0, backgroundYPositions[1] += BACKGROUND_SPEED, this);
        updateBackgroundPositions();

        // Displays all the game objects
        List<GameObject> gameObjects = controller.getAllGameObjects();

        for (GameObject o : gameObjects) {
            g.drawImage(o.getSprite(), o.getPosition().x, o.getPosition().y, this);
        }
    }

    /**
     * Met à jour la position du background (pour le faire défiler)
     */
    private void updateBackgroundPositions() {
        // Checks for background position reset
        if (backgroundYPositions[0] > DIMENTION.height)
            backgroundYPositions[0] = backgroundYPositions[1] - DIMENTION.height;
        else if (backgroundYPositions[1] > DIMENTION.height)
            backgroundYPositions[1] = backgroundYPositions[0] - DIMENTION.height;
    }
}
