package ch.neukom.lanternahelper;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

import static ch.neukom.lanternahelper.TerminalPositionHelper.*;

/**
 * this class provied helper methods to write longer text to a {@link Screen}
 */
public class LanternaTextWriter {
    private final Screen screen;

    /**
     * @param screen the screen you want to print to
     */
    public LanternaTextWriter(Screen screen) {
        this.screen = screen;
    }

    /**
     * print a character to a entire column of the terminal
     * @param character the character to print
     * @param column the column to print to
     */
    public void printCharacterToColumn(char character, int column) {
        int terminalRows = screen.getTerminalSize().getRows();
        printCharacterToArea(character, new TerminalPosition(column, 0), new TerminalPosition(column, terminalRows));
    }

    /**
     * print a character to a entire row of the terminal
     * @param character the character to print
     * @param row the row to print to
     */
    public void printCharacterToRow(char character, int row) {
        int terminalColumns = screen.getTerminalSize().getColumns();
        printCharacterToArea(character, new TerminalPosition(0, row), new TerminalPosition(terminalColumns, row));
    }

    /**
     * print a character to a area of the terminal, defined by two points
     * @param character the character to print
     * @param start the starting point of the area
     * @param end the ending point of the area
     */
    public void printCharacterToArea(char character, TerminalPosition start, TerminalPosition end) {
        getPositionsBetween(start, end).forEach(position -> screen.setCharacter(position, new TextCharacter(character)));
        refreshScreen();
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

        refreshScreen();
    }

    private void refreshScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new IllegalStateException("Printing to lanterna failed");
        }
    }
}
