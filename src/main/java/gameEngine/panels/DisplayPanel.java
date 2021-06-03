package gameEngine.panels;

import gameEngine.Controller;
import javax.swing.JPanel;
import java.awt.Graphics;

public class DisplayPanel extends JPanel {

    private final Controller controller;

    public DisplayPanel(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
