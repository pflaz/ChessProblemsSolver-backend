package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Names;
import pl.waw.activeprogress.chesssolver.pieces.Piece;

import java.security.InvalidParameterException;

public class Mover {

    public Board move(final Board board, String from, String to, String promotedFigure) throws CloneNotSupportedException {
        from = from.toUpperCase();
        to = to.toUpperCase();
        if (promotedFigure != null) {
            promotedFigure = promotedFigure.toUpperCase();
        }

        Board newBoard = board.copy();

        if (from.charAt(0) < 'A' || from.charAt(0) > 'H' || to.charAt(0) < 'A' || to.charAt(0) > 'H' || from.charAt(1) < '1' || from.charAt(1) > '8' || to.charAt(1) < '1' || to.charAt(1) > '8') {
            throw new InvalidParameterException("Invalid 'from' or 'to' parameter - should be from 'A1' to 'H8'");
        }

        Piece movingPiece = newBoard.getSquare(from).getPiece();

        if (!validMove(board, from, to, promotedFigure)) {
            throw new InvalidParameterException("Invalid move");
        }

        newBoard.getSquare(from).setPiece(null);
        newBoard.getSquare(to).setPiece(movingPiece);

        // set en passant possibility
        newBoard.setEnPassantTarget(null);
        if (movingPiece.getName() == Names.PAWN && movingPiece.getColor() == Color.WHITE && from.charAt(1) == '2' && to.charAt(1) == '4') {
            newBoard.setEnPassantTarget(Character.toLowerCase(from.charAt(0)) + "3");
        }
        if (movingPiece.getName() == Names.PAWN && movingPiece.getColor() == Color.BLACK && from.charAt(1) == '7' && to.charAt(1) == '5') {
            newBoard.setEnPassantTarget(Character.toLowerCase(from.charAt(0)) + "6");
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

    private boolean validMove(final Board board, final String from, final String to, final String promotedFigure) {
        // check if it is valid player turn
        // can this piece go there
        //
        return true;
    }
}
