import GUI.BoardFrame;
import GameLogic.Move;
import GameLogic.Players.ComputerPlayer;
import GameLogic.Players.HumanPlayer;
import GameLogic.Players.Player;
import GameLogic.Players.RemotePlayer;
import GameLogic.Tile;

import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws InterruptedException, IOException {

////        HumanPlayer humanPlayer = new HumanPlayer();
//        RemotePlayer remotePlayer = new RemotePlayer();
        BoardFrame bf = new BoardFrame();
        bf.startGame(new ComputerPlayer(), new HumanPlayer());

//        Move myMove = new Move(new Tile(1,1), new Tile(2,2));
    }


}
