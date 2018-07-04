package pl.waw.activeprogress.chesssolver;

public class NotationTranslator {
    public static int getColumnNumber(String columnLetterOrSquare) {
        return Character.toUpperCase(columnLetterOrSquare.charAt(0)) - 65; // A -> 0, B -> 1...
    }

    public static int getRowNumber(String rowLetterOrSquare) {
        if (rowLetterOrSquare.length() == 2) { // square input
            return Character.digit(rowLetterOrSquare.charAt(1), 10) - 1;
        }
        return Character.digit(rowLetterOrSquare.charAt(0), 10) - 1;
    }

    public static String getColumnLetter(int columnNumber) {
        return String.valueOf((char)(columnNumber + 65));
    }

    public static String getRowLetter(int rowNumber) {
        return String.valueOf(rowNumber + 1);
    }
}
