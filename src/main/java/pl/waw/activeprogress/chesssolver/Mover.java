package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.*;

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

    public Map<String, Move> getCorrectMoves(final Board board) {
        // returns possible moves and checks if after these moves there is no check
        Map<String, Move> result = new HashMap<>();
        Map<String, Move> possibleMoves = getPossibleMoves(board);
        for (Map.Entry<String, Move> theMove: possibleMoves.entrySet()) {
            Board newBoard;
            try {
                newBoard = board.copy();
            } catch (CloneNotSupportedException e) {
                return result;
            }
            String from = theMove.getValue().getFrom();
            String to = theMove.getValue().getTo();
            Piece movingPiece = newBoard.getSquare(from).getPiece();
            newBoard.getSquare(from).setPiece(null);
            newBoard.getSquare(to).setPiece(movingPiece);
            if (theMove.getValue().getPromotedFigure() != null) {
                Piece newPiece = null;
                switch (theMove.getValue().getPromotedFigure()) {
                    case QUEEN:
                        newPiece = new Queen(newBoard.getMovingPlayer());
                        break;
                    case ROOK:
                        newPiece = new Rook(newBoard.getMovingPlayer());
                        break;
                    case BISHOP:
                        newPiece = new Bishop(newBoard.getMovingPlayer());
                        break;
                    case KNIGHT:
                        newPiece = new Knight(newBoard.getMovingPlayer());
                        break;
                }
                newBoard.getSquare(to).setPiece(newPiece);
            }

            if (isCheck(newBoard)) {
                continue;
            }
            result.put(theMove.getKey(), theMove.getValue());
        }
        return result;
    }

    public Map<String, Move> getPossibleMoves(final Board board, boolean ignoreCastlingsAndEnPassant) {
        // ignoreCastlingsAndEnPassant parameter - don't verify castlings end en passant - using in isCheck method
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

                    if (!ignoreCastlingsAndEnPassant) {
                        to = getModifiedSquareName(from, 2, 0);
                        result.putAll(checkCastling(board, piece, from, to));
                        to = getModifiedSquareName(from, -2, 0);
                        result.putAll(checkCastling(board, piece, from, to));
                        break;
                    }

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
                        if (!ignoreCastlingsAndEnPassant) {
                            if (from.charAt(1) == '5' && board.getEnPassantTarget() != null) {
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
                        if (!ignoreCastlingsAndEnPassant) {
                            if (from.charAt(1) == '4' && board.getEnPassantTarget() != null) {
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
                    }
                    break;
            }
        });

        return result;
    }

    public Map<String, Move> getPossibleMoves(final Board board) {
        return getPossibleMoves(board, false);
    }

    public Map<String, Move> checkCastling(Board board, Piece piece, String from, String to) {
        Map<String, Move> result = new HashMap<>();
        if (from.charAt(0) != 'E') return result;
        if (to.charAt(0) != 'G' && to.charAt(0) != 'C') {
            return result;
        }
        if (isCheck(board)) {
            return result;
        }

        String squareBetweenFromAndTo = null;
        if (to.charAt(0) == 'G') {
            squareBetweenFromAndTo = "F" + from.charAt(1);
        }
        if (to.charAt(0) == 'C') {
            squareBetweenFromAndTo = "D" + from.charAt(1);
        }

        try { // moving King to the square between 'from' and 'to' without checking environment (ie. without getting all possible moves - cause stack overflow)
            Board newBoard = board.copy();
            newBoard.getSquare(from).setPiece(null);
            newBoard.getSquare(squareBetweenFromAndTo).setPiece(piece);

            if (isCheck(newBoard)) {
                return result;
            }

        } catch (CloneNotSupportedException e) {
            throw new InvalidParameterException("Invalid board - there is no possibility to clone.");
        }

        if (piece.getColor() == Color.WHITE) {
            if (from.charAt(1) != '1' || to.charAt(1) != '1') {
                return result;
            }
            if (to.charAt(0) == 'G') {
                if (board.getSquare("H1").getPiece() == null) return result;
                if (!board.isWhiteKingsideCastlingPossible() ||
                        !board.getSquare("H1").getPiece().equals(new Rook(Color.WHITE)) ||
                        !board.getSquare("F1").isEmpty() ||
                        !board.getSquare("G1").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from, to, NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
            }
            if (to.charAt(0) == 'C') {
                if (board.getSquare("A1").getPiece() == null) return result;
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
                if (board.getSquare("H8").getPiece() == null) return result;
                if (!board.isBlackKingsideCastlingPossible() || !board.getSquare("H8").getPiece().equals(new Rook(Color.BLACK)) || !board.getSquare("F8").isEmpty() || !board.getSquare("G8").isEmpty()) {
                    return result;
                }
                result.put(from + to, new Move(from, to,NotationGenerator.getShortNotation(board, from, to), NotationGenerator.getLongNotation(board, from, to)));
            }
            if (to.charAt(0) == 'C') {
                if (board.getSquare("A8").getPiece() == null) return result;
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

    public boolean isCheck(final Board board) {
        Board boardWithOppositePlayerMoving;
        try {
            boardWithOppositePlayerMoving = board.copy();
        } catch (CloneNotSupportedException e) {
            return false;
        }
        boardWithOppositePlayerMoving.switchMovingPlayer();

        Map<String, Move> possibleMoves = getPossibleMoves(boardWithOppositePlayerMoving, true); // don't get castlings end en passant - it is impossible to capture King by castling or en passant and checking it in this place would cause stachoverflow error (isCheck -> getPossibleMoves -> isCheck...)
        for (Map.Entry<String, Move> thePossibleMove: possibleMoves.entrySet()) {
            String toSquare = thePossibleMove.getValue().getTo();
            Piece pieceOnToSquare = boardWithOppositePlayerMoving.getSquare(toSquare).getPiece();
            if (pieceOnToSquare == null) continue;
            if (pieceOnToSquare.getName() == Names.KING && pieceOnToSquare.getColor() != boardWithOppositePlayerMoving.getMovingPlayer()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(final Board board) {
        if (!isCheck(board)) return false;

        Map<String, Move> correctMoves = getCorrectMoves(board);
        if (correctMoves.size() == 0) {
            return true;
        }
        return false;
    }

    public Board move(final Board board, String from, String to, final Names promotedFigure) {
        from = from.toUpperCase();
        to = to.toUpperCase();
        Board newBoard;

        try {
            newBoard = board.copy();
        } catch (CloneNotSupportedException e) {
            throw new InvalidParameterException("Invalid 'board' parameter - there is no possibility to do 'move' on that board.");
        }

        if (from.charAt(0) < 'A' || from.charAt(0) > 'H' || to.charAt(0) < 'A' || to.charAt(0) > 'H' || from.charAt(1) < '1' || from.charAt(1) > '8' || to.charAt(1) < '1' || to.charAt(1) > '8') {
            throw new InvalidParameterException("Invalid 'from' or 'to' parameter - should be from 'A1' to 'H8'");
        }

        Piece movingPiece = newBoard.getSquare(from).getPiece();
        if (movingPiece == null) {
            throw new InvalidParameterException("There is no piece on 'from' square");
        }

        if (!validMove(board, from, to, promotedFigure)) {
            throw new InvalidParameterException("Invalid move");
        }

        newBoard.getSquare(from).setPiece(null);
        newBoard.getSquare(to).setPiece(movingPiece);

        if (promotedFigure != null) {
            Piece newPiece = null;
            switch (promotedFigure) {
                case QUEEN:
                    newPiece = new Queen(newBoard.getMovingPlayer());
                    break;
                case ROOK:
                    newPiece = new Rook(newBoard.getMovingPlayer());
                    break;
                case BISHOP:
                    newPiece = new Bishop(newBoard.getMovingPlayer());
                    break;
                case KNIGHT:
                    newPiece = new Knight(newBoard.getMovingPlayer());
                    break;
            }
            newBoard.getSquare(to).setPiece(newPiece);
        }

        // set en passant possibility
        newBoard.setEnPassantTarget(null);
        if (movingPiece.getName() == Names.PAWN && movingPiece.getColor() == Color.WHITE && from.charAt(1) == '2' && to.charAt(1) == '4') {
            newBoard.setEnPassantTarget(Character.toLowerCase(from.charAt(0)) + "3");
        }
        if (movingPiece.getName() == Names.PAWN && movingPiece.getColor() == Color.BLACK && from.charAt(1) == '7' && to.charAt(1) == '5') {
            newBoard.setEnPassantTarget(Character.toLowerCase(from.charAt(0)) + "6");
        }

        // set castlings possibility
        if (movingPiece.getName() == Names.KING && movingPiece.getColor() == Color.WHITE) {
            newBoard.setWhiteKingsideCastlingPossible(false);
            newBoard.setWhiteQueensideCastlingPossible(false);
        }
        if (movingPiece.getName() == Names.KING && movingPiece.getColor() == Color.BLACK) {
            newBoard.setBlackKingsideCastlingPossible(false);
            newBoard.setBlackQueensideCastlingPossible(false);
        }
        if (newBoard.isWhiteQueensideCastlingPossible() && movingPiece.getColor() == Color.WHITE && movingPiece.getName() == Names.ROOK && from.equals("A1")) {
            newBoard.setWhiteQueensideCastlingPossible(false);
        }
        if (newBoard.isWhiteKingsideCastlingPossible() && movingPiece.getColor() == Color.WHITE && movingPiece.getName() == Names.ROOK && from.equals("H1")) {
            newBoard.setWhiteKingsideCastlingPossible(false);
        }
        if (newBoard.isBlackQueensideCastlingPossible() && movingPiece.getColor() == Color.BLACK && movingPiece.getName() == Names.ROOK && from.equals("A8")) {
            newBoard.setBlackQueensideCastlingPossible(false);
        }
        if (newBoard.isBlackKingsideCastlingPossible() && movingPiece.getColor() == Color.BLACK && movingPiece.getName() == Names.ROOK && from.equals("H8")) {
            newBoard.setBlackKingsideCastlingPossible(false);
        }

        // set fullMoveNumber
        if (newBoard.getMovingPlayer() == Color.BLACK) {
            newBoard.setFullmoveNumber(newBoard.getFullmoveNumber() + 1);
        }

        newBoard.switchMovingPlayer();
        return newBoard;
    }


    public Board move(final Board board, final String from, final String to) {
        return move(board, from, to, null);
    }

    private boolean validMove(final Board board, final String from, final String to, final Names promotedFigure) {

        String moveNameToCheck = new StringBuilder(from).append(to).append(promotedFigure == null ? "" : Piece.getShortcut(promotedFigure)).toString().toUpperCase();
        // verication of moves' names only (not all Move object) because for correct verification with Move object we have to put notation of move (en passant including)
        // this is no needed to verify Move object - name only is enough
        Map<String, Move> correctMoves = getCorrectMoves(board);
        if (!correctMoves.containsKey(moveNameToCheck))
        {
            return false;
        }
        return true;
    }


}
