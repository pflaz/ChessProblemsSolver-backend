package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;
import pl.waw.activeprogress.chesssolver.pieces.*;

import java.util.HashMap;
import java.util.Map;

public class BoardTest {

    @Test
    public void createBoardFromFenCode() {
        // Given
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        Map<String, Square> validSquares = new HashMap<>();
        validSquares.put("A8", new Square('A', 8, new Rook(Color.BLACK)));
        validSquares.put("B8", new Square('B', 8, new Knight(Color.BLACK)));
        validSquares.put("C8", new Square('C', 8, new Bishop(Color.BLACK)));
        validSquares.put("D8", new Square('D', 8, new Queen(Color.BLACK)));
        validSquares.put("E8", new Square('E', 8, new King(Color.BLACK)));
        validSquares.put("F8", new Square('F', 8, new Bishop(Color.BLACK)));
        validSquares.put("G8", new Square('G', 8, new Knight(Color.BLACK)));
        validSquares.put("H8", new Square('H', 8, new Rook(Color.BLACK)));
        validSquares.put("A1", new Square('A', 1, new Rook(Color.WHITE)));
        validSquares.put("B1", new Square('B', 1, new Knight(Color.WHITE)));
        validSquares.put("C1", new Square('C', 1, new Bishop(Color.WHITE)));
        validSquares.put("D1", new Square('D', 1, new Queen(Color.WHITE)));
        validSquares.put("E1", new Square('E', 1, new King(Color.WHITE)));
        validSquares.put("F1", new Square('F', 1, new Bishop(Color.WHITE)));
        validSquares.put("G1", new Square('G', 1, new Knight(Color.WHITE)));
        validSquares.put("H1", new Square('H', 1, new Rook(Color.WHITE)));

        for (char col = 'A'; col <= 'H'; col++) {
            validSquares.put(Character.toString(col) + "7", new Square(col, 7, new Pawn(Color.BLACK)));
            validSquares.put(Character.toString(col) + "2", new Square(col, 2, new Pawn(Color.WHITE)));
        }
        for (char col = 'A'; col <= 'H'; col++) {
            for (int row = 6; row >= 3; row--) {
                validSquares.put(Character.toString(col) + row, new Square(col, row, null));
            }
        }

        // When
        Board board = new Board(fen);

        // Then
        Assert.assertEquals(validSquares, board.getSquares());
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
        String retrievedFen2 = board2.getFen();

        // Then
        Assert.assertEquals(fen, retrievedFen);
        Assert.assertEquals(fen2, retrievedFen2);
    }

}