/*
The game session class takes two sockets parameters(the two players) in its constructor and its run method generate a new
thread that handles the communication between both clients.
 */
package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameSession implements Runnable {

    Socket p1, p2;
    ObjectInputStream objectInRemotePlayerOne, objectInRemotePlayerTwo;
    ObjectOutputStream objectOutRemotePlayerOne, objectOutRemotePlayerTwo;

    public GameSession(Socket playerOne, Socket playerTwo) {

        try {

            p1 = playerOne;
            p2 = playerTwo;

            objectOutRemotePlayerOne = new ObjectOutputStream(p1.getOutputStream());
            objectOutRemotePlayerTwo = new ObjectOutputStream(p2.getOutputStream());
            objectInRemotePlayerOne = new ObjectInputStream(p1.getInputStream());
            objectInRemotePlayerTwo = new ObjectInputStream(p2.getInputStream());
            assignRemoteIDs();
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public void assignRemoteIDs() {
        try {
            objectOutRemotePlayerOne.writeObject(1);
            objectOutRemotePlayerTwo.writeObject(2);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void run() {
        System.out.println("New game session started.");


        try {

            MoveMessage moveMessage;
            int counter = 0;
            while (counter < 100) {
                moveMessage = (MoveMessage) objectInRemotePlayerTwo.readObject();
                objectOutRemotePlayerOne.writeObject(moveMessage);
                moveMessage = (MoveMessage) objectInRemotePlayerOne.readObject();
                objectOutRemotePlayerTwo.writeObject(moveMessage);
                counter++;

            }
            System.out.println("Game is now finished");
            p1.close();
            p2.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
