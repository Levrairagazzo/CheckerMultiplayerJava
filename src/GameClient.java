/*
This class is the client of the checker game, it displays the main menu and gives the user three different game option.
The user can choose to play against the computer, to play locally against another player, or over the network.
 */

import GUI.BoardFrame;
import GameLogic.Players.ComputerPlayer;
import GameLogic.Players.HumanPlayer;
import GameLogic.Players.RemotePlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class GameClient {

    JFrame mainMenu;
    JButton singlePlayer, localMultiplayer, remoteMultiPlayer;

    public static void main(String[] args) {
        var client = new GameClient();
    }

    public GameClient() {
        mainMenu = new JFrame();
        mainMenu.setTitle("Main Menu");
        mainMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        mainMenu.setSize(new Dimension(400, 400));
        mainMenu.setLayout(new GridLayout(3, 1));
        singlePlayer();
        multiplayerLocal();
        multiplayerRemote();


        mainMenu.setResizable(false);
        mainMenu.setVisible(true);
    }

    private void singlePlayer() {
        singlePlayer = new JButton("Single player game");
        mainMenu.add(singlePlayer);
        singlePlayer.addActionListener(e -> startSinglePlayerGame());

    }

    private void multiplayerRemote() {
        remoteMultiPlayer = new JButton("Multiplayer game (remote)");
        mainMenu.add(remoteMultiPlayer);
        remoteMultiPlayer.addActionListener(e -> startRemoteMultiplayerGame());
    }

    private void multiplayerLocal() {
        localMultiplayer = new JButton("Multiplayer game (local)");
        mainMenu.add(localMultiplayer);
        localMultiplayer.addActionListener(e -> startLocalMultiplayerGame());


    }

    private void startLocalMultiplayerGame() {
        Thread t = new Thread(() -> {
            BoardFrame bf = new BoardFrame();
            bf.startGame(new HumanPlayer(), new HumanPlayer());
            mainMenu.dispatchEvent(new WindowEvent(mainMenu, WindowEvent.WINDOW_CLOSING));
        });
        t.start();
    }

    private void startRemoteMultiplayerGame() {
        Thread t = new Thread(() -> {
            RemotePlayer remote = new RemotePlayer();
            BoardFrame bf = new BoardFrame();
            bf.startGame(new HumanPlayer(), remote);
            mainMenu.dispatchEvent(new WindowEvent(mainMenu, WindowEvent.WINDOW_CLOSING));
        });
        t.start();
    }

    private void startSinglePlayerGame() {
        Thread t = new Thread(() -> {
            BoardFrame bf = new BoardFrame();
            bf.startGame(new HumanPlayer(), new ComputerPlayer());
            mainMenu.dispatchEvent(new WindowEvent(mainMenu, WindowEvent.WINDOW_CLOSING));
        });

        t.start();
    }
}
