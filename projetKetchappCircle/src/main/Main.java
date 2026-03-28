package main; /** La classe principale de ce projet */
import control.ReactionClic;
import model.Position;
import view.Affichage;

public class Main {
    /** La méthode de lancement du programme */
    public static void main(String [] args) {

        /* Modèle */
        Position p = new Position();

        /* Vue */
        Affichage a = new Affichage(p);

        /* Contrôleur */
        new ReactionClic(a,p);

    }
}