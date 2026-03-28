package model;

/* Classe pour descendre l'ovale automatiquement */
public class Descendre extends Thread {

    /* délai de déplacement */
    public static final int DELAY = 50;

    /*la hauteur*/
    private final Position maPosition;

    /* compteur de ticks pour augmenter le score */
    int ticks = 0;

    /* Récupérer la position */

    public Descendre(Position p) {
        maPosition = p;
        this.start();
    }

    /* Thread pour descendre automatiquement */
    @Override
    public void run() {

        while (maPosition.isEnCours()) {
            maPosition.move();
            ticks ++;
            /* augmenter le score de 1 tous les 20 ticks (une seconde)*/
            if (ticks % 20 == 0) maPosition.augmenterScore();
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}