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

            Player currentPlayer = players.getFirst()
            PlayingPiece currentPiece = currentPlayer.getPiece()

            boolean valueInserted = false
            Integer row, col

            print("$currentPlayer.name (piece ${currentPiece.type.toString()}) >> Enter position of your piece: ")
            List<String> input = scanner.nextLine().tokenize(',')
            if (input.size() == 2) {
                try {
                    row = (input.first().trim()).toInteger()
                    col = (input.last().trim()).toInteger()
                } catch (NumberFormatException ignored) {
                    println("Invalid position selected! Kindly enter a valid position.")
                    continue
                }
                valueInserted = gameBoard.setValue(row, col, currentPiece)
            }
            if (!valueInserted) {
                println("Invalid position selected! Kindly enter a valid position.")
                continue
            }
            players.addLast(players.removeFirst())
            noWinner = checkForWinner(row, col)

            if (!noWinner) {
                gameBoard.printBoard()
                return ("${currentPlayer.name.toUpperCase()} WINS !!")
            }

            if (!spaceAvailableInBoard()) {
                gameBoard.printBoard()
                return "No Winner, Hence A Tie"
            }
        }
    }

    private boolean checkForWinner(Integer currRow, Integer currCol) {
        boolean noWinner = true
        Integer boardSize = gameBoard.size

        List<PlayingPiece> rowPieces = []
        List<PlayingPiece> colPieces = []
        List<PlayingPiece> fwdDiagonal = []
        List<PlayingPiece> bwdDiagonal = []

        for (int i = 0; i < gameBoard.size; i++) {
            gameBoard.getValue(currRow, i) == null ?: rowPieces.add(gameBoard.getValue(currRow, i))
            gameBoard.getValue(i, currCol) == null ?: colPieces.add(gameBoard.getValue(i, currCol))
            if (currRow == currCol) {
                gameBoard.getValue(i, i) == null ?: fwdDiagonal.add(gameBoard.getValue(i, i))
            }
            if (currRow + currCol == boardSize - 1) {
                gameBoard.getValue(i, (boardSize - 1) - i) == null ?: bwdDiagonal.add(gameBoard.getValue(i, (boardSize - 1) - i))
            }
        }

        if ((rowPieces.size() == boardSize && rowPieces.unique().size() == 1) ||
                (colPieces.size() == boardSize && colPieces.unique().size() == 1) ||
                (fwdDiagonal.size() == boardSize && fwdDiagonal.unique().size() == 1) ||
                (bwdDiagonal.size() == boardSize && bwdDiagonal.unique().size() == 1)) {
            noWinner = false
            return noWinner
        }

        return noWinner
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