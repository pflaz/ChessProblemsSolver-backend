package pl.waw.activeprogress.chesssolver.dto;

import java.util.List;

public class MoveWithBoardDto {
    private String moveName;
    private String shortNotation;
    private String longNotation;
    private String fen;
    private List<MoveWithBoardDto> nextMoves;
    private String error;

    public MoveWithBoardDto(String moveName, String shortNotation, String longNotation, String fen, List<MoveWithBoardDto> nextMoves) {
        this.moveName = moveName;
        this.shortNotation = shortNotation;
        this.longNotation = longNotation;
        this.fen = fen;
        this.nextMoves = nextMoves;
        this.error = null;
    }

    public MoveWithBoardDto(String error) {
        this.error = error;
    }

    public MoveWithBoardDto() {
    }

    public String getMoveName() {
        return moveName;
    }

    public String getShortNotation() {
        return shortNotation;
    }

    public String getLongNotation() {
        return longNotation;
    }

    public String getFen() {
        return fen;
    }

    public List<MoveWithBoardDto> getNextMoves() {
        return nextMoves;
    }
}
