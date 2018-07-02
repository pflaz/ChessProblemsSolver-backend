package pl.waw.activeprogress.chesssolver;

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

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {

            }
        }
    }
}

