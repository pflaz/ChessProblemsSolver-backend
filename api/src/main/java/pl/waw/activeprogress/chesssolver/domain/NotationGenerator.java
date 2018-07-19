package pl.waw.activeprogress.chesssolver.domain;

import pl.waw.activeprogress.chesssolver.domain.pieces.Names;
import pl.waw.activeprogress.chesssolver.domain.pieces.Piece;

public class NotationGenerator {

    public static String getShortNotation(Board board, String from, String to, Names promotedFigure, boolean enPassant) {
        StringBuilder resultBuilder = new StringBuilder();
        Piece piece = board.getSquare(from).getPiece();

        String promotedFigureSign = "";
        if (promotedFigure != null) {
            promotedFigureSign = Character.toString(Piece.getShortcut(promotedFigure));
        }

        Color oppositeColor;
        if (piece.getColor() == Color.WHITE) {
            oppositeColor = Color.BLACK;
        } else {
            oppositeColor = Color.WHITE;
        }

        if ((piece.getName() == Names.KING && piece.getColor() == Color.WHITE && from.equals("E1") && to.equals("G1")) || (piece.getName() == Names.KING && piece.getColor() == Color.BLACK && from.equals("E8") && to.equals("G8"))) {
            return "0-0";
        }

        if ((piece.getName() == Names.KING && piece.getColor() == Color.WHITE && from.equals("E1") && to.equals("C1")) || (piece.getName() == Names.KING && piece.getColor() == Color.BLACK && from.equals("E8") && to.equals("C8"))) {
            return "0-0-0";
        }

        String shortMoveSign = "";

        if (board.getSquare(to).isOccupiedBy(oppositeColor)) {
            shortMoveSign = "x";
        }

        switch (piece.getName()) {
            case KING:
            case QUEEN:
            case ROOK:
            case BISHOP:
            case KNIGHT:
                resultBuilder
                        .append(piece.getShortcut())
                        .append(shortMoveSign)
                        .append(to.toLowerCase())
                        .toString();
                break;
            case PAWN:
                String shortNotationBegin = "";
                String enPassantNotation = "";

                if (enPassant || board.getSquare(to).isOccupiedBy(oppositeColor)) {
                    shortNotationBegin = Character.toString(from.charAt(0)) + "x";
                    shortNotationBegin = shortNotationBegin.toLowerCase();
                }
                if (enPassant) {
                    enPassantNotation = " e.p.";
                }

                resultBuilder
                        .append(shortNotationBegin)
                        .append(to.toLowerCase())
                        .append(enPassantNotation)
                        .append(promotedFigureSign);
                break;
        }

// TODO check
        return resultBuilder.toString();
    }

    public static String getShortNotation(Board board, String from, String to) {
        return getShortNotation(board, from, to, null, false);
    }

    public static String getShortNotation(Board board, String from, String to, boolean enPassant) {
        return getShortNotation(board, from, to, null, true);
    }

    public static String getShortNotation(Board board, String from, String to, Names promotedFigure) {
        return getShortNotation(board, from, to, promotedFigure, false);
    }

    public static String getLongNotation(Board board, String from, String to, Names promotedFigure, boolean enPassant) {
        from = from.toUpperCase();
        to = to.toUpperCase();
        StringBuilder resultBuilder = new StringBuilder();
        Piece piece = board.getSquare(from).getPiece();

        String promotedFigureSign = "";
        if (promotedFigure != null) {
            promotedFigureSign = Character.toString(Piece.getShortcut(promotedFigure));
        }

        Color oppositeColor;
        if (piece.getColor() == Color.WHITE) {
            oppositeColor = Color.BLACK;
        } else {
            oppositeColor = Color.WHITE;
        }

        if ((piece.getName() == Names.KING && piece.getColor() == Color.WHITE && from.equals("E1") && to.equals("G1")) || (piece.getName() == Names.KING && piece.getColor() == Color.BLACK && from.equals("E8") && to.equals("G8"))) {
            return "0-0";
        }

        if ((piece.getName() == Names.KING && piece.getColor() == Color.WHITE && from.equals("E1") && to.equals("C1")) || (piece.getName() == Names.KING && piece.getColor() == Color.BLACK && from.equals("E8") && to.equals("C8"))) {
            return "0-0-0";
        }

        String longMoveSign = "-";

        if (board.getSquare(to).isOccupiedBy(oppositeColor)) {
            longMoveSign = "x";
        }

        switch (piece.getName()) {
            case KING:
            case QUEEN:
            case ROOK:
            case BISHOP:
            case KNIGHT:
                resultBuilder
                        .append(piece.getShortcut())
                        .append(from.toLowerCase())
                        .append(longMoveSign)
                        .append(to.toLowerCase());
                break;

            case PAWN:
                String enPassantNotation = "";

                if (enPassant) {
                    longMoveSign = "x";
                    enPassantNotation = " e.p.";
                }

                resultBuilder
                        .append(from.toLowerCase())
                        .append(longMoveSign)
                        .append(to.toLowerCase())
                        .append(enPassantNotation)
                        .append(promotedFigureSign);
                break;
        }
// TODO check
        return resultBuilder.toString();
    }

    public static String getLongNotation(Board board, String from, String to) {
        return getLongNotation(board, from, to, null, false);
    }

    public static String getLongNotation(Board board, String from, String to, boolean enPassant) {
        return getLongNotation(board, from, to, null, true);
    }

    public static String getLongNotation(Board board, String from, String to, Names promotedFigure) {
        return getLongNotation(board, from, to, promotedFigure, false);
    }
}