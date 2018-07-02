package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Piece;

public class Square {
    private final int column;
    private final int row;
    private Piece piece;
    private final String name;
    private final Color color;

    public Square(int column, int row, Piece piece) {
        this.column = column;
        this.row = row;
        this.piece = piece;
        Character columnChar = (char)(column + 65);
        String columnLetter = columnChar.toString();
        this.name = columnLetter + (row + 1);
        if ((column + row) % 2 == 0) {
            this.color = Color.BLACK;
        } else {
            this.color = Color.WHITE;
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Piece getPiece() {
        return piece;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
