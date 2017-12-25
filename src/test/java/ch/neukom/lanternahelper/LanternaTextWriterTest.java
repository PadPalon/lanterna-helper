package ch.neukom.lanternahelper;

import org.testng.annotations.*;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.*;

public class LanternaTextWriterTest {
    private IMocksControl mocksControl = createControl();
    private Screen screen = mocksControl.createMock(Screen.class);

    @BeforeMethod
    public void resetMocks() {
        mocksControl.reset();
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

    private LanternaTextWriter setupWriter() {
        expect(screen.getTerminalSize()).andStubReturn(new TerminalSize(10, 10));

        mocksControl.replay();
        return new LanternaTextWriter(screen);
    }

    private TextCharacter asCharacter(char character) {
        return new TextCharacter(character);
    }
}