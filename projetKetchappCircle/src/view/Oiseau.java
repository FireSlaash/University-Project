package view;


import java.util.Random;


public class Oiseau {


    /* Vitesse de déplacement de l'oiseau */
    private int vitesseAleatoire = random.nextInt(10) + 1;

    /* On munit notre classe d'un générateur aléatoire dans une constante de type java.util.Random */
    private static final Random random = new Random();

    /* Position de l'oiseau */
    /* Initialisée à la position de départ (hors de la fenêtre) + un nombre aléatoire pour pas qu'ils soient tous au même endroit */
    private int positionX = Affichage.LARGEUR + 20 + random.nextInt(50) ; // Commence à droite de la fenêtre

    /* Position Y aléatoire sur la moitié haute de la fenêtre */
    private int positionY = random.nextInt(Affichage.HAUTEUR / 2);

    /* Index de l'image pour l'animation de l'oiseau */
    private int frameIndex = 0;
    private int frameTimer = 0;

    /* Angle de rotation pour la Lune*/
    private double angle = 0;


    /* Avancer l'oiseau en fonction de sa vitesse */
    public void avancer() {
        positionX -= vitesseAleatoire; // Déplace l'oiseau vers la gauche
        /* Gérer l'animation de l'oiseau en fonction de la vitesse*/
        frameTimer += vitesseAleatoire;
        frameIndex = (frameTimer / 20) % 2; // Change d'image toutes les 10 unités de temps
    }

    /* Getters pour la position de l'oiseau */
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    /* Méthode pour actualiser la position si l'oiseau sort de la fenêtre */
    public void resetPosition() {
        if (positionX < 0) { // Si l'oiseau sort de la fenêtre à gauche
            positionX = Affichage.LARGEUR + 20; // Réinitialise à droite de la fenêtre
            positionY = random.nextInt(Affichage.HAUTEUR / 2); // Nouvelle position Y aléatoire
            vitesseAleatoire = random.nextInt(10) + 1; // Nouvelle vitesse aléatoire
        }
    }

    /* Getter pour l'angle de la Lune */
    public double getAngle() {
        return angle;
    }

    /* Méthode pour faire tourner la Lune */
    public void tournerLune() {
        angle += 0.02;
    }


}

