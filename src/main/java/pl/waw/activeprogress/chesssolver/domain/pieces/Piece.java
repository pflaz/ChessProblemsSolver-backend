package pl.waw.activeprogress.chesssolver.domain.pieces;

import pl.waw.activeprogress.chesssolver.domain.Color;

import java.util.Objects;

public abstract class Piece implements Cloneable {
    private final Names name;
    private final Color color;

    public Piece(Names name, Color color) {
        this.name = name;
        this.color = color;

    }

    public Names getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public char getShortcut() {
        return getShortcut(getName());
    }

    public static char getShortcut(Names pieceName) {
        switch (pieceName) {
            case KING:
                return 'K';
            case QUEEN:
                return 'Q';
            case ROOK:
                return 'R';
            case BISHOP:
                return 'B';
            case KNIGHT:
                return 'N';
            case PAWN:
                return 'P';
            default:
                return 0;
        }
    }

    @Override
    public Piece clone() throws CloneNotSupportedException {
        return (Piece)super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return name == piece.name &&
                color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}

