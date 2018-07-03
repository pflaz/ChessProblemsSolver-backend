package pl.waw.activeprogress.chesssolver;

import java.security.InvalidParameterException;

public class Chesssolver {
    public static void main(String[] args) {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        try {
            Board board = new Board(fen);
            board.print();
            String retrievedFen = board.getFen();
            System.out.println("FEN: " + board.getFen());
            System.out.println("check: " + fen.equals(retrievedFen));
        } catch (InvalidParameterException e) {
            System.out.println("Cannot create the board: " + e.getMessage());
        }
    }
}
