package ch.neukom.lanternahelper;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;

/**
 * this class reads and monitors input of any {@link InputProvider}
 */
public class LanternaInputOutput extends Observable {
    private final InputProvider inputProvider;

    /**
     * @param inputProvider the class whose input you want to use
     */
    public LanternaInputOutput(InputProvider inputProvider) {
        this.inputProvider = inputProvider;
    }

    /**
     * reads the input from the {@link TerminalScreen} until '\n' is entered,
     * '\b' is interpreted as a backspace
     *
     * @return the joined string of all entered characters
     */
    public String readLine() {
        StringBuilder lineBuilder = new StringBuilder();
        char input;
        do {
            KeyStroke keyStroke = readInput();
            input = keyStroke.getCharacter();
            setChanged();
            notifyObservers(input);
            switch (input) {
                case '\b':
                    if(lineBuilder.length() > 0) {
                        lineBuilder.setLength(lineBuilder.length() - 1);
                    }
                    break;
                case '\n':
                    break;
                default:
                    lineBuilder.append(input);
            }
        } while(input != '\n');
        return lineBuilder.toString();
    }

    private KeyStroke readInput() {
        try {
            return inputProvider.readInput();
        } catch (IOException e) {
            throw new IllegalStateException("Could not read input from Lanterna", e);
        }
    }

    /**
     * specialized {@link Observer} that checks for specific changes in the {@link Observable}
     */
    public interface LanternaInputObserver extends Observer {
        @Override
        default void update(Observable observable, Object argument) {
            if(argument instanceof Character) {
                char character = (char) argument;
                handleControlCharacters(character);
                characterEntered(character);
            }
        }

        default void handleControlCharacters(char character) {
            if (character == '\b') {
                lastCharacterRemoved();
            } else if (character == '\n') {
                lineEntered();
            }
        }

        /**
         * a new character was added to the input
         * @param character the character that was added
         */
        void characterEntered(char character);

        /**
         * the last character that was added to the input was removed
         */
        void lastCharacterRemoved();

        /**
         * the current line was finished
         */
        void lineEntered();
    }
}
