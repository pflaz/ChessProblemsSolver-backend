package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Piece;

import java.util.Objects;

public class Square implements Cloneable {
    private final char column;
    private final int row;
    private Piece piece;
    private final String name;
    private final Color color;

    public Square(char column, int row, Piece piece) {
        this.column = column;
        this.row = row;
        this.piece = piece;
        this.name = new StringBuilder(this.column).append(this.row).toString();
        if ((column + row) % 2 == 0) {
            this.color = Color.BLACK;
        } else {
            this.color = Color.WHITE;
        }
    }

    public char getColumn() {
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
    protected Square clone() throws CloneNotSupportedException {
        return (Square)super.clone();
    }

    public Square copy() throws CloneNotSupportedException {
        Square newSquare = clone();
        if (piece != null) {
            newSquare.setPiece(newSquare.getPiece().clone());
        }
        return newSquare;
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
