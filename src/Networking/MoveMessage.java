package Networking;

import GameLogic.Move;
import GameLogic.Tile;

import java.io.Serializable;

public class MoveMessage implements Serializable {
    int fromRow, fromCol, toRow, toCol;
    boolean quit;
    boolean kinged;

    public MoveMessage(Move move, boolean playerQuit) {
        fromRow = move.getOriginalTile().getRow();
        toRow = move.getFinalTile().getRow();
        fromCol = move.getOriginalTile().getColumn();
        toCol = move.getFinalTile().getColumn();
        quit = playerQuit;
        kinged = move.getShouldBeKinged();
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public boolean isQuit() {
        return quit;
    }

    public Move generateMove() {
        return new Move(new Tile(fromRow, fromCol), new Tile(toRow, toCol));
    }

//        public Move generateMove() {
//        return new Move(fromTile, toTile);
//    }

}
