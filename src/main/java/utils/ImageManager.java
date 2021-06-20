package utils;

import java.awt.*;
import java.net.URL;

/**
 * Classe abstraite utilisée pour récupérer et intéragir avec les images du jeu
 *
 * Name     : ImageManager
 * File     : ImageManager.java
 * JDK      : openjdk java 11.0.9
 * @author Daniel Sciarra
 * @author Rosalie Chhen
 * @author Alessandro Parrino
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 18.06.2021
 */
public abstract class ImageManager {
    /**
     * Permet d'obtenir un objet Image depuis le dossier ressources
     * @param pathAndFileName le chemin de l'image depuis le dossier resources
     * @return L'objet Image récupéré
     */
    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}
