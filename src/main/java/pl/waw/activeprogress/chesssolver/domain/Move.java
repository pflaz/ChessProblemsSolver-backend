package pl.waw.activeprogress.chesssolver.domain;

import pl.waw.activeprogress.chesssolver.domain.pieces.Names;
import pl.waw.activeprogress.chesssolver.domain.pieces.Piece;

import java.util.Objects;

public class Move {
    private final String from;
    private final String to;
    private final Names promotedFigure;
    private final String shortNotation;
    private final String longNotation;

    public Move(String from, String to, Names promotedFigure, String shortNotation, String longNotation) {
        this.from = from;
        this.to = to;
        this.promotedFigure = promotedFigure;
        this.shortNotation = shortNotation;
        this.longNotation = longNotation;
    }

    public Move(String from, String to, String shortNotation, String longNotation) {
        this(from, to, null, shortNotation, longNotation);
    }

    public String getName() {
        String promotedFigureSign = "";
        if (promotedFigure != null) {
            promotedFigureSign = Character.toString(Piece.getShortcut(promotedFigure));
        }
        return new StringBuilder(from).append(to).append(promotedFigureSign).toString().toUpperCase();
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getShortNotation() {
        return shortNotation;
    }

    public String getLongNotation() {
        return longNotation;
    }

    public Names getPromotedFigure() {
        return promotedFigure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return  Objects.equals(from, move.from) &&
                Objects.equals(to, move.to) &&
                promotedFigure == move.promotedFigure &&
                Objects.equals(shortNotation, move.shortNotation) &&
                Objects.equals(longNotation, move.longNotation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(from, to, promotedFigure, shortNotation, longNotation);
    }
}
