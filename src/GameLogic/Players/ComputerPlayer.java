/*
 In charge of the logic needed for a computer player. Main task is choosing a move to be played.
 */

package GameLogic.Players;

import GameLogic.Move;
import GameLogic.Piece;
import GameLogic.Tile;
import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends HumanPlayer {
    ArrayList<int[]> singleMoves, doubleMoves;
    ArrayList<Move> potentialMoves, legalMoves;
    Random rand;

    public ComputerPlayer() {
        rand = new Random();
        potentialMoves = new ArrayList<>();
        legalMoves = new ArrayList<>();
        initMoves();
    }


    private void initMoves() {
        this.singleMoves = new ArrayList<>();
        this.doubleMoves = new ArrayList<>();

        singleMoves.add((new int[]{-1, 1}));
        singleMoves.add((new int[]{-1, -1}));
        singleMoves.add((new int[]{1, 1}));
        singleMoves.add((new int[]{1, -1}));
        doubleMoves.add((new int[]{-2, -2}));
        doubleMoves.add((new int[]{-2, 2}));
        doubleMoves.add((new int[]{2, -2}));
        doubleMoves.add((new int[]{2, 2}));


    }


    public ArrayList<Move> getPiecePotentialMoves(Piece piece) {
        var moves = new ArrayList<Move>();
        Tile originalTile = piece.getTile();
        Move genericMove = new Move(originalTile, originalTile);

        moves.add(genericMove.update((new int[]{-1, 1})));
        moves.add(genericMove.update((new int[]{-1, -1})));
        moves.add(genericMove.update((new int[]{1, 1})));
        moves.add(genericMove.update((new int[]{1, -1})));
        moves.add(genericMove.update(new int[]{-2, -2}));
        moves.add(genericMove.update(new int[]{-2, 2}));
        moves.add(genericMove.update(new int[]{2, -2}));
        moves.add(genericMove.update(new int[]{2, 2}));


        return moves;
    }

    public void printMoves() {

        for (Move move : potentialMoves) {
            if (currentGame.isLegalMove(move)) {
                legalMoves.add(move);

            }
        }
    }


    public Move chooseMove() throws InterruptedException {
        Thread.sleep(800);
        for (Piece piece : piecesList) {
            potentialMoves = getPiecePotentialMoves(piece);
            printMoves();

        }
        int index = (int) (Math.random() * legalMoves.size());

        Move movePlayed = legalMoves.get(index);
        System.out.println(movePlayed);


        return movePlayed;

    }


}
