/*
Tile encapsulate all the information needed for the tiles on the board to interact with the game. This is purely game logic
and does not directly with the UI.
 */
package GameLogic;

public class Tile {

    public int column,row;
    private Piece occupant;
    private boolean occupied;


    public Tile(int row, int col){

        this.column = col;
        this.row = row;
        occupied = false;
    }

    public Piece getOccupant() {
    return occupant;
    }

    public void setOccupant(Piece occupant1){
        this.occupant = occupant1;
        occupied = true;
    }
    public void removeOccupant(){
        this.occupant = null;
        occupied = false;
    }
    public boolean isOccupied(){
        return occupied;
    }

    public int getColumn(){
        return column;
    }
    public int getRow(){
        return row;
    }

}
