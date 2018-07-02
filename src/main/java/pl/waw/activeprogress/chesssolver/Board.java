package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Piece;

public class Board {
    private Square[][] squares;
    private boolean whiteKingsideCastlingPossible;
    private boolean whiteQueensideCastlingPossible;
    private boolean blackKingsideCastlingPossible;
    private boolean blackQueensideCastlingPossible;
    private String enPassantTarget;
    private int halfmoveClock; // halfmoves since the last capture or pawn advance
    private int fullmoveNumber;

    public Board() {
        whiteKingsideCastlingPossible = true;
        whiteQueensideCastlingPossible = true;
        blackKingsideCastlingPossible = true;
        blackQueensideCastlingPossible = true;
        enPassantTarget = null;
        halfmoveClock = 0;
        fullmoveNumber = 1;
        squares = new Square[8][8];

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                squares[col][row] = new Square(col, row, null);
            }
        }
    }

    public void print() {
        Piece piece;
        char letter;
        System.out.println("-----------------------------------");
        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " | ");
            for (int col = 0; col < 8; col++) {
                piece = squares[col][row].getPiece();
                if (piece != null) {
                    letter = piece.getClass().getName().charAt(0);
                    if (piece.getColor() == Color.WHITE) {
                        Character.toUpperCase(letter);
                    } else {
                        Character.toLowerCase(letter);
                    }
                } else {
                    letter = ' ';
                }
                System.out.print(letter + " | ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
        System.out.println("  | A | B | C | D | E | F | G | H |");
    }
}

