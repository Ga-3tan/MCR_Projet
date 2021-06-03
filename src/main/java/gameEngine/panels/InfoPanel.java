package gameEngine.panels;

import gameEngine.Controller;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final Controller controller;

    public InfoPanel(Controller controller) {
        this.controller = controller;

        setLayout(new FlowLayout());
        JPanel life = new JPanel();
        JPanel score = new JPanel();
        // ajouter les images de vie au panel life
        score.add(new JLabel(String.valueOf(controller.score())));
        add(life);
        add(score);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
