package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;
import pl.waw.activeprogress.chesssolver.pieces.Names;

import java.util.Map;

public class MoverTest {

    @Test
    public void move() {
        // Given
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

    @Test
    public void getPossibleMovesOnStartPosition() {
        // Given
        String fenStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        Board board = new Board(fenStart);
        Mover mover = new Mover();

        // When
        Map<String, Move> possibleMoves = mover.getPossibleMoves(board);
        // Then
        Assert.assertEquals(20, possibleMoves.size());
    }

    @Test
    public void getPossibleMovesOnWhiteEnPassant() {
        // Given
        String fenStartWithEnPassant = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 1";
        String fenStartWithoutEnPassant = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1";
        Board boardWithEnPassant = new Board(fenStartWithEnPassant);
        Board boardWithoutEnPassant = new Board(fenStartWithoutEnPassant);

        Mover mover = new Mover();

        Move checkingMove = new Move("E5", "F6", "exf6 e.p.", "e5xf6 e.p.");

        // When
        Map<String, Move> possibleMovesWithEnPassant = mover.getPossibleMoves(boardWithEnPassant);
        Map<String, Move> possibleMovesWithoutEnPassant = mover.getPossibleMoves(boardWithoutEnPassant);
        // Then
        Assert.assertEquals(31, possibleMovesWithEnPassant.size());
        Assert.assertTrue(possibleMovesWithEnPassant.containsValue(checkingMove));
        Assert.assertEquals(30, possibleMovesWithoutEnPassant.size());
        Assert.assertFalse(possibleMovesWithoutEnPassant.containsValue(checkingMove));
    }

    @Test
    public void getPossibleMovesOnBlackEnPassant() {
        String fenStartWithEnPassant = "rnbqkbnr/ppp1p1pp/8/3pP3/5pP1/8/PPPP1P1P/RNBQKBNR b KQkq g3 0 1";
        String fenStartWithoutEnPassant = "rnbqkbnr/ppp1p1pp/8/3pP3/5pP1/8/PPPP1P1P/RNBQKBNR b KQkq - 0 1";
        Board boardWithEnPassant = new Board(fenStartWithEnPassant);
        Board boardWithoutEnPassant = new Board(fenStartWithoutEnPassant);

        Mover mover = new Mover();

        Move checkingMove = new Move("F4", "G3", "fxg3 e.p.", "f4xg3 e.p.");

        // When
        Map<String, Move> possibleMovesWithEnPassant = mover.getPossibleMoves(boardWithEnPassant);
        Map<String, Move> possibleMovesWithoutEnPassant = mover.getPossibleMoves(boardWithoutEnPassant);
        // Then
        Assert.assertEquals(27, possibleMovesWithEnPassant.size());
        Assert.assertTrue(possibleMovesWithEnPassant.containsValue(checkingMove));
        Assert.assertEquals(26, possibleMovesWithoutEnPassant.size());
        Assert.assertFalse(possibleMovesWithoutEnPassant.containsValue(checkingMove));
    }

    @Test
    public void getPossibleMovesOnWhiteKingsideCastling() {
        // Given
        String fenStartWithCastling = "rnbqkb1r/pp2p2p/5np1/2ppP3/5pP1/5P1B/PPPPN2P/RNBQK2R w KQkq - 0 1";
        String fenStartWithoutCastling = "rnbqkb1r/pp2p2p/5np1/2ppP3/5pP1/5P1B/PPPPN2P/RNBQK2R w Qkq - 0 1";
        Board boardWithCastling = new Board(fenStartWithCastling);
        Board boardWithoutCastling = new Board(fenStartWithoutCastling);
        Mover mover = new Mover();
        Move checkingMove = new Move("E1", "G1", "0-0", "0-0");

        // When
        Map<String, Move> possibleMovesWithCastling = mover.getPossibleMoves(boardWithCastling);
        Map<String, Move> possibleMovesWithoutCastling = mover.getPossibleMoves(boardWithoutCastling);
        // Then
        Assert.assertEquals(25, possibleMovesWithCastling.size());
        Assert.assertEquals(24, possibleMovesWithoutCastling.size());
        Assert.assertTrue(possibleMovesWithCastling.containsValue(checkingMove));
        Assert.assertFalse(possibleMovesWithoutCastling.containsValue(checkingMove));
    }

    @Test
    public void getPossibleMovesOnWhiteQueensideCastling() {
        // Given
        String fenStartWithCastling = "rnb1kb1r/p1q1p2p/6p1/1p1pP2n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R w KQkq - 0 1";
        String fenStartWithoutCastling = "rnb1kb1r/p1q1p2p/6p1/1p1pP2n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R w Kkq - 0 1";
        Board boardWithCastling = new Board(fenStartWithCastling);
        Board boardWithoutCastling = new Board(fenStartWithoutCastling);
        Mover mover = new Mover();
        Move checkingMove = new Move("E1", "C1","0-0-0", "0-0-0");

        // When
        Map<String, Move> possibleMovesWithCastling = mover.getPossibleMoves(boardWithCastling);
        Map<String, Move> possibleMovesWithoutCastling = mover.getPossibleMoves(boardWithoutCastling);
        // Then
        Assert.assertEquals(41, possibleMovesWithCastling.size());
        Assert.assertEquals(40, possibleMovesWithoutCastling.size());
        Assert.assertTrue(possibleMovesWithCastling.containsValue(checkingMove));
        Assert.assertFalse(possibleMovesWithoutCastling.containsValue(checkingMove));
    }

    @Test
    public void getPossibleMovesOnBlackKingsideCastling() {
        // Given
        String fenStartWithCastling = "rnb1k2r/p1q1p2p/6pb/1p1pP2n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R b KQkq - 0 1";
        String fenStartWithoutCastling = "rnb1k2r/p1q1p2p/6pb/1p1pP2n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R b KQq - 0 1";
        Board boardWithCastling = new Board(fenStartWithCastling);
        Board boardWithoutCastling = new Board(fenStartWithoutCastling);
        Mover mover = new Mover();
        Move checkingMove = new Move("E8", "G8", "0-0", "0-0");

        // When
        Map<String, Move> possibleMovesWithCastling = mover.getPossibleMoves(boardWithCastling);
        Map<String, Move> possibleMovesWithoutCastling = mover.getPossibleMoves(boardWithoutCastling);
        // Then
        Assert.assertEquals(39, possibleMovesWithCastling.size());
        Assert.assertEquals(38, possibleMovesWithoutCastling.size());
        Assert.assertTrue(possibleMovesWithCastling.containsValue(checkingMove));
        Assert.assertFalse(possibleMovesWithoutCastling.containsValue(checkingMove));
    }

    @Test
    public void getPossibleMovesOnBlackQueensideCastling() {
        // Given
        String fenStartWithCastling = "r3k2r/p1q1p2p/2n1b1pb/1p1pP2n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R b KQkq - 0 1";
        String fenStartWithoutCastling = "r3k2r/p1q1p2p/2n1b1pb/1p1pP2n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R b KQk - 0 1";
        Board boardWithCastling = new Board(fenStartWithCastling);
        Board boardWithoutCastling = new Board(fenStartWithoutCastling);
        Mover mover = new Mover();
        Move checkingMove = new Move("E8", "C8", "0-0-0", "0-0-0");

        // When
        Map<String, Move> possibleMovesWithCastling = mover.getPossibleMoves(boardWithCastling);
        Map<String, Move> possibleMovesWithoutCastling = mover.getPossibleMoves(boardWithoutCastling);
        // Then
        Assert.assertEquals(45, possibleMovesWithCastling.size());
        Assert.assertEquals(44, possibleMovesWithoutCastling.size());
        Assert.assertTrue(possibleMovesWithCastling.containsValue(checkingMove));
        Assert.assertFalse(possibleMovesWithoutCastling.containsValue(checkingMove));
    }

    @Test
    public void getPossibleMovesOnWhitePromotion() {
        // Given
        String fenStart = "r3k2r/pPq1p2p/2n1b1pb/1p1p3n/2p2pP1/2NPBP1B/PPPQN2P/R3K2R w KQkq - 0 1";
        Board board = new Board(fenStart);
        Mover mover = new Mover();
        Move checkingMove1Q = new Move("B7", "A8", Names.QUEEN, "bxa8Q", "b7xa8Q");
        Move checkingMove1R = new Move("B7", "A8", Names.ROOK, "bxa8R", "b7xa8R");
        Move checkingMove1B = new Move("B7", "A8", Names.BISHOP, "bxa8B", "b7xa8B");
        Move checkingMove1N = new Move("B7", "A8", Names.KNIGHT, "bxa8N", "b7xa8N");
        Move checkingMove2Q = new Move("B7", "B8", Names.QUEEN, "b8Q", "b7-b8Q");
        Move checkingMove2R = new Move("B7", "B8", Names.ROOK, "b8R", "b7-b8R");
        Move checkingMove2B = new Move("B7", "B8", Names.BISHOP, "b8B", "b7-b8B");
        Move checkingMove2N = new Move("B7", "B8", Names.KNIGHT, "b8N", "b7-b8N");

        // When
        Map<String, Move> possibleMovesWithPromotion = mover.getPossibleMoves(board);

        // Then
        Assert.assertEquals(48, possibleMovesWithPromotion.size());

        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1Q));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1R));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1B));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1N));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2Q));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2R));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2B));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2N));

    }

    @Test
    public void getPossibleMovesOnBlackPromotion() {
        // Given
        String fenStart = "r4k1r/pPq1p2p/2n1b1pb/1p1p3n/5pP1/2NPBP1B/PpPQN2P/R4K1R b - - 0 1";
        Board board = new Board(fenStart);
        Mover mover = new Mover();
        Move checkingMove1Q = new Move("B2", "A1", Names.QUEEN, "bxa1Q", "b2xa1Q");
        Move checkingMove1R = new Move("B2", "A1", Names.ROOK, "bxa1R", "b2xa1R");
        Move checkingMove1B = new Move("B2", "A1", Names.BISHOP, "bxa1B", "b2xa1B");
        Move checkingMove1N = new Move("B2", "A1", Names.KNIGHT, "bxa1N", "b2xa1N");
        Move checkingMove2Q = new Move("B2", "B1", Names.QUEEN, "b1Q", "b2-b1Q");
        Move checkingMove2R = new Move("B2", "B1", Names.ROOK, "b1R", "b2-b1R");
        Move checkingMove2B = new Move("B2", "B1", Names.BISHOP, "b1B", "b2-b1B");
        Move checkingMove2N = new Move("B2", "B1", Names.KNIGHT, "b1N", "b2-b1N");

        // When
        Map<String, Move> possibleMovesWithPromotion = mover.getPossibleMoves(board);

        // Then
        Assert.assertEquals(49, possibleMovesWithPromotion.size());

        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1Q));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1R));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1B));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove1N));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2Q));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2R));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2B));
        Assert.assertTrue(possibleMovesWithPromotion.containsValue(checkingMove2N));
    }
}