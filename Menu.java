import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
//import com.googlecode.lanterna.gui2.AbstractTextGUI.processInput;

import java.io.IOException;

public class Menu {

    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        int choice = 0;
        String[] labels = {
                "Reaction time",
                "Number memory",
                "Verbal memory"
        };
        Measures measures = new Measures();
        Boxes boxes = new Boxes();
        int shift = -5;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            for (int i = 0 ; i< 3; i++) {
                if (i == choice) {
                    boxes.drawBoldBox(labels[i], terminal, screen, shift);
                } else boxes.drawBox(labels[i], terminal, screen, shift);
                shift += 3;
            }
            screen.refresh();

            while(true) {
                screen.doResizeIfNecessary();
                screen.clear();
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                    break;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowDown && choice < 2) {
                    choice++;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowUp && choice > 0) {
                    choice--;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                    switch (choice) {
                        case 0 :
//                            measures.start();
                            measures.measureReactionTime(terminal, screen);
                            break;
                        case 1 :
                            measures.measureNumberMemory(terminal,screen);
                            break;
                    }
                }

                shift = -5;
                for (int i = 0 ; i< 3; i++) {
                    if (i == choice) {
                        boxes.drawBoldBox(labels[i], terminal, screen, shift);
                    } else boxes.drawBox(labels[i], terminal, screen, shift);
                    shift += 3;
                }
                screen.refresh();
//                Thread.yield();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if(screen != null) {
                try {
                    /*
                    The close() call here will restore the terminal by exiting from private mode which was done in
                    the call to startScreen()
                     */
                    screen.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
