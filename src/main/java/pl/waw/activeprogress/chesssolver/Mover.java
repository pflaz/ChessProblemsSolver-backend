package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Names;
import pl.waw.activeprogress.chesssolver.pieces.Piece;
import pl.waw.activeprogress.chesssolver.pieces.Rook;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class Mover {

    private String getModifiedSquareName(String squareName, int changeColumn, int changeRow) {
        char currentColumn = squareName.charAt(0);
        char currentRow = squareName.charAt(1);
        char toColumn = (char)(currentColumn + changeColumn);
        char toRow = (char)(currentRow + changeRow);


        if (toColumn < 'A' || toColumn > 'H' || toRow < '1' || toRow > '8') {
            return null;
        }
        return new StringBuilder(Character.toString(toColumn)).append(Character.toString(toRow)).toString();
    }

    public Map<String, Move> getPossibleMoves(final Board board) {
        Map<String, Move> result = new HashMap<>();

        board.getSquares().forEach((squareName, square) -> {
            Piece piece = square.getPiece();
            if (piece == null) return;
            if (board.getMovingPlayer() != piece.getColor()) return;
            Color oppositeColor;
            if (piece.getColor() == Color.WHITE) {
                oppositeColor = Color.BLACK;
            } else {
                oppositeColor = Color.WHITE;
            }
            String from = squareName;
            String to, moveSign;
            switch (piece.getName()) {

                case KING:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.DOWN, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.UP, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_UP, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_DOWN, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_DOWN, 1));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_UP, 1));

                    to = getModifiedSquareName(from, 2, 0);
                    result.putAll(checkCastling(board, piece, from, to));
                    to = getModifiedSquareName(from, -2, 0);
                    result.putAll(checkCastling(board, piece, from, to));
                    break;

                case QUEEN:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.DOWN, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.UP, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_UP, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_DOWN, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_DOWN, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_UP, 7));
                    break;

                case ROOK:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.DOWN, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.UP, 7));
                    break;

                case BISHOP:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_UP, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_DOWN, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_DOWN, 7));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_UP, 7));
                    break;

                case KNIGHT:
                    to = getModifiedSquareName(from, 1, 2);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, 2, 1);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, 2, -1);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, 1, -2);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, -1, -2);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, -2, -1);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, -2, 1);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    to = getModifiedSquareName(from, -1, 2);
                    result.putAll(checkMovePossibilityForKnight(board, piece, from, to));
                    break;

                case PAWN:
                    if (piece.getColor() == Color.WHITE) {
                        // 1 step
                        to = getModifiedSquareName(from, 0, 1);
                        result.putAll(checkMovePossibilityForPawn(board, piece, from, to));

                        // 2 steps
                        if (from.charAt(1) == '2' && board.getSquare(to).isEmpty()) {
                            to = getModifiedSquareName(from, 0, 2);
                            result.putAll(checkMovePossibilityForPawn(board, piece, from, to));
                        }

                        // capture
                        to = getModifiedSquareName(from, -1, 1);
                        result.putAll(checkMovePossibilityForPawn(board, piece, from, to));
                        to = getModifiedSquareName(from, 1, 1);
                        result.putAll(checkMovePossibilityForPawn(board, piece, from, to));

                        // en passant
                        if (from.charAt(1)== '5' && board.getEnPassantTarget() != null) {
                            to = getModifiedSquareName(from, -1, 1);
                            if (to.toLowerCase().equals(board.getEnPassantTarget())) {
                                result.putAll(checkMovePossibilityForPawn(board, piece, from, to, true));
                            }
                            to = getModifiedSquareName(from, 1, 1);
                            if (to.toLowerCase().equals(board.getEnPassantTarget())) {
                                result.putAll(checkMovePossibilityForPawn(board, piece, from, to, true));
                            }
                        }
                    }
                    if (piece.getColor() == Color.BLACK) {
                        // 1 step
                        to = getModifiedSquareName(from, 0, -1);
                        result.putAll(checkMovePossibilityForPawn(board, piece, from, to));

                        // 2 steps
                        if (from.charAt(1) == '7' && board.getSquare(to).isEmpty()) {
                            to = getModifiedSquareName(from, 0, -2);
                            result.putAll(checkMovePossibilityForPawn(board, piece, from, to));
                        }

                        // capture
                        to = getModifiedSquareName(from, -1, -1);
                        result.putAll(checkMovePossibilityForPawn(board, piece, from, to));
                        to = getModifiedSquareName(from, 1, -1);
                        result.putAll(checkMovePossibilityForPawn(board, piece, from, to));

                        // en passant
                        if (from.charAt(1)== '4' && board.getEnPassantTarget() != null) {
                            to = getModifiedSquareName(from, -1, -1);
                            if (to.toLowerCase().equals(board.getEnPassantTarget())) {
                                result.putAll(checkMovePossibilityForPawn(board, piece, from, to, true));
                            }
                            to = getModifiedSquareName(from, 1, -1);
                            if (to.toLowerCase().equals(board.getEnPassantTarget())) {
                                result.putAll(checkMovePossibilityForPawn(board, piece, from, to, true));
                            }
                        }
                    }
                    break;
            }
        });

        return result;
    }

    public Map<String, Move> checkCastling(Board board, Piece piece, String from, String to) {
        Map<String, Move> result = new HashMap<>();
        if (from.charAt(0) != 'E') return result;
        if (to.charAt(0) != 'G' && to.charAt(0) != 'C') {
            return result;
        }
        // TODO if check
        if (piece.getColor() == Color.WHITE) {
            if (from.charAt(1) != '1' || to.charAt(1) != '1') {
                return result;
            }
            if (to.charAt(0) == 'G') {
                if (!board.isWhiteKingsideCastlingPossible() || !board.getSquare("H1").getPiece().equals(new Rook(Color.WHITE)) || !board.getSquare("F1").isEmpty() || !board.getSquare("G1").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from + to, from, to, null, "0-0", "0-0"));
            }
            if (to.charAt(0) == 'C') {
                if (!board.isWhiteQueensideCastlingPossible() || !board.getSquare("A1").getPiece().equals(new Rook(Color.WHITE)) || !board.getSquare("D1").isEmpty() || !board.getSquare("C1").isEmpty() || !board.getSquare("B1").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from + to, from, to, null, "0-0-0", "0-0-0"));
            }
        }
        if (piece.getColor() == Color.BLACK) {
            if (from.charAt(1) != '8' || to.charAt(1) != '8') {
                return result;
            }
            if (to.charAt(0) == 'G') {
                if (!board.isBlackKingsideCastlingPossible() || !board.getSquare("H8").getPiece().equals(new Rook(Color.BLACK)) || !board.getSquare("F8").isEmpty() || !board.getSquare("G8").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from + to, from, to, null, "0-0", "0-0"));
            }
            if (to.charAt(0) == 'C') {
                if (!board.isBlackQueensideCastlingPossible() || !board.getSquare("A8").getPiece().equals(new Rook(Color.BLACK)) || !board.getSquare("D8").isEmpty() || !board.getSquare("C8").isEmpty() || !board.getSquare("B8").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from + to, from, to, null, "0-0-0", "0-0-0"));
            }
        }
        return result;
    }

    public Map<String, Move> checkMovePossibilityForPawn(Board board, Piece piece, String from, String to) {
        return checkMovePossibilityForPawn(board, piece, from, to, false);
    }

    public Map<String, Move> checkMovePossibilityForPawn(Board board, Piece piece, String from, String to, boolean enPassant) {
        Map<String, Move> result = new HashMap<>();
        if (to == null) { // out of board
            return result;
        }
        boolean capture;
        if (from.charAt(0) == to.charAt(0)) {
            capture = false;
        } else {
            capture = true;
        }
        Color oppositeColor;
        if (piece.getColor() == Color.WHITE) {
            oppositeColor = Color.BLACK;
        } else {
            oppositeColor = Color.WHITE;
        }

        String longMoveSign = "-";
        if (board.getSquare(to).isOccupiedBy(piece.getColor())) {
            return result;
        }

        String shortNotationBegin = "";
        String enPassantNotation = "";
        if (enPassant) {
            if (board.getSquare(to).isEmpty()) {
                shortNotationBegin = Character.toString(from.charAt(0)) + "x";
                shortNotationBegin = shortNotationBegin.toLowerCase();
                longMoveSign = "x";
                enPassantNotation = " e.p.";
            } else {
                return result;
            }
        } else {
            if (board.getSquare(to).isOccupiedBy(oppositeColor)) {
                if (capture) {
                    shortNotationBegin = Character.toString(from.charAt(0)) + "x";
                    shortNotationBegin = shortNotationBegin.toLowerCase();
                    longMoveSign = "x";
                } else {
                    return result;
                }
            } else {
                if (capture) {
                    return result;
                }
            }
        }

        if ((to.charAt(1) == '8' && piece.getColor() == Color.WHITE) || (to.charAt(1) == '1' && piece.getColor() == Color.BLACK)) { // promotion
            result.put(from + to + "Q", new Move(from + to + "Q", from, to, Names.QUEEN, shortNotationBegin + to.toLowerCase() + enPassantNotation + "Q", from.toLowerCase() + longMoveSign + to.toLowerCase() + enPassantNotation + "Q"));
            result.put(from + to + "R", new Move(from + to + "R", from, to, Names.ROOK, shortNotationBegin + to.toLowerCase() + enPassantNotation + "R", from.toLowerCase() + longMoveSign + to.toLowerCase() + enPassantNotation + "R"));
            result.put(from + to + "B", new Move(from + to + "B", from, to, Names.BISHOP, shortNotationBegin + to.toLowerCase() + enPassantNotation + "B", from.toLowerCase() + longMoveSign + to.toLowerCase() + enPassantNotation + "B"));
            result.put(from + to + "N", new Move(from + to + "N", from, to, Names.KNIGHT, shortNotationBegin + to.toLowerCase() + enPassantNotation + "N", from.toLowerCase() + longMoveSign + to.toLowerCase() + enPassantNotation + "N"));
        } else {
            result.put(from + to, new Move(from + to, from, to, null, shortNotationBegin + to.toLowerCase() + enPassantNotation, from.toLowerCase() + longMoveSign + to.toLowerCase() + enPassantNotation));
        }
        return result;
    }

    public Map<String, Move> checkMovePossibilityForKnight(Board board, Piece piece, String from, String to) {
        Map<String, Move> result = new HashMap<>();
        Color oppositeColor;
        if (piece.getColor() == Color.WHITE) {
            oppositeColor = Color.BLACK;
        } else {
            oppositeColor = Color.WHITE;
        }
        if (to == null) { // out of board
            return result;
        }
        String longMoveSign = "-";
        String shortMoveSign = "";
        if (board.getSquare(to).isOccupiedBy(piece.getColor())) {
            return result;
        }
        if (board.getSquare(to).isOccupiedBy(oppositeColor)) {
            longMoveSign = "x";
            shortMoveSign = "x";
        }
        result.put(from + to, new Move(from + to, from, to, null, piece.getShortcut() + shortMoveSign + to.toLowerCase(), piece.getShortcut() + from.toLowerCase() + longMoveSign + to.toLowerCase()));
        return result;
    }

    public Map<String, Move> checkMovesInDirection(Board board, Piece piece, String from, Direction direction, int squaresCount) {

        // squaresCont - how many squares to check in the line (7 - for Q, B, N, 1 - for K)

        Map<String, Move> result = new HashMap<>();
        Color oppositeColor;
        if (piece.getColor() == Color.WHITE) {
            oppositeColor = Color.BLACK;
        } else {
            oppositeColor = Color.WHITE;
        }

        int columnMultiplier = 0, rowMultiplier = 0;
        switch (direction) {
            case RIGHT_UP:
                columnMultiplier = 1;
                rowMultiplier = 1;
                break;
            case RIGHT:
                columnMultiplier = 1;
                rowMultiplier = 0;
                break;
            case RIGHT_DOWN:
                columnMultiplier = 1;
                rowMultiplier = -1;
                break;
            case DOWN:
                columnMultiplier = 0;
                rowMultiplier = -1;
                break;
            case LEFT_DOWN:
                columnMultiplier = -1;
                rowMultiplier = -1;
                break;
            case LEFT:
                columnMultiplier = -1;
                rowMultiplier = 0;
                break;
            case LEFT_UP:
                columnMultiplier = -1;
                rowMultiplier = 1;
                break;
            case UP:
                columnMultiplier = 0;
                rowMultiplier = 1;
                break;
        }

        for (int changer = 1; changer <= squaresCount; changer++) {
            String to = getModifiedSquareName(from, changer * columnMultiplier, changer * rowMultiplier);

            if (to == null) break; // out of board
            String longMoveSign = "-";
            String shortMoveSign = "";
            if (board.getSquare(to).isOccupiedBy(piece.getColor())) break;
            if (board.getSquare(to).isOccupiedBy(oppositeColor)) {
                longMoveSign = "x";
                shortMoveSign = "x";
            }
            result.put(from + to, new Move(from + to, from, to, null, piece.getShortcut() + shortMoveSign + to.toLowerCase(), piece.getShortcut() + from.toLowerCase() + longMoveSign + to.toLowerCase()));
            if (board.getSquare(to).isOccupiedBy(oppositeColor)) break;
        }
        return result;
    }

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
