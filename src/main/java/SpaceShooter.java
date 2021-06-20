import gameEngine.Controller;
import lombok.Getter;
import lombok.Setter;

/**
 * Point d'entrée de l'application, démarre une instance du jeu
 *
 * Name     : RandomGenerator
 * File     : RandomGenerator.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
@Getter
@Setter
public class SpaceShooter {
    public static void main(String[] args) {
        Controller.getInstance().initController();
    }
}

