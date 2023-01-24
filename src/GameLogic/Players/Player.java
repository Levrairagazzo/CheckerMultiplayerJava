/*
This abstract class implements all methods and variables share by all types of player.
 */

package GameLogic.Players;

import GUI.BoardFrame;
import GUI.PieceGUI;
import GameLogic.Game;
import GameLogic.Move;
import GameLogic.Piece;
import GameLogic.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player {
    Game currentGame;
    BoardFrame myFrame;
    Color color;
    int direction;
    public ArrayList<Piece> piecesList;
    Move potentialMove;


    public Player() {
        piecesList = new ArrayList<>();
    }


    public void executePlayerMove(Move move) {

        Tile originalTile = getTileFromBoard(move.getOriginalTile().getRow(), move.getOriginalTile().getColumn());
        Tile finalTile = getTileFromBoard(move.getFinalTile().getRow(), move.getFinalTile().getColumn());

        boolean pieceShouldBeKinged = false;

        if (originalTile.getOccupant().isKinged() || (finalTile.getRow() == 0 && this.direction == 1) ||
                (finalTile.getRow() == 7 && this.direction == 0)) {
            pieceShouldBeKinged = true;
        }

        currentGame.removePieceFromPlayerList(originalTile.getOccupant());
        myFrame.getTilePanels()[originalTile.getRow()][originalTile.getColumn()].removeAll();
        myFrame.getTilePanels()[finalTile.getRow()][finalTile.getColumn()].revalidate();
        addPieceToGame(finalTile, pieceShouldBeKinged);

    }

    private Tile getTileFromBoard(int tileRow, int tileColumn) {
        return currentGame.getBoard()[tileRow][tileColumn];
    }


    public Game getGame() {
        return currentGame;
    }

    public void setGame(Game game) {
        currentGame = game;
    }


    public int getDirection() {
        return direction;
    }

    public int getNumPieces() {
        return piecesList.size();
    }


    public void setColor(Color updatedColor) {
        this.color = updatedColor;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setMyFrame(BoardFrame newFrame) {
        this.myFrame = newFrame;

    }


    public BoardFrame getMyFrame() {
        return myFrame;
    }


    public Color getColor() {
        return color;
    }

    public Move chooseMove() throws InterruptedException {
        return null;
    }

    public void addPieceToGame(Tile initialPosition, boolean kinged) {
        Piece myPiece = new Piece(this, initialPosition, direction);
        if (kinged) {
            myPiece.kingPiece();
        }

        currentGame.getBoard()[initialPosition.getRow()][initialPosition.getColumn()].setOccupant(myPiece);
        piecesList.add(myPiece);
        myFrame.addPieceToFrame(color, myPiece);

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
            if (currentGame.isLegalMove(potentialMove)) {
                currentGame.executeGameMove(potentialMove);

            }
        }

    }

    void clickedOnSelectedPiece(JPanel source, Tile tileClicked, Piece pieceClicked) {
        PieceGUI p1 = (PieceGUI) source.getComponents()[0];
        p1.switchColor(this.color);
        currentGame.setPieceSelected(false);
        pieceClicked.setSelected(false);
    }

    void clickedOnUnSelectedPiece(JPanel source, Tile tileClicked, Piece myPiece) {

        PieceGUI p1 = (PieceGUI) source.getComponents()[0];
        if (myPiece.getPlayer().equals(currentGame.getCurrentPlayer())) {
            p1.switchColor(this.color);
            currentGame.setPieceSelected(true);
            currentGame.setPieceSelectedLocation(tileClicked);
            myPiece.setSelected(true);
        }


    }

    public void removePieceFromList(Piece pieceTaken) {
        piecesList.remove(pieceTaken);
    }

    public ArrayList<Piece> getPiecesList() {
        return piecesList;
    }

    public boolean equals(Player otherPlayer) {
        return (otherPlayer.getDirection() == this.getDirection());
    }
}
