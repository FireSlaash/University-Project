package model;

public class Vie {

    /* nombre de vies maximum */
    public static final int MAX_VIES = 5;

    /* attribut nombreVies pour la vie actuelle */
    private int nombreVies = MAX_VIES;

    /* getter */
    public int getNombreVies() {
        return nombreVies;
    }

    /* méthode pour faire perdre une vie */
    public void perdreVie() {
        if (nombreVies > 0) {
            nombreVies--;
        }
    }

    /* méthode pour vérifier s'il reste des vies */
    public boolean aDesVies() {
        return nombreVies > 0;
    }

}
