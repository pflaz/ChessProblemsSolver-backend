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
            String from = squareName;
            String to;
            switch (piece.getName()) {

                case KING:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.UP));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_UP));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_UP));

                    to = getModifiedSquareName(from, 2, 0);
                    result.putAll(checkCastling(board, piece, from, to));
                    to = getModifiedSquareName(from, -2, 0);
                    result.putAll(checkCastling(board, piece, from, to));
                    break;

                case QUEEN:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.UP));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_UP));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_UP));
                    break;

                case ROOK:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.UP));
                    break;

                case BISHOP:
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_UP));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.RIGHT_DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_DOWN));
                    result.putAll(checkMovesInDirection(board, piece, from, Direction.LEFT_UP));
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
                result.put(from + to, new Move(from, to, NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
            }
            if (to.charAt(0) == 'C') {
                if (!board.isWhiteQueensideCastlingPossible() || !board.getSquare("A1").getPiece().equals(new Rook(Color.WHITE)) || !board.getSquare("D1").isEmpty() || !board.getSquare("C1").isEmpty() || !board.getSquare("B1").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from, to,NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
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
                result.put(from + to, new Move(from, to,NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
            }
            if (to.charAt(0) == 'C') {
                if (!board.isBlackQueensideCastlingPossible() || !board.getSquare("A8").getPiece().equals(new Rook(Color.BLACK)) || !board.getSquare("D8").isEmpty() || !board.getSquare("C8").isEmpty() || !board.getSquare("B8").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from, to,NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
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

        if (board.getSquare(to).isOccupiedBy(piece.getColor())) {
            return result;
        }

        if (enPassant) {
            if (!board.getSquare(to).isEmpty()) {
                return result;
            }
        } else {
            if (board.getSquare(to).isOccupiedBy(oppositeColor)) {
                if (!capture) {
                    return result;
                }
            } else {
                if (capture) {
                    return result;
                }
            }
        }

        if ((to.charAt(1) == '8' && piece.getColor() == Color.WHITE) || (to.charAt(1) == '1' && piece.getColor() == Color.BLACK)) { // promotion
            result.put(from + to + "Q", new Move(from, to, Names.QUEEN, NotationGenerator.getShortNotation(board, from, to, Names.QUEEN, enPassant), NotationGenerator.getLongNotation(board, from, to, Names.QUEEN, enPassant)));
            result.put(from + to + "R", new Move(from, to, Names.ROOK, NotationGenerator.getShortNotation(board, from, to, Names.ROOK, enPassant), NotationGenerator.getLongNotation(board, from, to, Names.ROOK, enPassant)));
            result.put(from + to + "B", new Move(from, to, Names.BISHOP, NotationGenerator.getShortNotation(board, from, to, Names.BISHOP, enPassant), NotationGenerator.getLongNotation(board, from, to, Names.BISHOP, enPassant)));
            result.put(from + to + "N", new Move(from, to, Names.KNIGHT, NotationGenerator.getShortNotation(board, from, to, Names.KNIGHT, enPassant), NotationGenerator.getLongNotation(board, from, to, Names.KNIGHT, enPassant)));
        } else {
            result.put(from + to, new Move(from, to, NotationGenerator.getShortNotation(board, from, to, enPassant), NotationGenerator.getLongNotation(board, from, to, enPassant)));
        }
        return result;
    }

    public Map<String, Move> checkMovePossibilityForKnight(Board board, Piece piece, String from, String to) {
        Map<String, Move> result = new HashMap<>();

        if (to == null) { // out of board
            return result;
        }

        if (board.getSquare(to).isOccupiedBy(piece.getColor())) {
            return result;
        }
        result.put(from + to, new Move(from, to, NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
        return result;
    }

    public Map<String, Move> checkMovesInDirection(Board board, Piece piece, String from, Direction direction) {

        // squaresCont - how many squares to check in the line (7 - for Q, B, N, 1 - for K)
        int squaresCount;
        Map<String, Move> result = new HashMap<>();

        switch (piece.getName()) {
            case KING:
                squaresCount = 1;
                break;
            case QUEEN:
            case ROOK:
            case BISHOP:
                squaresCount= 7;
                break;
            default:
                return result;
        }

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
            if (board.getSquare(to).isOccupiedBy(piece.getColor())) break;

            result.put(from + to, new Move(from, to,  NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
            if (board.getSquare(to).isOccupiedBy(oppositeColor)) break;
        }
        return result;
    }

    public Board move(final Board board, String from, String to, Names promotedFigure) throws CloneNotSupportedException {
        from = from.toUpperCase();
        to = to.toUpperCase();

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

    private boolean validMove(final Board board, final String from, final String to, final Names promotedFigure) {

        Move move = null; // TODO
        Map<String, Move> possibleMoves = getPossibleMoves(board);
       /* if (!possibleMoves.containsValue(move))
        {
            return false;
        }*/
        return true;
    }


}
