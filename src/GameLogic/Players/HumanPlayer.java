/*
Implements all the necessary function needed for the human player to play the game.
 */

package GameLogic.Players;

import GameLogic.Move;
import GameLogic.Piece;
import GameLogic.Tile;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class HumanPlayer extends Player{
    boolean moveChosen;

    public HumanPlayer (){
        super();
    }

    public Move chooseMove() throws InterruptedException {
        moveChosen = false;
        try {

            while (!moveChosen) {
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("potential move -> " + potentialMove);
        return potentialMove;
    }

    public void mouseClicked(MouseEvent e) {
        JPanel source = (JPanel) e.getSource();
        int columnClicked = source.getX() / myFrame.getTileWidth();
        int rowClicked = source.getY() / myFrame.getTileHeight();
        Tile tileClicked = getGame().getTile(rowClicked, columnClicked);
        Piece myPiece = tileClicked.getOccupant();


        if (tileClicked.isOccupied() && myPiece.isSelected()) {
            clickedOnSelectedPiece(source, tileClicked, myPiece);
        } else if (tileClicked.isOccupied() && !myPiece.isSelected() && !currentGame.isPieceSelected()) {
            clickedOnUnSelectedPiece(source, tileClicked, myPiece);
        }

        if (!tileClicked.isOccupied() && currentGame.isPieceSelected()) {
            Tile tileSelected = currentGame.getPieceSelectedLocation();
            potentialMove = new Move(tileSelected, tileClicked);
            moveChosen = true;

        }


    }


}
