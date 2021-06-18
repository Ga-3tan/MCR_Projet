package utils;

import java.util.Random;

/**
 * Classe permettant de générer un entier aléatoire compris entre 2 bornes
 */

/**
 * Classe permettant de générer un entier aléatoire compris entre 2 bornes
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
public class RandomGenerator {

    /**
     * Génère un entier aléatoire entre 2 bornes.
     *
     * @param min Borne minimale
     * @param max Borne maximale
     * @return entier aléatoire entre les 2 bornes
     */
    public static int randomInt(int min, int max) {
        if (max < min)
            throw new IllegalArgumentException("Illegal interval");
        else if (min == 0 && max == 0)
            return 0;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
