package Networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final int PORT = 4001;
    private ServerSocket serverSocket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private ArrayList<GameSession> connectionsList;

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        try {
            connectionsList = new ArrayList<>();
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server now running, waiting for connections...");
            while (true) {
                acceptTwoPlayers();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void acceptTwoPlayers() {
        try {
            Socket s1, s2;

            System.out.println("Waiting for player 1");
            s1 = serverSocket.accept();

            System.out.println("Waiting for player 2");
            s2 = serverSocket.accept();

            System.out.println("Player 2 connected!"); //is playing against player 2

            GameSession GS = new GameSession(s1, s2);
            connectionsList.add(GS);
            Thread t = new Thread(GS);
            t.start();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


}
