package pl.waw.activeprogress.chesssolver.pieces;

import pl.waw.activeprogress.chesssolver.Color;
import pl.waw.activeprogress.chesssolver.Square;

import java.util.Objects;

public abstract class Piece implements Cloneable {
    private final Names name;
    private final char shortcut;
    private final Color color;


    public Piece(Names name, Color color) {
        this.name = name;
        this.color = color;
        switch (name) {
            case KING:
                this.shortcut = 'K';
                break;
            case QUEEN:
                this.shortcut = 'Q';
                break;
            case ROOK:
                this.shortcut = 'R';
                break;
            case BISHOP:
                this.shortcut = 'B';
                break;
            case KNIGHT:
                this.shortcut = 'N';
                break;
            case PAWN:
                this.shortcut = 'P';
                break;
            default:
                this.shortcut = 0;
        }
    }

    public Names getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public char getShortcut() {
        return shortcut;
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

