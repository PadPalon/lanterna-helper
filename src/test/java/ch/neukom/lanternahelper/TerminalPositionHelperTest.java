package ch.neukom.lanternahelper;

import org.testng.annotations.*;

import com.google.common.collect.Lists;
import com.googlecode.lanterna.TerminalPosition;

import static ch.neukom.lanternahelper.TerminalPositionHelper.*;
import static com.google.common.truth.Truth8.*;

public class TerminalPositionHelperTest {
    @Test(dataProvider = "getPositions")
    public void testGetPositionsBetweenStartAndEnd(TerminalPosition start,
                                                   TerminalPosition end,
                                                   Iterable<TerminalPosition> expectedPositions) throws Exception {
        assertThat(getPositionsBetween(start, end)).containsExactlyElementsIn(expectedPositions);
    }

    @DataProvider
    public Object[][] getPositions() {
        return new Object[][] {
            new Object[] {
                position(0, 0),
                position(1, 1),
                Lists.newArrayList(
                    position(0, 0),
                    position(0, 1),
                    position(1, 0),
                    position(1, 1)
                )
            },
            new Object[] {
                position(1, 1),
                position(0, 0),
                Lists.newArrayList(
                    position(0, 0),
                    position(0, 1),
                    position(1, 0),
                    position(1, 1)
                )
            },
            new Object[] {
                position(3, 4),
                position(4, 3),
                Lists.newArrayList(
                    position(3, 3),
                    position(3, 4),
                    position(4, 3),
                    position(4, 4)
                )
            },
            new Object[] {
                position(2, 3),
                position(5, 5),
                Lists.newArrayList(
                    position(2, 3),
                    position(2, 4),
                    position(2, 5),
                    position(3, 3),
                    position(3, 4),
                    position(3, 5),
                    position(4, 3),
                    position(4, 4),
                    position(4, 5),
                    position(5, 3),
                    position(5, 4),
                    position(5, 5)
                )
            },
        };
    }

    private TerminalPosition position(int column, int row) {
        return new TerminalPosition(column, row);
    }
}