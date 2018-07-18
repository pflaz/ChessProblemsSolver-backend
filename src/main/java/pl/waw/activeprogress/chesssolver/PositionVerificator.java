package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Piece;

import java.util.Map;

public class PositionVerificator {

    public boolean isPositionCorrect(Board board) {

        Mover mover = new Mover();

        int whiteKings = 0;
        int whiteQueens = 0;
        int whiteRooks = 0;
        int whiteBishops = 0;
        int whiteKnights = 0;
        int whitePawns = 0;
        int blackKings = 0;
        int blackQueens = 0;
        int blackRooks = 0;
        int blackBishops = 0;
        int blackKnights = 0;
        int blackPawns = 0;

        Map<String, Square> squares = board.getSquares();
        for(Square theSquare: squares.values()) {
            Piece thePiece = theSquare.getPiece();
            if (thePiece == null) continue;
            if (thePiece.getColor() == Color.WHITE) {
                switch (thePiece.getName()) {
                    case KING:
                        whiteKings++;
                        break;
                    case QUEEN:
                        whiteQueens++;
                        break;
                    case ROOK:
                        whiteRooks++;
                        break;
                    case BISHOP:
                        whiteBishops++;
                        break;
                    case KNIGHT:
                        whiteKnights++;
                        break;
                    case PAWN:
                        whitePawns++;
                        break;
                }
            }
            if (thePiece.getColor() == Color.BLACK) {
                switch (thePiece.getName()) {
                    case KING:
                        blackKings++;
                        break;
                    case QUEEN:
                        blackQueens++;
                        break;
                    case ROOK:
                        blackRooks++;
                        break;
                    case BISHOP:
                        blackBishops++;
                        break;
                    case KNIGHT:
                        blackKnights++;
                        break;
                    case PAWN:
                        blackPawns++;
                        break;
                }
            }
        }
        if (whiteKings != 1) return false;
        if (blackKings != 1) return false;
        if (whiteKings + whiteQueens + whiteRooks + whiteBishops + whiteKnights + whitePawns > 16) return false;
        if (blackKings + blackQueens + blackRooks + blackBishops + blackKnights + blackPawns > 16) return false;
        if (whiteQueens > 9 || whiteRooks > 9 || whiteBishops > 9 || whiteKnights > 9) return false;
        if (blackQueens > 9 || blackRooks > 9 || blackBishops > 9 || blackKnights > 9) return false;
        if (whitePawns > 8 || blackPawns > 8) return false;

        Board boardWithOppositePlayerOnTheMove;
        try {
            boardWithOppositePlayerOnTheMove = board.copy();
        } catch (CloneNotSupportedException e) {
            return false;
        }
        boardWithOppositePlayerOnTheMove.switchMovingPlayer();
        if (mover.isCheck(boardWithOppositePlayerOnTheMove)) {
            return false;
        }
        return true;
    }
}
