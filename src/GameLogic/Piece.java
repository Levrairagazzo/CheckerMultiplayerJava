package GameLogic;

import GameLogic.Players.Player;

public class Piece {


    private Player player;
    private Tile tile;
    private int direction;  //1 = top, -1 = bottom
    private boolean kinged;
    private boolean selected;


    private int currentRow, currentColumn;

    public Piece(Player player, Tile tile, int direction) {
        this.player = player;
        this.tile = tile;
        this.direction = direction;
        this.selected = false;
        kinged = false;
    }

    public boolean canCapture(Piece other) {
        return !other.getPlayer().equals(this.player);
    }

    public boolean canMoveTo(Tile toTile) {
        int toRow = toTile.getRow();
        int toColumn = toTile.getColumn();

        if (toColumn == tile.getColumn() || toRow == tile.getRow()) {
            return false;

        }
        if (isKinged() || direction == 1) { //bottom
            if ((toColumn == tile.column + 1 || toColumn == tile.getColumn() - 1) && (toRow == tile.getRow() - 1)) {
//                System.out.println("test");
                return true;
            }
        }

        if (isKinged() || direction == 0) { // top
            if ((toColumn == tile.column + 1 || toColumn == tile.getColumn() - 1) && (toRow == tile.getRow() + 1)) {
//                System.out.println("test");
                return true;
            }
        }

        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isKinged() {
        return kinged;
    }

    public void kingPiece() {
        this.kinged = true;
    }

    public Tile getTile() {
        return this.tile;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int[] getCoordinate() {
        return new int[]{tile.getRow(), tile.getColumn()};
    }

    public int getDirection() {
        return direction;
    }

}
