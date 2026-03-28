package model;

/* Classe Avancer pour gérer le défilement de l'écran */
public class Avancer extends Thread{
    /* délai de déplacement */
    public static final int DELAY = 100;

    /* la position */
    private final Position maPosition;

    /* le parcours */
    private final Parcours monParcours;

    /* Récupérer la position */

    public Avancer(Position p, Parcours parcours) {
        /* Récupération des modèles */
        maPosition = p;
        monParcours = parcours;
        this.start();
    }

    /* Thread pour faire avancer l'écran */
    @Override
    public void run() {

        while (maPosition.isEnCours()) {
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /* Fait avancer la position */
            maPosition.avancer(1);
            /* Met à jour le parcours */
            monParcours.verifierPoints();
        }
    }


}
