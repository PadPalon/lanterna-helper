package ch.neukom.lanternahelper;

import java.io.IOException;

import org.testng.annotations.*;

import org.easymock.IMocksControl;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

import static org.easymock.EasyMock.*;

public class LanternaTextWriterTest {
    private IMocksControl mocksControl = createControl();
    private Screen screen = mocksControl.createMock(Screen.class);

    @BeforeMethod
    public void resetMocks() {
        mocksControl.reset();
    }

    @Test
    public void testPrintCharacterToColumn() throws Exception {
        screen.setCharacter(new TerminalPosition(3, 0), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 1), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 2), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 3), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 4), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 5), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 6), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 7), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 8), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 9), asCharacter('I'));
        screen.setCharacter(new TerminalPosition(3, 10), asCharacter('I'));

        setupWriter().printCharacterToColumn('I', 3);
    }

    @Test
    public void testPrintCharacterToRow() throws Exception {
        screen.setCharacter(new TerminalPosition(0, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(1, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(2, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(3, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(4, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(5, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(6, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(7, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(8, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(9, 5), asCharacter('0'));
        screen.setCharacter(new TerminalPosition(10, 5), asCharacter('0'));

        setupWriter().printCharacterToRow('0', 5);
    }

    @Test
    public void testPrintCharacterToArea() throws Exception {
        screen.setCharacter(new TerminalPosition(3, 4), asCharacter('-'));
        screen.setCharacter(new TerminalPosition(3, 5), asCharacter('-'));
        screen.setCharacter(new TerminalPosition(4, 4), asCharacter('-'));
        screen.setCharacter(new TerminalPosition(4, 5), asCharacter('-'));

        setupWriter().printCharacterToArea('-', new TerminalPosition(3, 4), new TerminalPosition(4, 5));
    }

    @Test
    public void testPrintText() throws Exception {
        screen.setCharacter(1, 3, asCharacter('t'));
        screen.setCharacter(2, 3, asCharacter('e'));
        screen.setCharacter(3, 3, asCharacter('x'));
        screen.setCharacter(4, 3, asCharacter('t'));

        setupWriter().printText("text", 1, 3);
    }

    @Test
    public void testPrintTextAtEdge() throws Exception {
        screen.setCharacter(9, 9, asCharacter('t'));

        setupWriter().printText("te\nxt", 9, 9);
    }

    @Test
    public void testPrintTextArea() throws Exception {
        screen.setCharacter(5, 5, asCharacter('t'));
        screen.setCharacter(5, 6, asCharacter('e'));
        screen.setCharacter(6, 6, asCharacter('x'));

        setupWriter().printTextArea("t\next long", 5, 5, 7, 7);
    }

    @Test
    public void testPrintTextAreaTooBig() throws Exception {
        screen.setCharacter(9, 9, asCharacter('t'));

        setupWriter().printTextArea("te\nxt", 9, 9, 11, 11);
    }

    @AfterMethod
    public void verifyMocks() {
        mocksControl.verify();
    }

    private LanternaTextWriter setupWriter() throws IOException {
        expect(screen.getTerminalSize()).andStubReturn(new TerminalSize(10, 10));
        screen.refresh();

        mocksControl.replay();
        return new LanternaTextWriter(screen);
    }

    private TextCharacter asCharacter(char character) {
        return new TextCharacter(character);
    }
}