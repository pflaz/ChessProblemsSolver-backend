package pl.waw.activeprogress.chesssolver;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotationTranslatorTest {

    @Test
    public void getColumnNumber() {

        // Given
        String[] columnLetters = {"A", "b", "C", "d", "E", "f", "G", "h"};
        int[] retrievedColumns = new int[8];
        int[] validColumns = {0, 1, 2, 3, 4, 5, 6, 7};

        // When
        for (int i = 0; i < columnLetters.length; i++) {
            retrievedColumns[i] = NotationTranslator.getColumnNumber(columnLetters[i]);
        }

        // Then
        Assert.assertArrayEquals(validColumns, retrievedColumns);

    }

    @Test
    public void getRowNumber() {
        // Given
        String[] rowStrings = {"1", "a2", "3", "b4", "5", "c6", "7", "d8"};
        int[] retrievedRows = new int[8];
        int[] validRows = {0, 1, 2, 3, 4, 5, 6, 7};

        // When
        for (int i = 0; i < rowStrings.length; i++) {
            retrievedRows[i] = NotationTranslator.getRowNumber(rowStrings[i]);
        }

        // Then
        Assert.assertArrayEquals(validRows, retrievedRows);
    }

    @Test
    public void getColumnLetter() {
        // Given
        int[] columns = {0, 1, 2, 3, 4, 5, 6, 7};
        String[] retrievedColumns = new String[8];
        String[] validColumns = {"A", "B", "C", "D", "E", "F", "G", "H"};

        // When
        for (int i = 0; i < columns.length; i++) {
            retrievedColumns[i] = NotationTranslator.getColumnLetter(columns[i]);
        }

        // Then
        Assert.assertArrayEquals(validColumns, retrievedColumns);
    }

    @Test
    public void getRowLetter() {
        // Given
        int[] rows = {0, 1, 2, 3, 4, 5, 6, 7};
        String[] retrievedRows = new String[8];
        String[] validRows = {"1", "2", "3", "4", "5", "6", "7", "8"};

        // When
        for (int i = 0; i < rows.length; i++) {
            retrievedRows[i] = NotationTranslator.getRowLetter(rows[i]);
        }

        // Then
        Assert.assertArrayEquals(validRows, retrievedRows);
    }
}