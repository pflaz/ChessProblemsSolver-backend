package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;
import pl.waw.activeprogress.chesssolver.pieces.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void createBoardFromFenCode() {
        // Given
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        Square[][] validSquares = new Square[8][8];
        validSquares[0][7] = new Square(0, 7, new Rook(Color.BLACK));
        validSquares[0][0] = new Square(0, 0, new Rook(Color.WHITE));
        validSquares[1][7] = new Square(1, 7, new Knight(Color.BLACK));
        validSquares[1][0] = new Square(1, 0, new Knight(Color.WHITE));
        validSquares[2][7] = new Square(2, 7, new Bishop(Color.BLACK));
        validSquares[2][0] = new Square(2, 0, new Bishop(Color.WHITE));
        validSquares[3][7] = new Square(3, 7, new Queen(Color.BLACK));
        validSquares[3][0] = new Square(3, 0, new Queen(Color.WHITE));
        validSquares[4][7] = new Square(4, 7, new King(Color.BLACK));
        validSquares[4][0] = new Square(4, 0, new King(Color.WHITE));
        validSquares[5][7] = new Square(5, 7, new Bishop(Color.BLACK));
        validSquares[5][0] = new Square(5, 0, new Bishop(Color.WHITE));
        validSquares[6][7] = new Square(6, 7, new Knight(Color.BLACK));
        validSquares[6][0] = new Square(6, 0, new Knight(Color.WHITE));
        validSquares[7][7] = new Square(7, 7, new Rook(Color.BLACK));
        validSquares[7][0] = new Square(7, 0, new Rook(Color.WHITE));
        for (int i = 0; i < 8; i++) {
            validSquares[i][6] = new Square(i, 6, new Pawn(Color.BLACK));
            validSquares[i][1] = new Square(i, 1, new Pawn(Color.WHITE));
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 5; j > 1; j--) {
                validSquares[i][j] = new Square(i, j, null);
            }
        }

        // When
        Board board = new Board(fen);

        // Then
        Assert.assertArrayEquals(validSquares, board.getSquares());
        Assert.assertEquals(Color.WHITE, board.getMovingPlayer());
        Assert.assertTrue(board.isWhiteKingsideCastlingPossible());
        Assert.assertTrue(board.isWhiteQueensideCastlingPossible());
        Assert.assertTrue(board.isBlackKingsideCastlingPossible());
        Assert.assertTrue(board.isBlackQueensideCastlingPossible());
        Assert.assertNull(board.getEnPassantTarget());
        Assert.assertEquals(0, board.getHalfmoveClock());
        Assert.assertEquals(1, board.getFullmoveNumber());
    }

    @Test
    public void createFenCode() {
        // Given
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String fen2 = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        Board board = new Board(fen);
        Board board2 = new Board(fen2);
        // When
        String retrievedFen = board.getFen();
        String retirevedFen2 = board2.getFen();

        // Then
        Assert.assertEquals(fen, retrievedFen);
        Assert.assertEquals(fen2, retirevedFen2);
    }

}