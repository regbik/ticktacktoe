package org.example.domain

class PlayingPiece {
    private PieceType type

    PlayingPiece(PieceType type) {
        this.type = type
    }

    PieceType getType() {
        return type
    }
}
