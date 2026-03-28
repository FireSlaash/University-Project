package view;

import model.Position;

/* Thread qui redessine l'affichage à intervalles réguliers */
public class Redessine extends Thread{

    /* délai de rafraichissement */
    public static final int DELAY = 30;

    /* l'affichage */
    private Affichage monAffichage;

    /* la position */
    private final Position maPosition;

    /* constructeur */
    public Redessine(Affichage a, Position p){
        /* Récupération des modèles */
        maPosition = p;
        monAffichage = a;
        this.start();
    }

    /* redéfinition de la méthode run */
    @Override
    public void run(){
        while (maPosition.isEnCours()) {
            monAffichage.revalidate();
            monAffichage.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /* une fois que la partie est terminée, on redessine une dernière fois pour afficher le message de fin de partie */
        monAffichage.revalidate();
        monAffichage.repaint();
    }
}
