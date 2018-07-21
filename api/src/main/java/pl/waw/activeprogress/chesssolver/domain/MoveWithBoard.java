package pl.waw.activeprogress.chesssolver.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoveWithBoard {
    private Move move;
    private Board board;
    private List<MoveWithBoard> nextMoves;


    public MoveWithBoard(Move move, Board board) {
        this.move = move;
        this.board = board;
        this.nextMoves = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
    }

    public Move getMove() {
        return move;
    }

    public List<MoveWithBoard> getNextMoves() {
        return nextMoves;
    }

    public void addNextMove(MoveWithBoard nextMoveWithBoard) {
        getNextMoves().add(nextMoveWithBoard);
    }

    public void addNextMovesList(List<MoveWithBoard> nextMoveWithBoardList) {
        getNextMoves().addAll(nextMoveWithBoardList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveWithBoard that = (MoveWithBoard) o;
        return Objects.equals(board, that.board) &&
                Objects.equals(move, that.move) &&
                Objects.equals(nextMoves, that.nextMoves);
    }

    @Override
    public int hashCode() {

        return Objects.hash(board, move, nextMoves);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (board.getFullmoveNumber() > 1) {
            for (int i = 0; i < board.getFullmoveNumber(); i++) {
                result.append("    ");
            }
        }
        if (board.getMovingPlayer() == Color.BLACK) {
            result.append("    ");
        }
        result.append("Move: ").append(move).append(" | Board: ").append(board).append(" | \n");

        if (nextMoves.size() > 0) {
            result.append(nextMoves).append("\n");
        }
        return result.toString();
    }
}
