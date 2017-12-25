## Synopsis

A tiny library that helps reading, monitoring and printing input of lanterna consoles.

## Code Example

This example starts a console that reprints every input at the top and jumps down when backspace is pressed.
The finished line gets printed at the bottom of the console.
When typing "stop", the console closes.
```java
public class Example {
    public static void main(String[] args) throws IOException {
        TerminalScreen screen = new DefaultTerminalFactory().createScreen();
        LanternaInputOutput inputOutput = new LanternaInputOutput(screen.getTerminal());
        LanternaTextWriter textWriter = new LanternaTextWriter(screen);
 
        inputOutput.addObserver(new LanternaInputObserver() {
            private int column = 0;
            private int row = 0;
 
            @Override
            public void characterEntered(char character) {
                textWriter.printText(Character.toString(character), column, row);
                column++;
            }
 
            @Override
            public void lastCharacterRemoved() {
                textWriter.printText(" ", column, row);
            }
 
            @Override
            public void lineEntered() {
                column = 0;
                row++;
            }
        });
 
        screen.startScreen();
 
        while (true) {
            String line = inputOutput.readLine();
            TerminalSize terminalSize = screen.getTerminalSize();
            textWriter.printText(line, 0, terminalSize.getRows() - 1);
            if(line.equals("stop")) {
                System.exit(0);
            }
        }
    }
}
```

## Motivation

In a recent project I wanted to use a console that was easier to handle than stdin and stdout. Lanterna offered exactly
that for displaying things. Handling input though was a bit difficult. I wanted to implement a interface inspired by
the old text parser style adventure games. So I had to build myself a system that would allow me to easily monitor
the input and print it to the console. This is my attempt at a more generalized version of what I had built.

## Tests

Tests are written on TestNG and can be run through that. Just run everything in the `ch.neukom.lanternahelper` package.

## License

MIT