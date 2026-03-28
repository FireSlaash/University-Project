package view;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Affichage extends JPanel {

    /* -------------------------------- CONSTANTES POUR L'OVALE ---------------------------------- */
    /* Ratio hauteur par rapport au modèle */
    public static final int RATIO_Y = 10 ;

    /* Ratio largeur par rapport au modèle */
    public static final int RATIO_X = 10 ;

    /* largeur de la fenêtre */
    public static final int LARGEUR = (Position.BEFORE + Position.AFTER) * RATIO_X ;
    /* hauteur de la fenêtre */
    public static final int HAUTEUR = (Position.H_MAX - Position.H_MIN) * RATIO_Y;

    /* Largeur de l'ovale */
    public static final int LARG_OVAL = 50;

    /* Hauteur de l'ovale (taille) recalculée */
    public static final int HAUT_OVAL = Position.HAUT_OVAL * RATIO_Y;

    public static final int X_DEPART = Position.BEFORE*RATIO_X - LARG_OVAL/2 ;

    /* -------------------------------- CONSTANTES POUR LES VIES --------------------------------- */
    /* Taille d'un coeur */
    public static final int TAILLE_COEUR = 100;

    /* Marge pour les coeurs par rapport au bord droit de la fenêtre */
    public static final int MARGE_COEUR = 10;



    /* -------------------------------- CONSTANTES POUR LA LUNE ---------------------------------- */
    /* Taille de la lune */
    public static final int TAILLE_LUNE = 160;

    /* Position de la lune */
    public static final int XLUNE = 20;
    public static final int YLUNE = 20;

    /* -------------------------------- CONSTANTES POUR L'OISEAU --------------------------------- */

    /* Taille de l'oiseau */
    public static final int TAILLE_OISEAU = 50;

    /* Nombre d'oiseaux à afficher */
    public static final int NOMBRE_OISEAUX = 10;

    /* -------------------------------- ATTRIBUTS ---------------------------------- */

    /* Le modèle qui donne l'ovale' */
    private Position maPosition;

    /* Le modèle qui donne la ligne brisée */
    private Parcours monParcours;

    /* le modèle de la vie */
    private Vie maVie;

    /* Liste d'oiseaux et Lune */
    private ArrayList<Oiseau> oiseaux;

    /* Images */
    private BufferedImage vieImage;
    private BufferedImage oiseau_haut;
    private BufferedImage oiseau_bas;
    private BufferedImage lune;

    /* Constructeur de la classe Affichage */
    public Affichage(Position p) {

        /* Taille de la fenêtre */
        setPreferredSize(new Dimension(LARGEUR, HAUTEUR));

        /* Récupération des modèles */
        maPosition = p;
        monParcours = p.getParcours();
        maVie = p.getVie();

        /* Charge les images */

        try {
            vieImage = ImageIO.read(new File("src/view/images/vie.png"));
            oiseau_haut = ImageIO.read(new File("src/view/images/pigeon_aile_haut.png"));
            oiseau_bas = ImageIO.read(new File("src/view/images/pigeon_aile_bas.png"));
            lune = ImageIO.read(new File("src/view/images/lune.png"));


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur : Impossible de charger l'image.");
        }

        /* Initialiser la liste d'oiseaux */
        oiseaux = new ArrayList<>();
        for (int i = 0; i < NOMBRE_OISEAUX; i++) {
            Oiseau oiseau = new Oiseau();
            oiseaux.add(oiseau);
        }



        /* Lancer le thread pour redessiner la fenêtre régulièrement */
        new Redessine(this, maPosition);

        /* Lancer le thread pour faire clignoter l'ovale en cas de collision */
        new AnimationCollision(maPosition);


        /* Lancer le thread pour faire avancer les oiseaux */
        new OiseauAvancer(oiseaux);

        /* Fenêtre graphique */
        JFrame maFenetre = new JFrame("Circle");
        /* Empêche de redimensionner la fenêtre pour éviter les problèmes d'affichage */
        maFenetre.setResizable(false);
        /* Ferme le programme quand on clique sur la croix */
        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* Ajoute le panneau d'affichage à la fenêtre, ajuste la taille de la fenêtre et la rend visible */
        maFenetre.add(this);
        maFenetre.pack();
        maFenetre.setVisible(true);


    }

    /* Méthode pour dessiner la ligne brisée */
    private void drawLigne(Graphics g) {

        /* Parcours de la liste de points et dessin des segments */
        for (int i = 0; i < monParcours.getListePoints().size() - 1; i++) {
            /* Récupère les deux points consécutifs */
            Point p1 = monParcours.getListePoints().get(i);
            Point p2 = monParcours.getListePoints().get(i + 1);

            /* Calcul des coordonnées réelles */
            int x1 = p1.x * RATIO_X;
            int y1 = (Position.H_MAX - p1.y) * RATIO_Y;
            int x2 = p2.x * RATIO_X;
            int y2 = (Position.H_MAX - p2.y) * RATIO_Y;

            /* Dessine le segment entre p1 et p2 */
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /* Méthode pour dessiner les vies */
    private void drawVies(Graphics g) {

        /* On calcule le point de départ pour que les coeurs soient en haut à droite*/
        int xDepart = getWidth() - ((maVie.getNombreVies()) * TAILLE_COEUR) - MARGE_COEUR;

        for (int i = 0; i < maVie.getNombreVies(); i++) {
            /* On dessine chaque coeur l'un après l'autre à partir de xDepart */
            g.drawImage(vieImage, xDepart + (i * TAILLE_COEUR), 0, TAILLE_COEUR, TAILLE_COEUR, null);
        }
    }

    /* Méthode pour dessiner les oiseaux */
    private void drawOiseaux(Graphics g) {
        for (Oiseau oiseau : oiseaux) {
            if (oiseau.getFrameIndex() == 1) {
                g.drawImage(oiseau_bas, oiseau.getPositionX(), oiseau.getPositionY(), TAILLE_OISEAU, TAILLE_OISEAU, null);
            } else {
                g.drawImage(oiseau_haut, oiseau.getPositionX(), oiseau.getPositionY(), TAILLE_OISEAU, TAILLE_OISEAU, null);
            }
        }
    }

    /* Méthode pour dessiner la lune */
    private void drawLune(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


        /* On sauvegarde l'état actuel (pour ne pas faire tourner tout le jeu !) */
        AffineTransform vieuxReglage = g2d.getTransform();

        /* On déplace le "pinceau" au centre de la lune */
        g2d.translate(XLUNE + TAILLE_LUNE / 2, YLUNE + TAILLE_LUNE / 2);

        /* On fait tourner le pinceau */
        g2d.rotate(oiseaux.get(0).getAngle());

        /* On dessine la lune (en centrant l'image sur le nouveau point 0,0) */
        g2d.drawImage(lune, -TAILLE_LUNE / 2, -TAILLE_LUNE / 2, TAILLE_LUNE, TAILLE_LUNE, null);

        /* On remet le pinceau droit pour les dessins suivants */
        g2d.setTransform(vieuxReglage);

    }

    /* Méthode pour dessiner le score */
    private void drawScore(Graphics g) {
        /* Affiche le score en bas à gauche */
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String scoreText = "Score: " + maPosition.getScore();
        g.drawString(scoreText, 10, getHeight() - 10);
    }

    /* Méthode pour dessiner le message de fin de partie */
    public void drawGameOver(Graphics g) {
        /* Affiche "Game Over" au centre de l'écran */
        /* Texte en rouge et plus grand */
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String gameOverText = "Game Over";
        /* Calculer la largeur du texte pour le centrer */
        int textWidth = g.getFontMetrics().stringWidth(gameOverText);
        int x = (getWidth() - textWidth) / 2;
        int y = getHeight() / 2;
        /* Dessiner le texte "Game Over" */
        g.drawString(gameOverText, x, y);

        /* Affiche le score final en dessous */
        String scoreText = "Score Final: " + maPosition.getScore();
        /* Calculer la largeur du texte pour le centrer */
        int scoreTextWidth = g.getFontMetrics().stringWidth(scoreText);
        int scoreX = (getWidth() - scoreTextWidth) / 2;
        int scoreY = y + 50; // 50 pixels en dessous du "Game Over"
        g.drawString(scoreText, scoreX, scoreY);

        /* Affiche un message pour recommencer */
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30   ));
        String restartText = "Redémarrer le programme pour recommencer";
        /* Calculer la largeur du texte pour le centrer */
        int restartTextWidth = g.getFontMetrics().stringWidth(restartText);
        int restartX = (getWidth() - restartTextWidth) / 2;
        int restartY = scoreY + 50; // 50 pixels en dessous du score
        g.drawString(restartText, restartX, restartY);
    }


    /* Méthode pour tout dessiner */

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Appelle la méthode de classe au dessus

        /* background vert clair pour le sol */
        g.setColor(new Color(35, 90, 27));
        g.fillRect(0, HAUTEUR - HAUT_OVAL, LARGEUR, HAUTEUR);

        /* background bleu foncé pour le ciel */
        g.setColor(new Color(0, 0, 153));
        g.fillRect(0,0, LARGEUR, HAUTEUR - HAUT_OVAL);


        /* dessine le score */
        drawScore(g);

        /* dessine la ligne brisée */
        g.setColor(Color.WHITE);
        drawLigne(g);

        /* dessine les vies */
        drawVies(g);

        /* dessine la lune */
        drawLune(g);

        /* dessine les oiseaux */
        drawOiseaux(g);

        /* hauteur de l'ovale par rapport à la fenêtre recalculée */
        int hauteur = (Position.H_MAX - maPosition.getPosition()) * RATIO_Y;

        /* si collision alors dessine l'Ovale en rouge clignotant sinon juste en black */
        if (monParcours.isCollision()) {
            // en collision : clignotement selon l'état de blink
            if (monParcours.isBlinkOn()) g.setColor(Color.RED);
            else g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.BLACK);
        }

        /*augmente l'épaisseur de l'ovale*/
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        /* dessine l'ovale */
        g.drawOval(X_DEPART, hauteur , LARG_OVAL, HAUT_OVAL);

        if (!maPosition.isEnCours()) drawGameOver(g);


    }

}


