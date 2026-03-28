package control;

import model.Position;
import view.Affichage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/* Classe pour réagir au clic de la souris */
public class ReactionClic implements MouseListener {

    /* L'affichage */
    public static Affichage monAffichage;

    /* La position */
    private Position maPosition;

    /* Constructeur */
    public ReactionClic(Affichage a, Position p) {
        monAffichage = a;
        maPosition = p;
        a.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (!maPosition.isEnCours()) return;
        /* Lorsque l'on clique, on fait sauter */
        maPosition.jump();

         /* Appliquer immédiatement un pas de mouvement pour éviter le délai du thread Descendre
          et demander un repaint immédiat */
        maPosition.move();
        if (monAffichage != null) monAffichage.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
