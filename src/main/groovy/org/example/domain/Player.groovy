package org.example.domain

class Player {
    private String name
    private PlayingPiece piece

    Player(String name, PlayingPiece piece) {
        this.name = name
        this.piece = piece
    }

    PlayingPiece getPiece() {
        return piece
    }

    String getName() {
        return name
    }
}
