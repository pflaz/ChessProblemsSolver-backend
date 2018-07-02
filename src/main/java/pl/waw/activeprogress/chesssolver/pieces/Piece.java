package pl.waw.activeprogress.chesssolver.pieces;

import pl.waw.activeprogress.chesssolver.Color;
import pl.waw.activeprogress.chesssolver.Square;

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

}

