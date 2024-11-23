package org.example

import org.example.domain.Board
import org.example.domain.Player
import org.example.domain.PlayingPiece
import org.example.domain.PlayingPieceO
import org.example.domain.PlayingPieceX

class TicTacToeGame {
    private Deque<Player> players
    private Board gameBoard

    TicTacToeGame() {
        initialize()
    }

    private void initialize() {
        players = new LinkedList<>()

        PlayingPiece playingPieceX = new PlayingPieceX()
        Player player1 = new Player("Player 1", playingPieceX)

        PlayingPiece playingPieceO = new PlayingPieceO()
        Player player2 = new Player("Player 2", playingPieceO)

        players.add(player1)
        players.add(player2)

        gameBoard = new Board(3)
    }

    String startGame() {
        Scanner scanner = new Scanner(System.in)
        boolean noWinner = true
        while (noWinner) {
            gameBoard.printBoard()

            Player currentPlayer = players.removeFirst()
            PlayingPiece currentPiece = currentPlayer.getPiece()

            print("$currentPlayer.name (piece ${currentPiece.type.toString()}) >> Enter position of your piece: ")
            List<String> input = scanner.nextLine().tokenize(',')
            int row = (input.first()).toInteger()
            int col = (input.last()).toInteger()
            boolean valueInserted = gameBoard.setValue(row, col, currentPiece)
            if (!valueInserted) {
                println("Invalid position selected. Please select an empty position for your piece!")
                players.addFirst(currentPlayer)
                continue
            }
            players.addLast(currentPlayer)
            noWinner = checkForWinner(currentPlayer, currentPiece)

            if (!noWinner) {
                return ("${currentPlayer.name.toUpperCase()} WINS !!")
            }

            if (!spaceAvailableInBoard()) {
                return "No Winner, Hence A Tie"
            }
        }
    }

    private boolean checkForWinner(Player player, PlayingPiece piece) {
        boolean noWinner = true
        boolean boardSizeIsEven = isEven(gameBoard.size)

        //check in the rows/columns
        List<PlayingPiece> rowPieces = []
        List<PlayingPiece> colPieces = []
        List<PlayingPiece> fwdDiagonal = []
        List<PlayingPiece> bwdDiagonal = []
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                rowPieces.add(gameBoard.getValue(row, col))
                colPieces.add(gameBoard.getValue(col, row))
                if (row == col) {
                    fwdDiagonal.add(gameBoard.getValue(row, col))
                }
                if ((!boardSizeIsEven && isEven(row + col)) || (boardSizeIsEven && !isEven(row + col))) {
                    bwdDiagonal.add(gameBoard.getValue(row, col))
                }
            }
            if ((rowPieces.findAll {it != null}.size() == 3 && rowPieces.findAll {it != null}.unique().size() == 1) || (colPieces.findAll {it != null}.size() == 3 && colPieces.findAll {it != null}.unique().size() == 1)) {
                noWinner = false
                return noWinner
            } else {
                rowPieces = []
                colPieces = []
            }
        }
        if ((fwdDiagonal.size() == 3 && fwdDiagonal.unique().size() == 1) || (bwdDiagonal.size() == 3 && bwdDiagonal.unique().size() == 1)) {
            noWinner = false
            return noWinner
        }
        return noWinner
    }

    private static boolean isEven(int value) {
        return (value % 2 == 0)
    }

    private boolean spaceAvailableInBoard() {
        boolean spaceAvailable = false
        outerloop:
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                if (gameBoard.getValue(row, col) == null) {
                    spaceAvailable = true
                    break outerloop
                }
            }
        }
        return spaceAvailable
    }
}