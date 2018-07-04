package pl.waw.activeprogress.chesssolver;

import java.security.InvalidParameterException;

public class Chesssolver {
    public static void main(String[] args) {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        Board board = null;
        try {
            board = new Board(fen);
            board.print();
        } catch (InvalidParameterException e) {
            System.out.println("Cannot create the board: " + e.getMessage());
        }


    }
}
