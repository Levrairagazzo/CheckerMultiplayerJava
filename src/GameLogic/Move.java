/*
Encapsulates a move that a Piece can do on the checkerboard
 */


package GameLogic;

import java.io.Serializable;

public class Move implements Serializable {
    Tile originalTile, finalTile;
    int[] moveCoordinate;
    boolean doubleMove;
    boolean shouldBeKinged;

    public Move(Tile t1, Tile t2) {
        originalTile = t1;
        finalTile = t2;
        initCoordinates();
        this.shouldBeKinged = false;
    }


    private void initCoordinates() {

        int rowMove = finalTile.getRow() - originalTile.getRow();
        int columnMove = finalTile.getColumn() - originalTile.getColumn();

        moveCoordinate = new int[]{rowMove, columnMove};
    }

    public int[] getMoveCoordinate() {
        return moveCoordinate;
    }

    public Tile getOriginalTile() {
        return originalTile;
    }

    public Tile getFinalTile() {
        return finalTile;
    }

    public boolean isDoubleMove() {
        return doubleMove;
    }

    public void setDoubleMove() {
        doubleMove = true;
    }

    public void isKinged() {
        if (originalTile.getOccupant().isKinged())
            shouldBeKinged = true;
    }

    public boolean getShouldBeKinged() {
        return shouldBeKinged;
    }

    @Override
    public String toString() {

        return "[" + originalTile.getRow() + ", " + originalTile.getColumn() + "]"+
                "[" + finalTile.getRow() + ", " + finalTile.getColumn() + "]";
    }

    public Move update(int[] updateCoordinates){
        Tile potentialFinalTile = new Tile(finalTile.getRow()+ updateCoordinates[0], finalTile.getColumn() + updateCoordinates[1]);
        return new Move(originalTile, potentialFinalTile);
    }

}
