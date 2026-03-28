package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/* Classe parcours pour représenter la ligne brisée */
public class Parcours {

    /* Constante pour définir le point minimal d'une ligne */
    public static final int X_MIN = 15;
    /* Constante pour définir le point maximal d'une ligne */
    public static final int X_MAX = 30;

    /* On munit notre classe d'un générateur aléatoire dans une constante de type java.util.Random */
    private static final Random random = new Random();

    /* Variable pour savoir si y'a une collision */
    private boolean collision;

    /* état de clignotement pour l'animation */
    private volatile boolean blinkOn = false;


    /*attribut contenante une liste de points */
    private ArrayList<Point> listePoints;

    /* attribut position */
    private final Position maPosition;

    /* Constructeur de la classe parcours */
    public Parcours(Position p) {

        maPosition = p;

        /*Initialisation de la liste de points */
        listePoints = new ArrayList<Point>();

        /* On fixe le premier point avant le BEFORE à la hauteur de l'ovale au départ */
        listePoints.add(new Point(0, Position.HAUT_DEPART - Position.HAUT_OVAL / 2));
        /* On fixe le deuxième point un peu après le BEFORE à la hauteur de l'ovale au départ */
        listePoints.add(new Point(Position.BEFORE + Position.BEFORE/5 , Position.HAUT_DEPART - Position.HAUT_OVAL/2));

        /* Boucle while pour ajouter des points aléatoires par X croissant, d'écart entre X_MIN et X_MAX */
        int x = Position.BEFORE+ Position.BEFORE/5;

        while (x < Position.AFTER) {
            /* Générer un point aléatoire avec x entre x + X_MIN et x + X_MAX */
            x += X_MIN + random.nextInt(X_MAX - X_MIN + 1);
            /* Générer une hauteur aléatoire entre 10 et 30*/
            int y = 10 + random.nextInt(21);
            /* Ajouter le point à la liste */
            listePoints.add(new Point(x, y));
        }
    }


    /* Getter pour savoir s'il y a une collision */
    public boolean isCollision() {
        return !collision;
    }


    /* Getter pour récupérer la liste de points avec tous les x décalés de - maPosition.getAvancement() */
    public ArrayList<Point> getListePoints() {
        /* Création d'une nouvelle liste de points décalés */
        ArrayList<Point> listeDecalee = new ArrayList<Point>();

        /* Parcours de la liste de points et décalage de chaque point */
        for (Point point : listePoints) {
            listeDecalee.add(new Point(point.x - maPosition.getAvancement() + Position.BEFORE, point.y));
        }
        return listeDecalee;
    }

    /* Méthode pour vérifier les points et en ajouter si nécessaire */
    public void verifierPoints() {
        /* Récupérer le premier point de la liste */
        Point deuxiemePoint = listePoints.get(1);

        /* Si le deuxième point est hors de l'écran */
        if (deuxiemePoint.x - maPosition.getAvancement() + Position.BEFORE < 0) {
            /* Supprimer le premier point */
            listePoints.remove(0);
        }

        /* Récupérer le dernier point de la liste */
        Point dernierPoint = listePoints.get(listePoints.size() - 1);

        /* Si le dernier point est proche de la fin de l'écran (x - avancement < AFTER) */
        if (dernierPoint.x - maPosition.getAvancement() + Position.BEFORE < Position.AFTER) {
            /* Générer un nouveau point aléatoire */
            int x = dernierPoint.x + X_MIN + random.nextInt(X_MAX - X_MIN + 1);
            int y = 10 + random.nextInt(21);
            /* L'ajouter à la liste */
            listePoints.add(new Point(x, y));
        }
    }

    /* Méthode pour détecter la collision entre l'ovale et le parcours */
    public boolean detecterCollision() {
        /* Récupérer la position de l'ovale */
        int ovalX = maPosition.getAvancement();
        int ovalY = maPosition.getPosition();

        /* Créer la fonction de la droite entre les deux points entourant l'ovale en utilisant la formule PENTE = (Y2-Y1)/(X2-X1) */
        for (int i = 0; i < listePoints.size() - 1; i++) {
            /* Récupérer les deux points consécutifs */
            Point p1 = listePoints.get(i);
            Point p2 = listePoints.get(i + 1);


            /* Vérifier si l'ovale est entre p1.x et p2.x */
            if (ovalX >= p1.x && ovalX <= p2.x) {
                double pente = (double) (p2.y - p1.y) / (p2.x - p1.x);

                /* Calculer la hauteur de la droite au niveau de l'ovale avec la formule (X-X1)*PENTE*/
                double ySurDroite = p1.y + (ovalX - p1.x) * pente;

                /* Vérifier si l'ovale touche la droite */
                if ((double)ovalY - (double) Position.HAUT_OVAL <= ySurDroite && (double)ovalY >= ySurDroite) {
                    collision = true;
                    return true;
                }
                else collision = false;
            }
        }
        return false;
    }

    /* Méthodes pour gérer l'animation (clignotement) */
    public synchronized void setBlinkOn(boolean b) {
        blinkOn = b;
    }

    /* Méthode pour inverser l'état de clignotement */
    public synchronized void toggleBlink() {
        blinkOn = !blinkOn;
    }

    /* Getter pour savoir si le clignotement est activé */
    public synchronized boolean isBlinkOn() {
        return blinkOn;
    }


}
