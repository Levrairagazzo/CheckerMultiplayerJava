/*
Handles all the game logic and communicates with both players.
 */

package GameLogic;

import GameLogic.Players.Player;
import GameLogic.Players.RemotePlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game implements Runnable {
    Tile[][] board;
    public Player playerOne = null;
    public Player playerTwo = null;
    public Player currentPlayer;
    private boolean gameIsOn;
    private boolean pieceSelected;
    private Tile pieceSelectedLocation;
    private ArrayList<int[]> singleMoves = new ArrayList<int[]>();
    private ArrayList<int[]> doubleMoves = new ArrayList<int[]>();

    public Move getLastMovePlayed() {
        return lastMovePlayed;
    }

    private Move lastMovePlayed;


    public Game() {
        gameIsOn = false;
        lastMovePlayed = null;
        board = new Tile[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Tile(i, j);

            }

        }
        initMoves();
    }

    private void initMoves() {
        singleMoves.add((new int[]{-1, 1}));
        singleMoves.add((new int[]{-1, -1}));
        singleMoves.add((new int[]{1, 1}));
        singleMoves.add((new int[]{1, -1}));
        doubleMoves.add((new int[]{-2, -2}));
        doubleMoves.add((new int[]{-2, 2}));
        doubleMoves.add((new int[]{2, -2}));
        doubleMoves.add((new int[]{2, 2}));
    }

    public boolean containsArr(ArrayList<int[]> list, int[] twin) {
        for (int i = 0; i < list.size(); i++)
            if (Arrays.equals(list.get(i), twin))
                return true;
        return false;
    }


    public void addPlayer(Player newPlayer) {

        if (playerOne == null) {
            playerOne = newPlayer;
            playerOne.setColor(Color.WHITE);
            playerOne.setDirection(0);                  /// TOP
            playerOne.setGame(this);
        } else if (playerTwo == null) {
            playerTwo = newPlayer;
            playerTwo.setColor(Color.RED);
            playerTwo.setDirection(1);                  ///BOTTOM
            playerTwo.setGame(this);
            currentPlayer = playerOne;                  //Select current player


        } else
            System.out.println("ALREADY HAVE TWO PLAYERS");
    }

    protected boolean endGame() {
        return playerTwo.getNumPieces() == 0 || playerOne.getNumPieces() == 0;
    }

    public Tile getTile(int row, int column) {
        return board[row][column];
    }

    public void executeGameMove(Move move) {
        lastMovePlayed = move;
        Tile originalTile = move.getOriginalTile();
        Tile finalTile = move.getFinalTile();
        currentPlayer.executePlayerMove(move);

        if (howManyTileJumped(move) == 2) {
            removeCapturedPiece(move);
        }
        board[originalTile.getRow()][originalTile.getColumn()] = new Tile(originalTile.getRow(), originalTile.getColumn());


        setPieceSelected(false);
        switchTurn();
    }


    public boolean isLegalMove(Move move) {
        if (!withinBounds(move.getOriginalTile()) || !withinBounds(move.finalTile)) {
            return false;
        }
        Tile from = board[move.getOriginalTile().getRow()][move.getOriginalTile().getColumn()];
        Tile to = board[move.getFinalTile().getRow()][move.getFinalTile().getColumn()];

        int rowMove = from.getRow() - to.getRow();
        int columnMove = from.getColumn() - to.getColumn();


        if (!from.isOccupied()) {
            System.out.println("From tile is not occupied ");
            return false;
        }

        if (to.isOccupied()) {
            System.out.println("To tile is already occupied");
            return false;
        }

        if (!from.getOccupant().getPlayer().equals(currentPlayer)) {
            System.out.println("currentPlayer ->" + currentPlayer.getColor());
            System.out.println("occupant player ->" + from.getOccupant().getPlayer().getColor());
            System.out.println("Piece does not belong to the good player");
            return false;
        }
//
        if (!withinBounds(from) || !withinBounds(to)) {
            //verifies if tiles are within bounds

            System.out.println("Outside of bounds ");
            return false;
        }

        if (howManyTileJumped(move) == 2) {
            return validDoubleMove(move);
        } else if (howManyTileJumped(move) == 1) {
            return validSingleMove(move);
        }
        return false;

    }

    public boolean withinBounds(Tile myTile) {
        if (myTile.getColumn() > 7 || myTile.getColumn() < 0)
            return false;
        if (myTile.getRow() > 7 || myTile.getRow() < 0)
            return false;
        return true;
    }

    public int howManyTileJumped(Move move) {
        Tile from = move.getOriginalTile();
        Tile to = move.getFinalTile();
        int rowMove = from.getRow() - to.getRow();
        int columnMove = from.getColumn() - to.getColumn();
        int[] movePair = {rowMove, columnMove};

        if (containsArr(singleMoves, movePair)) {
            return 1;
        }
        if (containsArr(doubleMoves, movePair)) {
            return 2;
        }


        return -1;
    }

    private boolean validDoubleMove(Move move) {
        int originalRow = move.getOriginalTile().getRow();
        int originalCol = move.getOriginalTile().getColumn();
        boolean isValid = false;

        if (!move.getOriginalTile().getOccupant().isKinged() && currentPlayer.getDirection() == 0 && move.getMoveCoordinate()[0] == -2) {
            return false;
        } else if (!move.getOriginalTile().getOccupant().isKinged() && currentPlayer.getDirection() == 1 && move.getMoveCoordinate()[0] == 2) {
            return false;
        }

        if (move.getMoveCoordinate()[0] == -2 && move.getMoveCoordinate()[1] == -2) {
            isValid = board[originalRow - 1][originalCol - 1].isOccupied()
                    && !(board[originalRow - 1][originalCol - 1].getOccupant().getPlayer().equals(currentPlayer));
        }
        if (move.getMoveCoordinate()[0] == -2 && move.getMoveCoordinate()[1] == 2) {
            isValid = board[originalRow - 1][originalCol + 1].isOccupied()
                    && !(board[originalRow - 1][originalCol + 1].getOccupant().getPlayer().equals(currentPlayer));
        }
        if (move.getMoveCoordinate()[0] == 2 && move.getMoveCoordinate()[1] == -2) {
            isValid = board[originalRow + 1][originalCol - 1].isOccupied()
                    && !(board[originalRow + 1][originalCol - 1].getOccupant().getPlayer().equals(currentPlayer));
        }
        if (move.getMoveCoordinate()[0] == 2 && move.getMoveCoordinate()[1] == 2) {
            isValid = board[originalRow + 1][originalCol + 1].isOccupied()
                    && !(board[originalRow + 1][originalCol + 1].getOccupant().getPlayer().equals(currentPlayer));
        }

        return isValid;
    }

    public void removeCapturedPiece(Move move) {
        int originalRow = move.getOriginalTile().getRow();
        int originalCol = move.getOriginalTile().getColumn();

        if (move.getMoveCoordinate()[0] == -2 && move.getMoveCoordinate()[1] == -2) {
            removePieceFromPlayerList(board[originalRow - 1][originalCol - 1].getOccupant());
            removePieceFromGame(board[originalRow - 1][originalCol - 1]);
        }
        if (move.getMoveCoordinate()[0] == -2 && move.getMoveCoordinate()[1] == 2) {
            removePieceFromPlayerList(board[originalRow - 1][originalCol + 1].getOccupant());
            removePieceFromGame(board[originalRow - 1][originalCol + 1]);
        }
        if (move.getMoveCoordinate()[0] == 2 && move.getMoveCoordinate()[1] == -2) {
            removePieceFromPlayerList(board[originalRow + 1][originalCol - 1].getOccupant());
            removePieceFromGame(board[originalRow + 1][originalCol - 1]);
        }
        if (move.getMoveCoordinate()[0] == 2 && move.getMoveCoordinate()[1] == 2) {
            removePieceFromPlayerList(board[originalRow + 1][originalCol + 1].getOccupant());
            removePieceFromGame(board[originalRow + 1][originalCol + 1]);
        }

    }


    private boolean validSingleMove(Move move) {
        if (move.getOriginalTile().getOccupant().isKinged()) {

            return true;
        }
        if (currentPlayer.getDirection() == 0 && move.getMoveCoordinate()[0] == -1) {
            return false;
        } else if (currentPlayer.getDirection() == 1 && move.getMoveCoordinate()[0] == 1) {
            return false;
        }
        return true;
    }

    private void removePieceFromGame(Tile removed) {
        int removedRow = removed.getRow();
        int removedCol = removed.getColumn();
        if (removed.isOccupied()) {
            board[removedRow][removedCol].removeOccupant();
        }

        currentPlayer.getMyFrame().removePieceFromBoard(removedRow, removedCol);


    }


    protected void play() throws InterruptedException {
        Move chosenMove;
        gameIsOn = true;
        while (gameIsOn) {
            chosenMove = currentPlayer.chooseMove();
            if (currentPlayer instanceof RemotePlayer) {
                executeGameMove(chosenMove);

            } else if (isLegalMove(chosenMove)) {
                executeGameMove(chosenMove);
            }
        }
    }

    public void startGame() {
        resetBoard();
        addPieces(playerOne);
        addPieces(playerTwo);

    }

    protected void switchTurn() {
        System.out.println("calling switch turn");
        if (currentPlayer.equals(playerOne)) {
            currentPlayer = playerTwo;
        } else if (currentPlayer.equals(playerTwo)) {
            currentPlayer = playerOne;

        }
    }

    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Tile(i, j);

            }

        }
    }


    private void addPieces(Player player) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    if (player.getDirection() == 0) {  //top of the board
                        if (i < 3) {
                            player.addPieceToGame(board[i][j], false);
                        }


                    }
                    if (player.getDirection() == 1) { //bottom of the board
                        if (i > 4) {
                            player.addPieceToGame(board[i][j], false);
                        }


                    }

                }

            }

        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public boolean isPieceSelected() {
        return pieceSelected;
    }

    public void setPieceSelected(boolean pieceSelected) {
        this.pieceSelected = pieceSelected;
    }

    public Tile getPieceSelectedLocation() {
        return pieceSelectedLocation;
    }

    public void setPieceSelectedLocation(Tile pieceSelectedLocation) {
        this.pieceSelectedLocation = pieceSelectedLocation;
    }


    public void removePieceFromPlayerList(Piece pieceTaken) {
        ArrayList<Piece> pieces = pieceTaken.getPlayer().getPiecesList();
        System.out.println("piece taken = " + Arrays.toString(pieceTaken.getCoordinate()));
        for (int i = 0; i < pieces.size(); i++) {
            if (pieceTaken.getCoordinate()[0] == pieces.get(i).getCoordinate()[0]
                    && pieceTaken.getCoordinate()[1] == pieces.get(i).getCoordinate()[1]) {
                pieceTaken.getPlayer().removePieceFromList(pieces.get(i));
                break;

            }
        }
    }


    @Override
    public void run() {

        startGame();
        try {
            play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
