package view;

import java.util.ArrayList;

public class OiseauAvancer extends Thread {

    /* délai de déplacement */
    public static final int DELAY = 30;


    /* la liste des oiseaux */
    private ArrayList<Oiseau> listeOiseau;

    /* constructeur */
    public OiseauAvancer(ArrayList<Oiseau> Oiseaux){
        listeOiseau = Oiseaux;
        this.start();
    }

    /*redéfinition de la méthode run*/
    @Override
    public void run() {
        while (true) {
            for (Oiseau oiseau : listeOiseau) {;
                /* faire avancer l'oiseau */
                oiseau.avancer();
                /* si l'oiseau sort de la fenêtre, le remettre à sa position de départ */
                oiseau.resetPosition();
                /* faire tourner la lune */
                oiseau.tournerLune();

            }
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
