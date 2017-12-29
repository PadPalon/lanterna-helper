package ch.neukom.lanternahelper;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * this class provied helper methods to write longer text to a {@link Screen}
 */
public class LanternaTextWriter {
    private final Screen screen;

    public LanternaTextWriter(Screen screen) {
        this.screen = screen;
    }

    /**
     * print some text from a starting point to the edge of the terminal,
     * new lines are respected
     * @param text the text to print
     * @param column the column where you want to start printing
     * @param row the row where you want to start printing
     */
    public void printText(String text, int column, int row) {
        TerminalSize terminalSize = screen.getTerminalSize();
        printTextArea(text, column, row, terminalSize.getColumns(), terminalSize.getRows());
    }

    /**
     * print some text from a starting point in a given area,
     * new lines are respected
     * @param text the text to print
     * @param column the column where you want to start printing
     * @param row the row where you want to start printing
     * @param limitColumn the column where you want the text to end, this gets reduced to the terminal size if bigger
     * @param limitRow the row where you want the text to end, this gets reduced to the terminal size if bigger
     */
    public void printTextArea(String text, int column, int row, int limitColumn, int limitRow) {
        TerminalSize terminalSize = screen.getTerminalSize();
        print(text, column, row, getLimitColumn(limitColumn, terminalSize), getLimitRow(limitRow, terminalSize));
    }

    private int getLimitColumn(int limitColumn, TerminalSize terminalSize) {
        int terminalColumns = terminalSize.getColumns();
        return limitColumn > terminalColumns ? terminalColumns : limitColumn;
    }

    private int getLimitRow(int limitRow, TerminalSize terminalSize) {
        int terminalRows = terminalSize.getRows();
        return limitRow > terminalRows ? terminalRows : limitRow;
    }

    private void print(String text, int column, int row, int limitColumn, int limitRow) {
        int columnCount = column;
        int rowCount = row;
        for (char character : text.toCharArray()) {
            if(character == '\n') {
                columnCount = column;
                rowCount++;
            } else if (character != '\r') {
                if(columnCount >= limitColumn) {
                    columnCount = column;
                    rowCount++;
                }

                if(rowCount >= limitRow) {
                    break;
                }

                screen.setCharacter(columnCount, rowCount, new TextCharacter(character));
                columnCount++;
            }
        }

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new IllegalStateException("Printing to lanterna failed");
        }
    }
}
