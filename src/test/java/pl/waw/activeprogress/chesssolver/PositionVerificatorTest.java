package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionVerificatorTest {

    @Test
    public void isPositionCorrect() {
        // Given
        PositionVerificator positionVerificator = new PositionVerificator();
        String[] fens = {
                "4k3/p2pp2p/2N3p1/2p5/5pP1/1B3P2/PPr4P/3r1K1R w KQ - 0 1", // T
                "4k3/p2pp2p/2N3p1/2p5/5pP1/1B3P2/PPr4P/3r1K1R b KQ - 0 1", // F
                "4k3/p2pp2p/3N2p1/2p5/5pP1/1B3P2/PPrr3P/5K1R w KQ - 0 1" // F

        };

        boolean[] correctResults = {true, false, false};

        // When
        boolean[] results = new boolean[fens.length];
        for (int i = 0; i < fens.length; i++) {
            results[i] = positionVerificator.isPositionCorrect(new Board(fens[i]));
        }

        // Then
        Assert.assertArrayEquals(correctResults, results);
    }

}