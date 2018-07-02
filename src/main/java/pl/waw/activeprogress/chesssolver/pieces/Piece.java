package pl.waw.activeprogress.chesssolver.pieces;

import pl.waw.activeprogress.chesssolver.Color;
import pl.waw.activeprogress.chesssolver.Square;

public abstract class Piece {
    private final Names name;
    private final Color color;
    private Square square;


    public Piece(Names name, Color color, Square square) {
        this.name = name;
        this.color = color;
        this.square = square;
    }

    public Names getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}

