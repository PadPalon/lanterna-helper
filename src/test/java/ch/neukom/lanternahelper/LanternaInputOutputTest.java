package ch.neukom.lanternahelper;

import java.io.IOException;

import org.testng.annotations.*;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.google.common.truth.Truth;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.KeyStroke;

import static com.google.common.truth.Truth.*;
import static org.easymock.EasyMock.*;

public class LanternaInputOutputTest {
    private IMocksControl mocksControl = createControl();
    private InputProvider inputProvider = mocksControl.createMock(InputProvider.class);

    @BeforeMethod
    public void resetMocks() {
        mocksControl.reset();
    }

    @Test
    public void testReadLine() throws IOException {
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("\b"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("h"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("e"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("l"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("l"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("b"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("\b"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("o"));
        expect(inputProvider.readInput()).andReturn(KeyStroke.fromString("\n"));

        mocksControl.replay();
        LanternaInputOutput lanternaInputOutput = new LanternaInputOutput(inputProvider);
        String line = lanternaInputOutput.readLine();

        assertThat(line).isEqualTo("hello");
    }

    @AfterMethod
    public void verifyMocks() {
        mocksControl.verify();
    }
}