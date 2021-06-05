package gameEngine.panels;

import gameEngine.Controller;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private static final Dimension DIMENSION = new Dimension(576, 50);
    private final Controller controller;

    public InfoPanel(Controller controller) {
        this.controller = controller;

        // Configure le panel
        setBackground(Color.BLACK);
        setPreferredSize(DIMENSION);

        // Configure le placement des éléments d'interface
        setLayout(new GridLayout(1, 2));

        // Configure les zones de texte
        JLabel life = new JLabel("LIVES : 10", JLabel.CENTER);
        JLabel score = new JLabel("SCORE : 0", JLabel.CENTER);
        life.setForeground(Color.WHITE);
        score.setForeground(Color.WHITE);

        // Ajoute les textes au panel
        score.add(new JLabel(String.valueOf(controller.score())));
        add(life);
        add(score);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
