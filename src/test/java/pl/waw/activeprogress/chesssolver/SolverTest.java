package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SolverTest {

    @Test
    public void searchCheckmateIn1Move() {
        // Given
        Solver solver = new Solver();
        String[] fens = {
                "4k3/p2pp2p/2N3p1/2p5/5pP1/1B3P2/PPrr3P/5K1R w KQ - 0 1",
                "5k2/p2pp1pp/1RRN4/2p5/5pP1/1B3P2/PPrr3P/5K2 w - - 0 1",
                "8/8/8/8/8/5p2/2R4B/5K1k w - - 0 1",
                "8/8/8/8/8/2R5/5p1B/5K1k w - - 0 1",
                "2k5/5R2/8/8/7R/8/8/2K5 w - - 0 1"
        };

        int[] correctSizes = {0, 2, 0, 0, 1};
        int[] retrievedSizes = new int[fens.length];

        // When

        for (int i = 0; i < fens.length; i++) {
            Board board = new Board(fens[i]);
            List<MoveWithBoard> result = solver.getSolutionsForCheckmate(board, 1);
            retrievedSizes[i] = result.size();

            if (i == fens.length - 1) {
                System.out.println("Last case:");
                System.out.println("Solutions: " + result.size());
                System.out.println(result);
            }
        }

        // Then
        Assert.assertArrayEquals(correctSizes, retrievedSizes);

    }

    @Test
    public void searchCheckmateIn2Moves() {
        // Given
        Solver solver = new Solver();
        String[] fens = {
                "4k3/p2pp2p/2N3p1/2p5/5pP1/1B3P2/PPrr3P/5K1R w KQ - 0 1",
                "8/8/8/8/8/5p2/2R4B/5K1k w - - 0 1",
                "8/8/8/8/8/2R5/5p1B/5K1k w - - 0 1",
                "8/8/2Qp2K1/4k3/4N3/6p1/2R5/8 w - - 0 1",
                "8/1R6/3p4/pN1p4/1k6/8/2K5/2RB4 w - - 0 1",
                "4r3/6k1/8/8/K7/8/8/2r5 b - - 0 1"
        };

        int[] correctSizes = {0, 0, 0, 1, 1, 2};
        int[] retrievedSizes = new int[fens.length];

        // When

        for (int i = 0; i < fens.length; i++) {
            Board board = new Board(fens[i]);
            List<MoveWithBoard> result = solver.getSolutionsForCheckmate(board, 2);
            retrievedSizes[i] = result.size();

            if (i == fens.length - 1) {
                System.out.println("Last case:");
                System.out.println("Solutions: " + result.size());
                System.out.println(result);
            }
        }

        // Then
        Assert.assertArrayEquals(correctSizes, retrievedSizes);

    }

    @Test
    public void searchCheckmateIn3Moves() {
        // Given
        Solver solver = new Solver();
        String[] fens = {
                "4k3/p2pp2p/2N3p1/2p5/5pP1/1B3P2/PPrr3P/5K1R w KQ - 0 1",
                "8/8/8/8/8/5p2/2R4B/5K1k w - - 0 1",
                "8/8/8/8/8/2R5/5p1B/5K1k w - - 0 1",
                "3Q4/8/8/8/4b3/7N/7p/2K4k w - - 0 1",
                "8/6R1/8/8/3K4/2Qb3k/8/3r4 w - - 0 1",
                "3k1n2/8/8/3N4/6B1/8/1K4Q1/8 w - - 0 1"
        };

        int[] correctSizes = {0, 1, 14, 1, 1, 1};
        int[] retrievedSizes = new int[fens.length];

        // When

        for (int i = 0; i < fens.length; i++) {
            Board board = new Board(fens[i]);
            List<MoveWithBoard> result = solver.getSolutionsForCheckmate(board, 3);
            retrievedSizes[i] = result.size();

            if (i == fens.length - 1) {
                System.out.println("Last case:");
                System.out.println("Solutions: " + result.size());
                System.out.println(result);
            }
        }

        // Then
        Assert.assertArrayEquals(correctSizes, retrievedSizes);

    }

    @Test
    public void searchCheckmateIn4Moves() {
        // Given
        Solver solver = new Solver();
        String[] fens = {
                "8/8/4K3/1BkN4/8/2NpP3/3P4/8 w - - 0 1",
        };

        int[] correctSizes = {1};
        int[] retrievedSizes = new int[fens.length];

        // When

        for (int i = 0; i < fens.length; i++) {
            Board board = new Board(fens[i]);
            List<MoveWithBoard> result = solver.getSolutionsForCheckmate(board, 4);
            retrievedSizes[i] = result.size();

            if (i == fens.length - 1) {
                System.out.println("Last case:");
                System.out.println("Solutions: " + result.size());
                System.out.println(result);
            }
        }

        // Then
        Assert.assertArrayEquals(correctSizes, retrievedSizes);

    }
}