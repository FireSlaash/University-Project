package model;

/* Classe pour gérer les collisions */
public class Collision extends Thread{
    public static final int DELAY = 20;

    /* attribut monParcours */
    private final Parcours monParcours;

    /* la vie */
    private final Vie maVie;

    /* la position */
    private final Position maPosition;

    /* constructeur */
    public Collision(Position p) {
        monParcours = p.getParcours();
        maVie = p.getVie();
        maPosition = p;
        this.start();
    }


    /* Thread pour gérer les collisions */
    @Override
    public void run() {
        while (maPosition.isEnCours()) {
            /* si collision détectée*/
            if (!monParcours.detecterCollision()) {

                /* fait perdre une vie */
                maVie.perdreVie();
                /* donner 1 seconde pour bien se replacer sinon reperdre une vie */
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /* si plus de vie, fin du jeu */
                if (!maVie.aDesVies()) {
                    maPosition.gameOver();
                }
            }
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
