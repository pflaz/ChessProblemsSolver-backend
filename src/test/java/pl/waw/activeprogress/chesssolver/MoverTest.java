package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoverTest {

    @Test
    public void move() {
        // Given
        System.out.println("test");
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String validFenAfterMove = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        Board boardStart = new Board(fenStart);

        // When
        Mover mover = new Mover();
        Board boardAfterMove = null;
        try {
            boardAfterMove = mover.move(boardStart, "E2", "E4");
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
        }
        String fenAfterMove = boardAfterMove.getFen();

        // Then

        System.out.println("start:");
        boardStart.print();
        System.out.println("after:");
        boardAfterMove.print();

        Assert.assertEquals(validFenAfterMove, fenAfterMove);
        Assert.assertEquals(Color.WHITE, boardStart.getMovingPlayer());
        Assert.assertEquals(Color.BLACK, boardAfterMove.getMovingPlayer());
    }
}