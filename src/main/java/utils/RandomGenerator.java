package utils;

import java.util.Random;

/**
 * Classe permettant de générer un entier aléatoire compris entre 2 bornes
 */
public class RandomGenerator {

    /**
     * Génère un entier aléatoire entre 2 bornes.
     * @param min Borne minimale
     * @param max Borne maximale
     * @return entier aléatoire entre les 2 bornes
     */
    public static int randomInt(int min, int max) {
        if(max < min || (min == 0 && max == 0))
            throw new IllegalArgumentException("Illegal interval");
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}