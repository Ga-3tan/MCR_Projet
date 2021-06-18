package gameEngine.panels;

import gameEngine.Controller;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private static final Dimension DIMENSION = new Dimension(576, 50);
    private final Controller controller;
    JLabel score;
    JLabel life;

    /**
     * Paint le Component
     * @param controller
     */
    public InfoPanel(Controller controller) {
        this.controller = controller;

        // Configure le panel
        setBackground(Color.BLACK);
        setPreferredSize(DIMENSION);

        // Configure le placement des éléments d'interface
        setLayout(new GridLayout(1, 2));

        // Configure les zones de texte
        life = new JLabel("LIVES : 10" , JLabel.CENTER);
        score = new JLabel("SCORE : " + controller.getScore(), JLabel.CENTER);
        life.setForeground(Color.WHITE);
        score.setForeground(Color.WHITE);

        // Ajoute les textes au panel
        //score.setText(new JLabel( String.valueOf(controller.getScore())));
        add(life);
        add(score);
    }

    /**
     * Paint le Component
     * @param g graphics où peindre
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        life.setText("LIVES : " + controller.getPlayer().getHp());
        score.setText("SCORE : " + controller.getScore());

    }
}
