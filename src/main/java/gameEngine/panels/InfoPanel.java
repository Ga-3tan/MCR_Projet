package gameEngine.panels;

import gameEngine.Controller;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Graphics;

/**
 * Panel ou sont affichées les données de la partie (vie, score)
 *
 * Name     : InfoPanel
 * File     : InfoPanel.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
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
