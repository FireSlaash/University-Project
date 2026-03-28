package model;

import view.AnimationCollision;

/* Classe principale du Modèle */
/* Classe qui gère la position verticale et l'avancement horizontal de l'ovale */
public class Position {

    /* BOOLEEN QUI GERE LE DEROULEMENT DU JEU */
    public static boolean enCours = true;


    /* Constantes pour l'ovale */
    /* la position de départ (ça sera le haut de l'ovale)*/
    public static final int HAUT_DEPART = 0 ;

    /* la hauteur de l'ovale */
    public static final int HAUT_OVAL = 10;

    /* la hauteur maximale pour l'ovale */
    public static final int H_MAX = 50 ;

    /* la hauteur minimale pour l'ovale */
    public static final int H_MIN = -20 ;

    /* la hauteur d'un saut / impulsion */
    public static final double SAUT = 1.5;

    /* fenêtre virtuelle largeur avant l'ovale */
    public static final int BEFORE = 50;

    /* fenêtre virtuelle largeur après l'ovale */
    public static final int AFTER = 200;

    /* gravité */
    public static final double GRAVITE = 0.2;

    /* vitesse */
    private double vitesse = 0.0;

    /* mon attribut position qui correspond à la hauteur de l'ovale */
    private double position = HAUT_DEPART;

    /* mon attribut avancement qui correspond à la position en X de l'ovale */
    private int avancement = BEFORE;

    /* mon attribut score qui correspond au score du joueur */
    private int score = 0;


    /* --------------------------------------------------------------- */

    /* Attribut des autres classes de modèle */
    /* Parcours pour la ligne brisée */
    private Parcours pa;

    /* Vie pour la vie du joueur */
     private Vie vie;

    /* --------------------------------------------------------------- */
    /* Constructeur de la classe Position */
    public Position() {

        /* Initialiser le parcours */
        pa = new Parcours(this);

        /* Initialiser la vie */
        vie = new Vie();

        /* Initialiser le thread pour la collision */
        new Collision(this);

        /* Initialiser le thread pour faire avancer l'ovale */
        new Avancer(this, pa);

        /* Initialiser le thread pour la chute de l'ovale */
        new Descendre(this);
    }
    /* --------------------------------------------------------------- */
    /* Méthodes */
    /* Getter pour récupérer le parcours */
    public Parcours getParcours(){
        return pa;
    }

    /* Getter pour récupérer la vie */
    public Vie getVie(){
        return vie;
    }

    /* Getter pour récupérer la position de l'ovale */
    public int getPosition(){
        return (int) Math.round(position);
    }

    /* Getter pour récupérer l'avancement */
    public int getAvancement(){
        return avancement;
    }

    /* Getter pour récupérer le score */
    public int getScore(){
        return score;
    }

    /* Getter pour savoir si le jeu est en cours */
    public boolean isEnCours(){
        return enCours;
    }

    /* Setter pour faire monter la position */
    public void jump(){

        /* Mettre la vitesse à SAUT */
        vitesse = SAUT;

    }

    /* Setter pour faire descendre la position */
    public void move(){

        /* Appliquer la gravité à la vitesse */
        vitesse -= GRAVITE;    // gravité

        /* rajouter borne de vitesse max */
        if(vitesse <= -1.4) vitesse = -1.4;

        position += vitesse;   // mouvement fluide

        // limites
        if(position > H_MAX){
            position = H_MAX;
            vitesse = 0;
        }

        if(position + vitesse < 0 ){
            position = 0;
            vitesse = 0;
        }
    }

    /* Setter pour l'avancement */
    public void avancer(int n){
        avancement += n ;
    }

    /* Setter pour augmenter le score */
    public void augmenterScore(){
        score++;
    }

    /* Setter pour finir la partie */
    public void gameOver() {
        enCours = false;
    }

}
