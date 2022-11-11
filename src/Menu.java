import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.*;
//import com.googlecode.lanterna.gui2.AbstractTextGUI.processInput;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Menu {

    public static ArrayList<String> readTitle() {
        ArrayList<String> title = new ArrayList<>();
        File file = new File("title.txt");
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                title.add(nextLine);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return title;
    }

    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        TerminalSize terminalSize = new TerminalSize(120,120);
        Screen screen = null;
        int choice = 0;
        String[] labels = {
                "Reaction time",
                "Number memory",
                "Verbal memory",
                "Typing"
        };
        Measures measures = new Measures();
        Boxes boxes = new Boxes();
        ArrayList<String> title = readTitle();
        int shift = -5;
        int col, row, width = 20, heigth = 3;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
//            SwingTerminalFrame terminal = new SwingTerminalFrame(TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);
//            terminal.setSize(120,120);
//            SwingTerminalFrame terminal = new SwingTerminalFrame("Human Benchmark",terminalSize, TerminalEmulatorDeviceConfiguration.getDefault(),SwingTerminalFontConfiguration.getDefault(),TerminalEmulatorColorConfiguration.getDefault(),TerminalEmulatorAutoCloseTrigger.CloseOnEscape);
//            String font = Font.SANS_SERIF;
//            SwingTerminalFontConfiguration swingTerminalFontConfiguration = new SwingTerminalFontConfiguration(false, AWTTerminalFontConfiguration.BoldMode.EVERYTHING, Font.getFont(Font.MONOSPACED));
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            boxes.drawTitle(title, terminal, screen);

            for (int i = 0 ; i< 4; i++) {
                col = (terminal.getTerminalSize().getColumns()-width)/2;
                row = (terminal.getTerminalSize().getRows())/2+shift;
                if (i == choice) {
                    boxes.drawBoldButton(labels[i],terminal, screen,col,row,width,heigth);
                } else boxes.drawButton(labels[i],terminal, screen,col,row,width,heigth);
                shift += 4;
            }

            screen.refresh();

            while(true) {
                screen.doResizeIfNecessary();
                screen.clear();
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                    break;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowDown && choice < 3) {
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
                        case 2 :
                            measures.measureVerbalMemory(terminal, screen);
                            break;
                        case 3:
                            measures.measureTyping(terminal, screen);
                            screen.setCursorPosition(null);
                            break;
                    }
                }
                boxes.drawTitle(title, terminal, screen);

                shift = -5;
                for (int i = 0 ; i< 4; i++) {
                    col = (terminal.getTerminalSize().getColumns()-width)/2;
                    row = (terminal.getTerminalSize().getRows())/2+shift;
                    if (i == choice) {
                        boxes.drawBoldButton(labels[i],terminal, screen,col,row,width,heigth);  //labels[i].length()+3
                    } else boxes.drawButton(labels[i],terminal, screen,col,row,width,heigth);
                    shift += 4;
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
