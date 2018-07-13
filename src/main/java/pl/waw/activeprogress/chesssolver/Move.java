package pl.waw.activeprogress.chesssolver;

import pl.waw.activeprogress.chesssolver.pieces.Names;

import java.util.Objects;

public class Move {
    private String name;
    private String from;
    private String to;
    private Names promotedFigure;
    private String shortNotation;
    private String longNotation;

    public Move(String name, String from, String to, Names promotedFigure, String shortNotation, String longNotation) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.promotedFigure = promotedFigure;
        this.shortNotation = shortNotation;
        this.longNotation = longNotation;
    }

    public String getName() {
        return name;
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
        return Objects.equals(name, move.name) &&
                Objects.equals(from, move.from) &&
                Objects.equals(to, move.to) &&
                promotedFigure == move.promotedFigure &&
                Objects.equals(shortNotation, move.shortNotation) &&
                Objects.equals(longNotation, move.longNotation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, from, to, promotedFigure, shortNotation, longNotation);
    }
}
