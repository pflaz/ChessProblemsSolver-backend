package pl.waw.activeprogress.chesssolver.service;

import pl.waw.activeprogress.chesssolver.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolverService {

    public List<MoveWithBoard> getSolutionsForCheckmate(final Board board, final int movesCount) {

        List<MoveWithBoard> result = new ArrayList<>();

        PositionVerificator positionVerificator = new PositionVerificator();
        Mover mover = new Mover();

        if (!positionVerificator.isPositionCorrect(board)) {
            return null;
        }

        if (mover.isCheckmate(board)) {
            return result;
        }

        Map<String, Move> correctMovesForHalfMove1 = mover.getCorrectMoves(board);
        for (Move halfMove1 : correctMovesForHalfMove1.values()) {
            Board boardAfterHalfMove1 = mover.move(board, halfMove1.getFrom(), halfMove1.getTo(), halfMove1.getPromotedFigure());
            MoveWithBoard moveWithBoardForHalfMove1 = new MoveWithBoard(halfMove1, boardAfterHalfMove1);

            if (movesCount == 1) {
                if (mover.isCheckmate(boardAfterHalfMove1)) {
                    result.add(moveWithBoardForHalfMove1);
                }
            } else {
                Map<String, Move> correctMovesForHalfMove2 = mover.getCorrectMoves(boardAfterHalfMove1);
                if (correctMovesForHalfMove2.size() == 0 && !mover.isCheckmate(moveWithBoardForHalfMove1.getBoard())) { //stalemate
                    continue;
                }
                boolean isSolution = true;
                for (Move halfMove2 : correctMovesForHalfMove2.values()) {
                    Board boardAfterHalfMove2 = mover.move(boardAfterHalfMove1, halfMove2.getFrom(), halfMove2.getTo(), halfMove2.getPromotedFigure());
                    MoveWithBoard moveWithBoardForHalfMove2 = new MoveWithBoard(halfMove2, boardAfterHalfMove2);
                    List<MoveWithBoard> solutionsForOneMoveLess = getSolutionsForCheckmate(boardAfterHalfMove2, movesCount - 1);
                    if (solutionsForOneMoveLess.size() == 0) {
                        isSolution = false;
                        break;
                    }
                    moveWithBoardForHalfMove2.addNextMovesList(solutionsForOneMoveLess);
                    moveWithBoardForHalfMove1.addNextMove(moveWithBoardForHalfMove2);
                }
                if (isSolution) {
                    result.add(moveWithBoardForHalfMove1);
                }
            }
        }
        return result;
    }
}
