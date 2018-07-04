package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Names;
import pl.waw.activeprogress.chesssolver.pieces.Piece;

import java.security.InvalidParameterException;

public class Mover {

    public Board move(final Board board, final String from, final String to, final String promotedFigure) throws CloneNotSupportedException{
        Board newBoard = board.clone();
        final int fromColumn = NotationTranslator.getColumnNumber(from);
        final int fromRow = NotationTranslator.getRowNumber(from);
        final int toColumn = NotationTranslator.getColumnNumber(to);
        final int toRow = NotationTranslator.getRowNumber(to);

        if (fromColumn < 0 || fromColumn > 7 || toColumn < 0 || toColumn > 7 || fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7) {
            throw new InvalidParameterException("Invalid 'from' or 'to' parameter - should be from 'A1' to 'H8'");
        }

        Piece movingPiece = newBoard.getSquares()[fromColumn][fromRow].getPiece();
        newBoard.getSquares()[fromColumn][fromRow].setPiece(null);
        newBoard.getSquares()[toColumn][toRow].setPiece(movingPiece);

        // set en passant possibility
        newBoard.setEnPassantTarget(null);
        if (movingPiece.getName() == Names.PAWN && movingPiece.getColor() == Color.WHITE && fromRow == 1 && toRow == 3) {
            newBoard.setEnPassantTarget(NotationTranslator.getColumnLetter(fromColumn).toLowerCase() + "3");
        }
        if (movingPiece.getName() == Names.PAWN && movingPiece.getColor() == Color.BLACK && fromRow == 6 && toRow == 4) {
            newBoard.setEnPassantTarget(NotationTranslator.getColumnLetter(fromColumn).toLowerCase() + "6");
        }

        if (newBoard.getMovingPlayer() == Color.WHITE) {
            newBoard.setMovingPlayer(Color.BLACK);
        } else {
            newBoard.setMovingPlayer(Color.WHITE);
            newBoard.setFullmoveNumber(newBoard.getFullmoveNumber() + 1);
        }

        return newBoard;

    }

    public Board move(final Board board, final String from, final String to) throws CloneNotSupportedException{
        return move(board, from, to, null);
    }
}
