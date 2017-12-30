package ch.neukom.lanternahelper;

import java.util.stream.Stream;

import com.googlecode.lanterna.TerminalPosition;

import static java.util.stream.IntStream.*;

/**
 * helper functions to deal with positions on a terminal
 */
public class TerminalPositionHelper {
    private TerminalPositionHelper() {
    }

    /**
     * used to get all positions for an area<br>
     * <br>
     * example<br>
     * start: (1, 1)<br>
     * end: (2, 3)<br>
     * stream would contain (1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3)
     *
     * @param start the start position
     * @param end the end position
     * @return a stream containing all possible points between the two positions
     */
    public static Stream<TerminalPosition> getPositionsBetween(TerminalPosition start, TerminalPosition end) {
        int startColumn;
        int endColumn;
        if(start.getColumn() <= end.getColumn()) {
            startColumn = start.getColumn();
            endColumn = end.getColumn();
        } else {
            startColumn = end.getColumn();
            endColumn = start.getColumn();
        }

        int startRow;
        int endRow;
        if(start.getRow() <= end.getRow()) {
            startRow = start.getRow();
            endRow = end.getRow();
        } else {
            startRow = end.getRow();
            endRow = start.getRow();
        }

        return rangeClosed(startColumn, endColumn)
            .mapToObj(column -> rangeClosed(startRow, endRow).mapToObj(row -> new TerminalPosition(column, row)))
            .flatMap(stream -> stream);
    }
}
