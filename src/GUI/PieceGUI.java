/*
Extends JComponent and creates the UI representation of a piece. Can be kinged if needed.
 */
package GUI;

import GameLogic.Piece;
import GameLogic.Players.Player;
import GameLogic.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

public class PieceGUI extends JComponent {

    protected Color color;
    protected int diameter;
    protected boolean kinged;
    public boolean currentlySelected;

    private Player player;
    private Tile tile;
    private int direction;
    private int currentRow, currentColumn;


    public PieceGUI(Color color, boolean kinged) {
        this.color = color;
        this.kinged = kinged;     //modification temporaire
        this.diameter = 50;
    }


    public Dimension getPreferredSize() {
        return new Dimension(this.diameter, this.diameter);
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.yellow);
        if (this.kinged)
            graphics.fillOval(0, 0, this.diameter, this.diameter);
        int offset = 3;
        int innerDiameter = this.diameter - 2 * offset;
        graphics.setColor(this.color);
        graphics.fillOval(offset, offset, innerDiameter, innerDiameter);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(offset, offset, innerDiameter - 1, innerDiameter - 1);
    }


    public void switchColor(Color playerColor){
        if(this.color.equals(Color.pink))
            this.color = playerColor;
        else
            this.color = Color.pink;
        repaint();
    }




    public boolean canCapture(Piece other) {
        return !other.getPlayer().equals(this.player);
    }

    public boolean canMoveTo(Tile tile) {
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

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }
}


