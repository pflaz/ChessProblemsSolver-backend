package pl.waw.activeprogress.chesssolver;

import java.util.HashMap;
import java.util.Map;

public class Solver {

    public Map<String, Move> searchCheckmate(final Board board, final int movesCount) {
        Map<String, Move> result = new HashMap<>(); //<fen, Move>

        PositionVerificator positionVerificator = new PositionVerificator();
        Mover mover = new Mover();

        if (!positionVerificator.isPositionCorrect(board)) {
            return null;
        }

        if (mover.isCheckmate(board)) {
            return result;
        }

        if (movesCount == 1) {
            Map<String, Move> correctMoves = mover.getCorrectMoves(board);
            for (Move theMove: correctMoves.values()) {
                Board boardAfterMove = mover.move(board, theMove.getFrom(), theMove.getTo(), theMove.getPromotedFigure());
                if (mover.isCheckmate(boardAfterMove)) {
                    result.put(boardAfterMove.getFen(), theMove);
                }
            }
        }
        return result;
    }
}
