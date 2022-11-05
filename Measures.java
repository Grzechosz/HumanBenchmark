import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Scanner;

public class Measures {


    public void measureReactionTime(Terminal terminal, Screen screen) {
        screen.clear();
        try {
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(2, 1, "Kliknij enter a następnie czekaj aż zmieni się kolor na zielony", SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke1 = screen.readInput();
            while (keyStroke1.getKeyType() != KeyType.Tab) {   //keyStroke1.getKeyType() != KeyType.Tab
                if (keyStroke1.getKeyType() == KeyType.Enter) {
                    textGraphics.setBackgroundColor(TextColor.ANSI.RED);
                    textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                    textGraphics.fill(' ');
                    textGraphics.putString(2, 1, "Kliknij enter a następnie czekaj aż zmieni się kolor na zielony", SGR.BOLD);

                    screen.refresh();
                    ReactionTime reactionTime = new ReactionTime();
                    reactionTime.measureReactionTime();
                    reactionTime.setStart_time(System.nanoTime());
                    textGraphics.setBackgroundColor(TextColor.ANSI.GREEN);
                    textGraphics.fill(' ');
                    screen.refresh();
                    keyStroke1 = screen.readInput();

                    if (keyStroke1.getKeyType() == KeyType.Tab) {
                        break;
                    }

                    if (keyStroke1.getKeyType() == KeyType.Enter) {
                        reactionTime.setEnd_time(System.nanoTime());
                        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                        textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - ("twoj czas to:" + reactionTime.getReactionTime() + "ms").length() / 2, terminal.getTerminalSize().getRows() / 2, "twoj czas to:" + reactionTime.getReactionTime() + "ms", SGR.BOLD);
                        screen.refresh();
                    } else if (keyStroke1.getKeyType() == KeyType.Tab) {
                        break;
                    }
                }
                screen.refresh();
                keyStroke1 = screen.readInput();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }screen.clear();

//        finally {
//            if (screen != null) {
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
    }

    public void measureNumberMemory(Terminal terminal, Screen screen) {
        Boxes boxes = new Boxes();
        screen.clear();
        NumberMemory numberMemory = new NumberMemory();
        try {
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(terminal.getTerminalSize().getColumns() / 2- ("Kliknij enter aby rozpocząć").length()/2, 1, "Kliknij enter aby rozpocząć", SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke1 = screen.readInput();
            while (keyStroke1.getKeyType() != KeyType.Tab) {  //  keyStroke1.getKeyType() != KeyType.Tab
                if (keyStroke1.getKeyType() == KeyType.Enter) {
                    screen.clear();
                    long number = numberMemory.drawNewNumber();
                    int columnStart = terminal.getTerminalSize().getColumns()/2-10;
                    int columnEnd = terminal.getTerminalSize().getColumns()/2+10;
                    int row = terminal.getTerminalSize().getRows()/2+10;
                    TerminalPosition progressBarStart = new TerminalPosition(columnStart,row);
                    TerminalPosition progressBarEnd = new TerminalPosition(columnEnd, row);
                    textGraphics.putString(terminal.getTerminalSize().getColumns() / 2- Long.toString(number).length()/2, terminal.getTerminalSize().getRows() / 2,Long.toString(number) , SGR.BOLD);
                    screen.refresh();
                    textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                    textGraphics.drawLine(progressBarStart,progressBarEnd,Symbols.BLOCK_SOLID);
                    screen.refresh();
                    textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                    for (int i = columnEnd; i >= columnStart; i--) {
                        textGraphics.putString(i,row,String.valueOf(Symbols.BLOCK_SOLID));
                        Thread.sleep(1000*numberMemory.getLevel()/20);
                        screen.refresh();
                    }
                    textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                    screen.clear();
                    boxes.drawBox((" ").repeat(50),terminal, screen, terminal.getTerminalSize().getRows()/4);
                    screen.refresh();
                    Writing writing = new Writing();
                    keyStroke1 = screen.readInput();
                     while (keyStroke1.getKeyType() != KeyType.Enter){   //for (int i = 0; i < numberMemory.getLevel(); i++)
                        keyStroke1 = screen.readInput();
                        if (keyStroke1.getKeyType() == KeyType.Character) {
                            writing.appendText(keyStroke1.getCharacter());
                            textGraphics.putString(terminal.getTerminalSize().getColumns()/2-writing.getText().length()/2,terminal.getTerminalSize().getRows()/2+terminal.getTerminalSize().getRows()/4+1,writing.getText() , SGR.BOLD);
                        } else if (keyStroke1.getKeyType() == KeyType.Backspace && writing.getText().compareTo("") != 0) {
                            writing.cutLastChar();
                            textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - writing.getText().length() / 2 - writing.getText().length(),terminal.getTerminalSize().getRows()/2+terminal.getTerminalSize().getRows()/4+1,(" ").repeat(writing.getText().length()+1) , SGR.BOLD);
                            textGraphics.putString(terminal.getTerminalSize().getColumns()/2-writing.getText().length()/2,terminal.getTerminalSize().getRows()/2+terminal.getTerminalSize().getRows()/4+1,writing.getText() , SGR.BOLD);

                        }
//                         keyStroke1 = screen.readInput();
                        screen.refresh();
                    }
//                    screen.refresh();
//                    Thread.sleep(2000);

                    numberMemory.nextLevel();
                }
                keyStroke1 = screen.readInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        screen.clear();
    }
}
