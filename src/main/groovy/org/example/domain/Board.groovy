package org.example.domain

class Board {
    private int size
    private PlayingPiece[][] board

    Board(int size) {
        this.size = size
        this.board = new PlayingPiece[size][size]
    }

    int getSize() {
        return size
    }

    PlayingPiece[][] getBoard() {
        return board
    }

    void printBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                print("|  ${getValue(row, col)?.type?:' '}  ")
                if (col == size - 1)
                    println("|")
            }
        }
    }

    PlayingPiece getValue(int row, int col) {
        return board[row][col]
    }

    boolean setValue(Integer row, Integer col, PlayingPiece piece) {
        if (row > size - 1 || col > size - 1) {
            return false
        }
        if (row == null || col == null ) {
            return false
        }
        PlayingPiece currValue = getValue(row, col)
        if (currValue != null)
            return false
        board[row][col] = piece
        return true
    }
}
