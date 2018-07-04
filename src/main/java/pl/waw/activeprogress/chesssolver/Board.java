package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.*;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

public class Board implements Cloneable {
    private final Square[][] squares;
    private Color movingPlayer;
    private final boolean whiteKingsideCastlingPossible;
    private final boolean whiteQueensideCastlingPossible;
    private final boolean blackKingsideCastlingPossible;
    private final boolean blackQueensideCastlingPossible;
    private String enPassantTarget;
    private final int halfmoveClock; // halfmoves since the last capture or pawn advance
    private int fullmoveNumber;

    public Board() {
        movingPlayer = Color.WHITE;
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


    public Board(String fen) {
        String[] fenParts = fen.split(" ");
        if (fenParts.length != 6) {
            throw new InvalidParameterException("FEN code should consists of 6 parts divided by space");
        }
        String[] fenRows = fenParts[0].split("/");

        ///// fenParts[0] -> pieces

        if (fenRows.length != 8) {
            throw new InvalidParameterException("FEN's 1st part should consists of 8 parts divided by '/'");
        }

        squares = new Square[8][8];
        int row, col;
        row = 7;
        for (int fenRow = 0; fenRow < 8; fenRow++) { // reading 7 rows divided by '/'
            char[] charsInFenRow = fenRows[fenRow].toCharArray();
            col = 0;
            for (char theChar: charsInFenRow) {
                Piece piece = null;
                switch (theChar) {
                    case 'K':
                        piece = new King(Color.WHITE);
                        break;
                    case 'k':
                        piece = new King(Color.BLACK);
                        break;
                    case 'Q':
                        piece = new Queen(Color.WHITE);
                        break;
                    case 'q':
                        piece = new Queen(Color.BLACK);
                        break;
                    case 'R':
                        piece = new Rook(Color.WHITE);
                        break;
                    case 'r':
                        piece = new Rook(Color.BLACK);
                        break;
                    case 'N':
                        piece = new Knight(Color.WHITE);
                        break;
                    case 'n':
                        piece = new Knight(Color.BLACK);
                        break;
                    case 'B':
                        piece = new Bishop(Color.WHITE);
                        break;
                    case 'b':
                        piece = new Bishop(Color.BLACK);
                        break;
                    case 'P':
                        piece = new Pawn(Color.WHITE);
                        break;
                    case 'p':
                        piece = new Pawn(Color.BLACK);
                        break;
                }
                if (piece != null) {
                    Square square = new Square(col, row, piece);
                    squares[col][row] = square;
                    col++;
                } else if ((Character.getNumericValue(theChar) >= 1) && (Character.getNumericValue(theChar) <= 8)) {
                    for (int i = 0; i < Character.getNumericValue(theChar); i++) {
                        Square square = new Square(col, row, null);
                        squares[col][row] = square;
                        col++;
                    }
                } else {
                    throw new InvalidParameterException("Invalid character in description of row " + (row + 1) + ", column " + (col + 1));
                }
            }
            row--;
        }

        ///// fenParts[1] -> movingPlayer
        if (fenParts[1].equals("w")) {
            movingPlayer = Color.WHITE;
        } else if (fenParts[1].equals("b")) {
            movingPlayer = Color.BLACK;
        } else {
            throw new InvalidParameterException("Invalid character in the 2nd part of FEN code. 'w' or 'b' expected");
        }

        ///// fenParts[2] -> castlings
        if (fenParts[2].contains("K")) {
            whiteKingsideCastlingPossible = true;
        } else {
            whiteKingsideCastlingPossible = false;
        }
        if (fenParts[2].contains("Q")) {
            whiteQueensideCastlingPossible = true;
        } else {
            whiteQueensideCastlingPossible = false;
        }
        if (fenParts[2].contains("k")) {
            blackKingsideCastlingPossible = true;
        } else {
            blackKingsideCastlingPossible = false;
        }
        if (fenParts[2].contains("q")) {
            blackQueensideCastlingPossible = true;
        } else {
            blackQueensideCastlingPossible = false;
        }

        ///// fenParts[3] -> en passant
        if (fenParts[3].length() == 2) {
            enPassantTarget = fenParts[3];
        } else {
            enPassantTarget = null;
        }

        ///// fenParts[4] -> halfMoveClock
        try {
            halfmoveClock = Integer.parseInt(fenParts[4]);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid 5th part of FEN code (number expected)");
        }

        ///// fenParts[5] -> fullMoveNumber
        try {
            fullmoveNumber = Integer.parseInt(fenParts[5]);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid 6th part of FEN code (number expected)");
        }
    }


    @Override
    protected Board clone() throws CloneNotSupportedException {
        return (Board)super.clone();
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Color getMovingPlayer() {
        return movingPlayer;
    }

    public void setMovingPlayer(Color movingPlayer) {
        this.movingPlayer = movingPlayer;
    }

    public boolean isWhiteKingsideCastlingPossible() {
        return whiteKingsideCastlingPossible;
    }

    public boolean isWhiteQueensideCastlingPossible() {
        return whiteQueensideCastlingPossible;
    }

    public boolean isBlackKingsideCastlingPossible() {
        return blackKingsideCastlingPossible;
    }

    public boolean isBlackQueensideCastlingPossible() {
        return blackQueensideCastlingPossible;
    }

    public String getEnPassantTarget() {
        return enPassantTarget;
    }

    public void setEnPassantTarget(String enPassantTarget) {
        this.enPassantTarget = enPassantTarget;
    }

    public int getHalfmoveClock() {
        return halfmoveClock;
    }

    public int getFullmoveNumber() {
        return fullmoveNumber;
    }

    public void setFullmoveNumber(int fullmoveNumber) {
        this.fullmoveNumber = fullmoveNumber;
    }

    public String getFen() {
        Piece piece;
        StringBuffer result = new StringBuffer();
        char letter = 0;
        int blankSquaresCount;
        for (int row = 7; row >= 0; row--) {
            blankSquaresCount = 0;
            for (int col = 0; col < 8; col++) {
                piece = getSquares()[col][row].getPiece();
                if (piece != null && blankSquaresCount > 0) {
                    result.append(blankSquaresCount);
                    blankSquaresCount = 0;
                }
                if (piece != null) {
                    switch (piece.getName()) {
                        case KING:
                            letter = 'K';
                            break;
                        case QUEEN:
                            letter = 'Q';
                            break;
                        case ROOK:
                            letter = 'R';
                            break;
                        case KNIGHT:
                            letter = 'N';
                            break;
                        case BISHOP:
                            letter = 'B';
                            break;
                        case PAWN:
                            letter = 'P';
                    }
                    if (piece.getColor() == Color.WHITE) {
                        letter = Character.toUpperCase(letter);
                    } else {
                        letter = Character.toLowerCase(letter);
                    }
                    result.append(letter);
                } else {
                    blankSquaresCount++;
                }
            }
            if (blankSquaresCount > 0) {
                result.append(blankSquaresCount);
            }
            if (row > 0) {
                result.append("/");
            }
        }

        if (getMovingPlayer() == Color.BLACK) {
            result.append(" b ");
        } else {
            result.append(" w ");
        }

        StringBuffer castlings = new StringBuffer();
        if (isWhiteKingsideCastlingPossible()) {
            castlings.append("K");
        }
        if (isWhiteQueensideCastlingPossible()) {
            castlings.append("Q");
        }
        if (isBlackKingsideCastlingPossible()) {
            castlings.append("k");
        }
        if (isBlackQueensideCastlingPossible()) {
            castlings.append("q");
        }
        if (castlings.length() == 0) {
            castlings.append("-");
        }
        result.append(castlings).append(" ");

        if (getEnPassantTarget() != null) {
            result.append(getEnPassantTarget()).append(" ");
        } else {
            result.append("- ");
        }

        result.append(getHalfmoveClock()).append(" ").append(getFullmoveNumber());
        return result.toString();
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
                    letter = piece.getName().toString().charAt(0);
                    if (piece.getColor() == Color.WHITE) {
                        letter = Character.toUpperCase(letter);
                    } else {
                        letter = Character.toLowerCase(letter);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return whiteKingsideCastlingPossible == board.whiteKingsideCastlingPossible &&
                whiteQueensideCastlingPossible == board.whiteQueensideCastlingPossible &&
                blackKingsideCastlingPossible == board.blackKingsideCastlingPossible &&
                blackQueensideCastlingPossible == board.blackQueensideCastlingPossible &&
                halfmoveClock == board.halfmoveClock &&
                fullmoveNumber == board.fullmoveNumber &&
                Arrays.deepEquals(squares, board.squares) &&
                movingPlayer == board.movingPlayer &&
                Objects.equals(enPassantTarget, board.enPassantTarget);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(movingPlayer, whiteKingsideCastlingPossible, whiteQueensideCastlingPossible, blackKingsideCastlingPossible, blackQueensideCastlingPossible, enPassantTarget, halfmoveClock, fullmoveNumber);
        result = 31 * result + Arrays.deepHashCode(squares);
        return result;
    }
}

