package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void searchCheckmate() {
        // Given
        Solver solver = new Solver();
        String fen = "4k3/p2pp2p/2N3p1/2p5/5pP1/1B3P2/PPrr3P/5K1R w KQ - 0 1";
        String fen2 = "4k3/p2pp2p/3N2p1/2p5/5pP1/1B3P2/PPrr3P/5K1R w KQ - 0 1";
        String fen3 = "5k2/p2pp1pp/1RRN4/2p5/5pP1/1B3P2/PPrr3P/5K2 w KQ - 0 1";

        Board board = new Board(fen);
        Board board2 = new Board(fen2);
        Board board3 = new Board(fen3);
        // When
        Map<String, Move> result = solver.searchCheckmate(board, 1);
        Map<String, Move> result2 = solver.searchCheckmate(board2, 1);
        Map<String, Move> result3 = solver.searchCheckmate(board3, 1);

        // Then
        Assert.assertEquals(0, result.size());
        Assert.assertEquals(null, result2);
        Assert.assertEquals(2, result3.size());
    }
}