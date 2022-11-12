import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Measures {

    public void measureReactionTime(Terminal terminal, Screen screen) {
        screen.clear();
        try {
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            String information = "Kliknij enter a następnie czekaj aż kolor zmieni się na zielony";
            String shortInformation = "Czekaj aż kolor zmieni się na zielony";
            textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - information.length()/2, 3, information, SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke1 = screen.readInput();
            while (keyStroke1.getKeyType() != KeyType.Escape) {   //keyStroke1.getKeyType() != KeyType.Tab
                if (keyStroke1.getKeyType() == KeyType.Enter) {
                    textGraphics.setBackgroundColor(TextColor.ANSI.RED);
                    textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                    textGraphics.fill(' ');
                    textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - shortInformation.length()/2, 3, shortInformation, SGR.BOLD);

                    screen.refresh();
                    ReactionTime reactionTime = new ReactionTime();
                    reactionTime.measureReactionTime();
                    reactionTime.setStart_time(System.nanoTime());
                    textGraphics.setBackgroundColor(TextColor.ANSI.GREEN);
                    textGraphics.fill(' ');
                    screen.refresh();
                    keyStroke1 = screen.readInput();

                    if (keyStroke1.getKeyType() == KeyType.Escape) {
                        break;
                    }

                    if (keyStroke1.getKeyType() == KeyType.Enter) {
                        reactionTime.setEnd_time(System.nanoTime());
                        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                        textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - ("twoj czas to:" + reactionTime.getReactionTime() + "ms").length() / 2, terminal.getTerminalSize().getRows() / 2, "twoj czas to:" + reactionTime.getReactionTime() + "ms", SGR.BOLD);
                        screen.refresh();
                    } else if (keyStroke1.getKeyType() == KeyType.Escape) {
                        break;
                    }
                }
                screen.refresh();
                keyStroke1 = screen.readInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }screen.clear();
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
            while (keyStroke1.getKeyType() != KeyType.Escape) {  //  keyStroke1.getKeyType() != KeyType.Tab
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
                     while (keyStroke1.getKeyType() != KeyType.Enter && keyStroke1.getKeyType() != KeyType.Escape){   //for (int i = 0; i < numberMemory.getLevel(); i++)
                        if (keyStroke1.getKeyType() == KeyType.Character) {
                            writing.appendText(keyStroke1.getCharacter());
                            textGraphics.putString(terminal.getTerminalSize().getColumns()/2-writing.getText().length()/2,terminal.getTerminalSize().getRows()/2+terminal.getTerminalSize().getRows()/4+1,writing.getText() , SGR.BOLD);
                        } else if (keyStroke1.getKeyType() == KeyType.Backspace && writing.getText().compareTo("") != 0) {
                            writing.cutLastChar();
                            textGraphics.putString(terminal.getTerminalSize().getColumns()/2-24,terminal.getTerminalSize().getRows()/2+terminal.getTerminalSize().getRows()/4+1,(" ").repeat(49) , SGR.BOLD);
                            textGraphics.putString(terminal.getTerminalSize().getColumns()/2-writing.getText().length()/2,terminal.getTerminalSize().getRows()/2+terminal.getTerminalSize().getRows()/4+1,writing.getText() , SGR.BOLD);

                        }
                        screen.refresh();
                        keyStroke1 = screen.readInput();

                     }
                     if (numberMemory.compareInput(writing.getText())) {
                         String completedLvMsg = "Poziom " + numberMemory.getLevel() + " ukończony!";
                         String startNextLvMsg = "Aby przejść do następnego poziomu kliknij ENTER";
                         screen.clear();
                         textGraphics.putString(terminal.getTerminalSize().getColumns()/2-completedLvMsg.length()/2,terminal.getTerminalSize().getRows()/2, completedLvMsg, SGR.BOLD);
                         textGraphics.putString(terminal.getTerminalSize().getColumns()/2-startNextLvMsg.length()/2,terminal.getTerminalSize().getRows()/2+3, startNextLvMsg, SGR.BOLD);
                         screen.refresh();
                         numberMemory.nextLevel();
                         keyStroke1 = screen.readInput();
                     }
                     else {
                         String summaryMsg = "Ukończono " + (numberMemory.getLevel()-1) + " poziomów";
                         String returnMsg =  "kliknij dowolny klawisz aby powrócić do menu głównego";
                         textGraphics.putString(terminal.getTerminalSize().getColumns()/2-summaryMsg.length()/2,terminal.getTerminalSize().getRows()/2, summaryMsg, SGR.BOLD);
                         textGraphics.putString(terminal.getTerminalSize().getColumns()/2-returnMsg.length()/2,terminal.getTerminalSize().getRows()/2+3, returnMsg, SGR.BOLD);
                         screen.refresh();
                         keyStroke1 = screen.readInput();
                         break;
                     }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        screen.clear();
    }

    public void measureVerbalMemory(Terminal terminal, Screen screen) {
        Boxes boxes = new Boxes();
        VerbalMemory verbalMemory = new VerbalMemory();
        int col, row, shift = -7, choice = 1;
        String[] buttonLabels = {
                "było",
                "nowe"
        };

        try {
            screen.clear();
            String testInformationMsg = "Na ekranie będą wyświetlane wyrazy. Jeżeli słowo już się pojawiło kliknij 'było' jeżeli to nowe słowo, kliknij 'nowe'";
            String startTest = "Aby rozpocząć kliknij ENTER";
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString((terminal.getTerminalSize().getColumns()-testInformationMsg.length())/2,terminal.getTerminalSize().getRows()/2,testInformationMsg,SGR.BOLD);
            textGraphics.putString((terminal.getTerminalSize().getColumns()-startTest.length())/2,terminal.getTerminalSize().getRows()/2+3,startTest,SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Enter) {
                String word;
                int result = 0;
                screen.clear();

                for (int i = 0 ; i< 2; i++) {
                    col = (terminal.getTerminalSize().getColumns()-buttonLabels.length)/2+shift;
                    row = (terminal.getTerminalSize().getRows())/2;
                    if (i == choice) {
                        boxes.drawBoldButton(buttonLabels[i],terminal, screen,col,row,buttonLabels[i].length()+3,3);
                    } else boxes.drawButton(buttonLabels[i],terminal, screen,col,row,buttonLabels[i].length()+3,3);
                        shift += 10;
                }
                word = verbalMemory.getNewWord();

                String livesAndPointsMsg = "Szanse | " + verbalMemory.getLives() + " Punkty | " + verbalMemory.getPoints();
                textGraphics.putString((terminal.getTerminalSize().getColumns()-livesAndPointsMsg.length())/2, terminal.getTerminalSize().getRows()/2-6,livesAndPointsMsg,SGR.BOLD);
                textGraphics.putString((terminal.getTerminalSize().getColumns()-word.length())/2, terminal.getTerminalSize().getRows()/2-2,word,SGR.BOLD);

                screen.refresh();

                while (true) {
                    keyStroke = screen.readInput();
                    if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                        break;
                    }else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowRight && choice < 1) {
                        choice++;
                    } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowLeft && choice > 0) {
                        choice--;
                    } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                        if (verbalMemory.isWordInSeen(word) == choice) {
                            verbalMemory.addPoints();
                            if (verbalMemory.isWordInSeen(word) == 1) {
                                verbalMemory.addSeen(word);
                            }
                            if (verbalMemory.getNewOrSeen()==0) {
                                word = verbalMemory.getNewWord();
                                result = 0;
                            } else {
                                word = verbalMemory.getSeenWord();
                                result = 1;
                            }
                            livesAndPointsMsg = "Szanse | " + verbalMemory.getLives() + " Punkty | " + verbalMemory.getPoints();
                            textGraphics.putString((terminal.getTerminalSize().getColumns()-livesAndPointsMsg.length())/2, terminal.getTerminalSize().getRows()/2-6,livesAndPointsMsg,SGR.BOLD);
                            textGraphics.putString((terminal.getTerminalSize().getColumns()-(" ").repeat(25).length())/2, terminal.getTerminalSize().getRows()/2-2,(" ").repeat(25),SGR.BOLD);
                            textGraphics.putString((terminal.getTerminalSize().getColumns()-word.length())/2, terminal.getTerminalSize().getRows()/2-2,word,SGR.BOLD);
                            screen.refresh();
                        } else {
                            verbalMemory.loseLive();
                            if (verbalMemory.isWordInSeen(word) == 1) {
                                verbalMemory.addSeen(word);
                            }
                            if (verbalMemory.getNewOrSeen()==0) {
                                word = verbalMemory.getNewWord();
                                result = 0;
                            } else {
                                word = verbalMemory.getSeenWord();
                                result = 1;
                            }
                            livesAndPointsMsg = "Szanse | " + verbalMemory.getLives() + " Punkty | " + verbalMemory.getPoints();
                            textGraphics.putString((terminal.getTerminalSize().getColumns()-livesAndPointsMsg.length())/2, terminal.getTerminalSize().getRows()/2-6,livesAndPointsMsg,SGR.BOLD);
                            textGraphics.putString((terminal.getTerminalSize().getColumns()-(" ").repeat(25).length())/2, terminal.getTerminalSize().getRows()/2-2,(" ").repeat(25),SGR.BOLD);textGraphics.putString((terminal.getTerminalSize().getColumns()-word.length())/2, terminal.getTerminalSize().getRows()/2-2,word,SGR.BOLD);
                            screen.refresh();
                            if (verbalMemory.getLives() == 0){
                                break;
                            }
                           }
                        }
                        shift = -7;
                        for (int i = 0 ; i< 2; i++) {
                            col = (terminal.getTerminalSize().getColumns()-buttonLabels.length)/2+shift;
                            row = (terminal.getTerminalSize().getRows())/2;
                            if (i == choice) {
                                boxes.drawBoldButton(buttonLabels[i],terminal, screen,col,row,buttonLabels[i].length()+3,3);
                            } else boxes.drawButton(buttonLabels[i],terminal, screen,col,row,buttonLabels[i].length()+3,3);
                            shift += 10;
                        }
                        screen.refresh();
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen.clear();
    }

    public void measureTyping(Terminal terminal, Screen screen) {
        Boxes boxes = new Boxes();
        Typing typing = new Typing();
        KeyStroke keyStroke1 = null;
        screen.clear();
        try {
            int spacing = 3;
            String startTestMsg = "Kliknij ENTER a następnie zacznij pisać aby rozpocząć test";
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - startTestMsg.length() / 2, 1, startTestMsg, SGR.BOLD);
            for (String line:typing.getText()) {
                textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - line.length() / 2, spacing, line, SGR.BOLD);
                spacing+=2;

            }
            screen.refresh();
            spacing = 3;
            keyStroke1 = screen.readInput();
            if (keyStroke1.getKeyType() == KeyType.Enter) {
                typing.setStart_time(System.nanoTime());
                for (String line:typing.getText()) {
                    int x = (terminal.getTerminalSize().getColumns() / 2 - line.length() / 2);
                    int y = spacing;
                    screen.setCursorPosition(new TerminalPosition(x,y));
                    screen.refresh();
                    int index = 0;
                    boolean endWriting = false;
                    while (index < line.length()) {    //    && !endWriting
                        keyStroke1 = screen.readInput();
                        if (keyStroke1.getKeyType() != KeyType.Escape && keyStroke1.getKeyType() == KeyType.Character && keyStroke1.getCharacter() == line.charAt(index)) {
                            textGraphics.setBackgroundColor(TextColor.ANSI.GREEN);
                            typing.incrementCorrect();
                        } else if (keyStroke1.getKeyType() == KeyType.Escape){
                            endWriting = true;
                            break;
                        } else  {
                            textGraphics.setBackgroundColor(TextColor.ANSI.RED);
                        }
                        textGraphics.putString(x,y, String.valueOf(line.charAt(index)));
                        x++;
                        index++;
                        screen.refresh();
                    }
                    spacing+=2;
                    if (endWriting) {
                        break;
                    }

                }

                typing.setEnd_time(System.nanoTime());
                float time = typing.getTypingTime();
                int words = typing.getTextLength();
                float accuracy = typing.getAccuracy();
                double result =  Math.floor((words / (time/60)) * accuracy);

                String resultMsg = "Twój wynik to: " + result + " WPM";
                textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
                textGraphics.putString(terminal.getTerminalSize().getColumns() / 2 - resultMsg.length() / 2, terminal.getTerminalSize().getRows()/2 + 10, resultMsg, SGR.BOLD);
                screen.refresh();

                keyStroke1 = screen.readInput();
                while (keyStroke1.getKeyType() != KeyType.Escape) {
                    keyStroke1 = screen.readInput();
                }
            }

//            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        screen.clear();
    }
}
