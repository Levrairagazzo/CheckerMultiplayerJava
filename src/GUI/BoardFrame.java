/*
This class extends JFrame and displays a checkerboard using an 8*8 2D array of JPanels. The pieceComponents are to be added
to one of these JPanels. This class does not handle the logic of the game.
 */

package GUI;

import GameLogic.Game;
import GameLogic.Piece;
import GameLogic.Players.HumanPlayer;
import GameLogic.Players.Player;
import GameLogic.Players.RemotePlayer;
import GameLogic.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardFrame extends JFrame {
    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private JPanel main;
    private Tile tiles[][];
    public PieceGUI pieces[][];
    private GridBagConstraints gbc;
    private JPanel[][] tilePanels;
    private Game myGame;

    public BoardFrame() {
        main = new JPanel();
        setTitle("Checker v3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        add(main);
        main.setBackground(Color.blue);
        main.setLayout(new GridLayout(8, 8));
        gbc = new GridBagConstraints();
        initTiles();
        pack();
        pieces = new PieceGUI[8][8];

    }

    private void initTiles() {
        tilePanels = new JPanel[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                JPanel p = new JPanel();
                p.addMouseListener(new PanelMouseListener());
                p.setPreferredSize(new Dimension(75, 75));
                if (row % 2 == 0 && column % 2 == 0 || row % 2 == 1 && column % 2 == 1) {
                    p.setBackground(Color.black);
                } else {
                    p.setBackground(Color.lightGray);
                }
                tilePanels[row][column] = p;
                main.add(p, gbc);
            }

        }
    }

    public void addPieceToFrame(Color color, Piece piece) {
        int row = piece.getTile().getRow();
        int column = piece.getTile().getColumn();
        PieceGUI GUI = new PieceGUI(color, piece.isKinged());
        tilePanels[row][column].add(GUI);
        pieces[row][column] = GUI;
        repaint();
    }

    public int getTileWidth() {
        return tilePanels[0][0].getWidth();
    }

    public int getTileHeight() {
        return tilePanels[0][0].getHeight();
    }

    public void removePieceFromBoard(int rowRemoved, int rowColumn) {
        tilePanels[rowRemoved][rowColumn].removeAll();
    }

    public void startGame(Player one, Player two) {

        myGame = new Game();
        myGame.addPlayer(one);
        one.setMyFrame(this);
        myGame.addPlayer(two);
        two.setMyFrame(this);

        Thread t = new Thread(myGame);

        t.start();

    }

    public void startGame(HumanPlayer humanPlayer, RemotePlayer remotePlayer) {
        myGame = new Game();

        if (remotePlayer.getRemoteID() == 1) {
            myGame.addPlayer(remotePlayer);
            remotePlayer.setMyFrame(this);
            myGame.addPlayer(humanPlayer); //human is the red and remote is the whites
            humanPlayer.setMyFrame(this);
            setTitle("Player two");
            System.out.println("Player two");
        } else if (remotePlayer.getRemoteID() == 2) {
            myGame.addPlayer(humanPlayer);
            humanPlayer.setMyFrame(this);
            myGame.addPlayer(remotePlayer);
            remotePlayer.setMyFrame(this);
            setTitle("Player one");
            System.out.println("Player one");
        }


        Thread t = new Thread(myGame);

        t.start();

    }


    class PanelMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if (myGame.getCurrentPlayer() instanceof HumanPlayer)
                myGame.getCurrentPlayer().mouseClicked(e);
        }

    }

    public JPanel[][] getTilePanels() {
        return tilePanels;
    }
}
