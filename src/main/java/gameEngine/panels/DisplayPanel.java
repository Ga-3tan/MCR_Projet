package gameEngine.panels;

import entities.GameObject;
import gameEngine.Controller;
import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

public class DisplayPanel extends JPanel {
    private static final Image BACKGROUND = Toolkit.getDefaultToolkit().getImage("images/backgrounds/background_loop.png");
    private static final Dimension DIMENTION = new Dimension(512, 1024);
    private static final int BACKGROUND_SPEED = 1;
    private final Controller controller;
    private final int[] backgroundYPositions = new int[2];

    /**
     *
     * @param controller
     */
    public DisplayPanel(Controller controller) {
        this.controller = controller;
        setBackground(Color.BLACK);

        // Initialisation des positions du background
        backgroundYPositions[0] = 0;
        backgroundYPositions[1] = -DIMENTION.height;
    }

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Moves the background
        g.drawImage(BACKGROUND, 0, backgroundYPositions[0] += BACKGROUND_SPEED,this);
        g.drawImage(BACKGROUND, 0, backgroundYPositions[1] += BACKGROUND_SPEED,this);
        updateBackgroundPositions();

        // Displays all the game objects
        List<GameObject> gameObjects = controller.getAllGameObjects();

        for (GameObject o : gameObjects) {
            g.drawImage(o.getSprite(), o.getPosition().x, o.getPosition().y, this);
        }
    }

    /**
     *
     */
    private void updateBackgroundPositions() {
        // Checks for background position reset
        if (backgroundYPositions[0] > DIMENTION.height)
            backgroundYPositions[0] = backgroundYPositions[1] - DIMENTION.height;
        else if (backgroundYPositions[1] > DIMENTION.height)
            backgroundYPositions[1] = backgroundYPositions[0] - DIMENTION.height;
    }
}
