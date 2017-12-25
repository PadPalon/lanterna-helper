package ch.neukom.lanternahelper;

import org.testng.annotations.*;

import org.easymock.IMocksControl;

import com.google.common.truth.Truth;

import static ch.neukom.lanternahelper.LanternaInputOutput.*;
import static com.google.common.truth.Truth.*;
import static org.easymock.EasyMock.*;

public class LanternaInputObserverTest {
    private IMocksControl mocksControl = createControl();
    private LanternaInputOutput inputOutput = mocksControl.createMock(LanternaInputOutput.class);

    @BeforeMethod
    public void resetMocks() {
        mocksControl.reset();
    }

    @Test
    public void testCharacterEntered() throws Exception {
        FakeLanternaObserver observer = new FakeLanternaObserver();
        mocksControl.replay();
        observer.update(inputOutput, 'd');

        assertThat(observer.isCharacterEnteredHit()).isTrue();
        assertThat(observer.isLastCharacterRemovedHit()).isFalse();
        assertThat(observer.isLineEnteredHit()).isFalse();
    }

    @Test
    public void testLastCharacterRemoved() throws Exception {
        FakeLanternaObserver observer = new FakeLanternaObserver();
        mocksControl.replay();
        observer.update(inputOutput, '\b');

        assertThat(observer.isCharacterEnteredHit()).isTrue();
        assertThat(observer.isLastCharacterRemovedHit()).isTrue();
        assertThat(observer.isLineEnteredHit()).isFalse();
    }

    @Test
    public void testLineEntered() throws Exception {
        FakeLanternaObserver observer = new FakeLanternaObserver();
        mocksControl.replay();
        observer.update(inputOutput, '\n');

        assertThat(observer.isCharacterEnteredHit()).isTrue();
        assertThat(observer.isLastCharacterRemovedHit()).isFalse();
        assertThat(observer.isLineEnteredHit()).isTrue();
    }

    @AfterMethod
    public void verifyMocks() {
        mocksControl.verify();
    }

    private static class FakeLanternaObserver implements LanternaInputObserver {
        private boolean characterEnteredHit = false;
        private boolean lastCharacterRemovedHit = false;
        private boolean lineEnteredHit = false;

        @Override
        public void characterEntered(char character) {
            characterEnteredHit = true;
        }

        @Override
        public void lastCharacterRemoved() {
            lastCharacterRemovedHit = true;
        }

        @Override
        public void lineEntered() {
            lineEnteredHit = true;
        }

        public boolean isLineEnteredHit() {
            return lineEnteredHit;
        }

        public boolean isLastCharacterRemovedHit() {
            return lastCharacterRemovedHit;
        }

        public boolean isCharacterEnteredHit() {
            return characterEnteredHit;
        }
    }
}