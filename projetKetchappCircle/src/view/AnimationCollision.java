package view;

import model.Parcours;
import model.Position;

/* Thread qui gère l'animation visuelle (clignotement) lors d'une collision */
public class AnimationCollision extends Thread {

    /* délai de clignotement */
    private static final int DELAY = 200; // ms

    /* le parcours */
    private final Parcours monParcours;

    /* la position */
    private final Position maPosition;

    /* constructeur */
    public AnimationCollision(Position p) {
        monParcours = p.getParcours();
        maPosition = p;
        this.start();
    }

    @Override
    public void run() {
        while (maPosition.isEnCours()) {
            if (monParcours.isCollision()) {
                monParcours.toggleBlink();
            } else {
                monParcours.setBlinkOn(false);
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}