package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Piece;

import java.util.Objects;

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

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return column == square.column &&
                row == square.row &&
                Objects.equals(piece, square.piece) &&
                Objects.equals(name, square.name) &&
                color == square.color;
    }

    @Override
    public int hashCode() {

        return Objects.hash(column, row, piece, name, color);
    }
}
