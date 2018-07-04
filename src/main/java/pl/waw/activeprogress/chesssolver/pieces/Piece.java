package pl.waw.activeprogress.chesssolver.pieces;

import pl.waw.activeprogress.chesssolver.Color;
import pl.waw.activeprogress.chesssolver.Square;

import java.util.Objects;

public abstract class Piece {
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

