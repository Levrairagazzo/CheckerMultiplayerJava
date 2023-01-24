/*
Encapsulate the specificity of a remote player. Connects the remove player to the gameserver.
 */

package GameLogic.Players;

import GameLogic.Move;
import Networking.MoveMessage;

import java.io.*;
import java.net.Socket;

public class RemotePlayer extends Player {
    private final int PORT = 4001;
    Socket socket;
    int remoteID;
    ObjectInputStream objectIn;
    ObjectOutputStream objectOut;


    public RemotePlayer() {
        super();
        connectToServer();
    }

    private void connectToServer() {
        try {

            socket = new Socket("localhost", PORT);
            System.out.println("Connection to server was successful!");
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());

            remoteID = (Integer) objectIn.readObject();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Move chooseMove() {
        try {
            writeMove(currentGame.getLastMovePlayed());
            var moveReceived = receiveMove();
            return moveReceived;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not choose a move from remote player");
            return null;
        }
    }

    public void writeMove(Move move) throws IOException {
        if (!(move == null)) {
            System.out.println("Sending move to player");
            var message = new MoveMessage(move, false);
            objectOut.writeObject(message);

        }


    }

    public Move receiveMove() throws IOException, ClassNotFoundException {
        System.out.println("Waiting for move");
        MoveMessage message = (MoveMessage) objectIn.readObject();
        System.out.println("Move received");
        return message.generateMove();

    }

    public int getRemoteID() {
        return remoteID;
    }
}
