import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class SortResults implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 -o2;
    }
}

class ReverseSortResults implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o2 -o1;
    }
}


public class Measures {

    public void measureReactionTime( Screen screen, Statistics statistics) {
        screen.clear();
        try {
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            String information = "Kliknij enter a następnie czekaj aż kolor zmieni się na zielony";
            String shortInformation = "Czekaj aż kolor zmieni się na zielony";
            textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - information.length()/2, screen.getTerminalSize().getRows()/2, information, SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke1 = screen.readInput();
            while (keyStroke1.getKeyType() != KeyType.Escape) {
                if (keyStroke1.getKeyType() == KeyType.Enter) {
                    textGraphics.setBackgroundColor(TextColor.ANSI.RED);
                    textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                    textGraphics.fill(' ');
                    textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - shortInformation.length()/2, screen.getTerminalSize().getRows()/2, shortInformation, SGR.BOLD);

                    screen.refresh();
                    ReactionTime reactionTime = new ReactionTime();
                    reactionTime.measureReactionTime();
                    reactionTime.setStart_time(System.nanoTime());
                    textGraphics.setBackgroundColor(TextColor.ANSI.GREEN);
                    textGraphics.fill(' ');
                    while (screen.pollInput() != null) {

                    }
                    screen.refresh();
                    keyStroke1 = screen.readInput();

                    if (keyStroke1.getKeyType() == KeyType.Escape) {
                        break;
                    }

                    if (keyStroke1.getKeyType() == KeyType.Enter) {
                        reactionTime.setEnd_time(System.nanoTime());
                        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                        textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - ("twoj czas to:" + reactionTime.getReactionTime() + "ms").length() / 2, screen.getTerminalSize().getRows() / 2, "twoj czas to:" + reactionTime.getReactionTime() + "ms", SGR.BOLD);
                        statistics.addReactionTimeResult(reactionTime.getReactionTime());
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

    public void measureNumberMemory( Screen screen, Statistics statistics) {
        Boxes boxes = new Boxes();
        screen.clear();
        NumberMemory numberMemory = new NumberMemory();
        try {
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(screen.getTerminalSize().getColumns() / 2- ("Kliknij enter aby rozpocząć").length()/2, screen.getTerminalSize().getRows()/2, "Kliknij enter aby rozpocząć", SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke1 = screen.readInput();
            while (keyStroke1.getKeyType() == KeyType.Enter && keyStroke1.getKeyType() != KeyType.Escape) {  //  keyStroke1.getKeyType() != KeyType.Tab
//                if (keyStroke1.getKeyType() == KeyType.Enter) {
                    screen.clear();
                    long number = numberMemory.drawNewNumber();
                    int columnStart = screen.getTerminalSize().getColumns()/2-10;
                    int columnEnd = screen.getTerminalSize().getColumns()/2+10;
                    int row = screen.getTerminalSize().getRows()/2+10;
                    TerminalPosition progressBarStart = new TerminalPosition(columnStart,row);
                    TerminalPosition progressBarEnd = new TerminalPosition(columnEnd, row);
                    textGraphics.putString(screen.getTerminalSize().getColumns() / 2- Long.toString(number).length()/2, screen.getTerminalSize().getRows() / 2,Long.toString(number) , SGR.BOLD);
                    screen.refresh();
                    textGraphics.setForegroundColor(TextColor.ANSI.MAGENTA);
                    textGraphics.drawLine(progressBarStart,progressBarEnd,Symbols.BLOCK_SOLID);
                    screen.refresh();
                    textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                    for (int i = columnEnd; i >= columnStart; i--) {
                        textGraphics.putString(i,row,String.valueOf(Symbols.BLOCK_SOLID));
                        long time = 1000L;
                        Thread.sleep(time *numberMemory.getLevel()/20);
                        screen.refresh();
                    }
                    textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                    screen.clear();

                    boxes.drawBox((" ").repeat(50), screen, screen.getTerminalSize().getRows()/4);
                    String hint = "Wpisz zapamiętany numer";
                    textGraphics.putString( screen.getTerminalSize().getColumns()/2-hint.length()/2,screen.getTerminalSize().getRows()/2+screen.getTerminalSize().getRows()/4+1,hint , SGR.BOLD);
                    screen.refresh();
                    Writing writing = new Writing();
                    while (screen.pollInput() != null) {

                    }
                    keyStroke1 = screen.readInput();
                    textGraphics.putString( screen.getTerminalSize().getColumns()/2-(" ").repeat(40).length()/2,screen.getTerminalSize().getRows()/2+screen.getTerminalSize().getRows()/4+1,(" ").repeat(40), SGR.BOLD);
                     while (keyStroke1.getKeyType() != KeyType.Enter && keyStroke1.getKeyType() != KeyType.Escape){   //for (int i = 0; i < numberMemory.getLevel(); i++)
                        if (keyStroke1.getKeyType() == KeyType.Character) {
                            writing.appendText(keyStroke1.getCharacter());
                            textGraphics.putString(screen.getTerminalSize().getColumns()/2-writing.getText().length()/2,screen.getTerminalSize().getRows()/2+screen.getTerminalSize().getRows()/4+1,writing.getText() , SGR.BOLD);
                        } else if (keyStroke1.getKeyType() == KeyType.Backspace && writing.getText().compareTo("") != 0) {
                            writing.cutLastChar();
                            textGraphics.putString(screen.getTerminalSize().getColumns()/2-24,screen.getTerminalSize().getRows()/2+screen.getTerminalSize().getRows()/4+1,(" ").repeat(49) , SGR.BOLD);
                            textGraphics.putString(screen.getTerminalSize().getColumns()/2-writing.getText().length()/2,screen.getTerminalSize().getRows()/2+screen.getTerminalSize().getRows()/4+1,writing.getText() , SGR.BOLD);

                        }
                        screen.refresh();
                        keyStroke1 = screen.readInput();

                     }
                     if (numberMemory.compareInput(writing.getText())) {
                         String completedLvMsg = "Poziom " + numberMemory.getLevel() + " ukończony!";
                         String startNextLvMsg = "Aby przejść do następnego poziomu kliknij ENTER";
                         screen.clear();
                         textGraphics.putString(screen.getTerminalSize().getColumns()/2-completedLvMsg.length()/2,screen.getTerminalSize().getRows()/2, completedLvMsg, SGR.BOLD);
                         textGraphics.putString(screen.getTerminalSize().getColumns()/2-startNextLvMsg.length()/2,screen.getTerminalSize().getRows()/2+3, startNextLvMsg, SGR.BOLD);
                         screen.refresh();
//                         statistics.addNumberMemoryResult(numberMemory.getLevel());
                         numberMemory.nextLevel();
                         keyStroke1 = screen.readInput();
                     }
                     else {
                         String summaryMsg = "Ukończono " + (numberMemory.getLevel()-1) + " poziomów";
                         String returnMsg =  "kliknij dowolny klawisz aby powrócić do menu głównego";
                         textGraphics.putString(screen.getTerminalSize().getColumns()/2-summaryMsg.length()/2,screen.getTerminalSize().getRows()/2, summaryMsg, SGR.BOLD);
                         textGraphics.putString(screen.getTerminalSize().getColumns()/2-returnMsg.length()/2,screen.getTerminalSize().getRows()/2+3, returnMsg, SGR.BOLD);
                         screen.refresh();
                         statistics.addNumberMemoryResult(numberMemory.getLevel()-1);
                         keyStroke1 = screen.readInput();
                         break;
                     }
//                }
//                keyStroke1 = screen.readInput();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        screen.clear();
    }

    public void measureVerbalMemory( Screen screen, Statistics statistics) {
        Boxes boxes = new Boxes();
        VerbalMemory verbalMemory = new VerbalMemory();
        int col, row, shift = -7, choice = 1;
        String[] buttonLabels = {
                " Było",
                " Nowe"
        };

        try {
            screen.clear();
            String testInformationMsg1 = "Na ekranie będą wyświetlane wyrazy.";
            String testInformationMsg2 =  "Jeżeli słowo już się pojawiło kliknij 'było' jeżeli to nowe słowo, kliknij 'nowe'";
            String startTest = "Aby rozpocząć kliknij ENTER";
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString((screen.getTerminalSize().getColumns()-testInformationMsg1.length())/2,screen.getTerminalSize().getRows()/2-3,testInformationMsg1,SGR.BOLD);
            textGraphics.putString((screen.getTerminalSize().getColumns()-testInformationMsg2.length())/2,screen.getTerminalSize().getRows()/2,testInformationMsg2,SGR.BOLD);
            textGraphics.putString((screen.getTerminalSize().getColumns()-startTest.length())/2,screen.getTerminalSize().getRows()/2+3,startTest,SGR.BOLD);
            screen.refresh();
            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Enter) {
                String word;
                screen.clear();

                for (int i = 0 ; i< 2; i++) {
                    col = (screen.getTerminalSize().getColumns()-buttonLabels.length)/2+shift;
                    row = (screen.getTerminalSize().getRows())/2;
                    if (i == choice) {
                        boxes.drawBoldButton(buttonLabels[i], screen,col,row,buttonLabels[i].length()+3,3);
                    } else boxes.drawButton(buttonLabels[i], screen,col,row,buttonLabels[i].length()+3,3);
                        shift += 10;
                }
                word = verbalMemory.getNewWord();

                String livesAndPointsMsg = "Szanse | " + verbalMemory.getLives() + "  Punkty | " + verbalMemory.getPoints();
                textGraphics.putString((screen.getTerminalSize().getColumns()-livesAndPointsMsg.length())/2, screen.getTerminalSize().getRows()/2-6,livesAndPointsMsg,SGR.BOLD);
                textGraphics.putString((screen.getTerminalSize().getColumns()-word.length())/2, screen.getTerminalSize().getRows()/2-2,word,SGR.BOLD);

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
                            } else {
                                word = verbalMemory.getSeenWord();

                            }
                        }
                        else {
                            verbalMemory.loseLive();
                            if (verbalMemory.isWordInSeen(word) == 1) {
                                verbalMemory.addSeen(word);
                            }
                            if (verbalMemory.getNewOrSeen()==0) {
                                word = verbalMemory.getNewWord();
                            } else {
                                word = verbalMemory.getSeenWord();
                            }
                            if (verbalMemory.getLives() == 0){
                                break;
                            }
                        }

                        int columnStart = (screen.getTerminalSize().getColumns() - word.length())/2;
                        int columnEnd = (screen.getTerminalSize().getColumns() + word.length())/2;
                        int rowv = screen.getTerminalSize().getRows()/2-2;
                        TerminalPosition progressBarStart = new TerminalPosition(columnStart,rowv);
                        TerminalPosition progressBarEnd = new TerminalPosition(columnEnd, rowv);
                        textGraphics.drawLine(progressBarStart,progressBarEnd,Symbols.BLOCK_SOLID);
                        screen.refresh();
                        Thread.sleep(200);
                    }

                        livesAndPointsMsg = "Szanse | " + verbalMemory.getLives() + "  Punkty | " + verbalMemory.getPoints();

                        textGraphics.putString((screen.getTerminalSize().getColumns()-livesAndPointsMsg.length())/2, screen.getTerminalSize().getRows()/2-6,livesAndPointsMsg,SGR.BOLD);
                        textGraphics.putString((screen.getTerminalSize().getColumns()-(" ").repeat(25).length())/2, screen.getTerminalSize().getRows()/2-2,(" ").repeat(25),SGR.BOLD);
                        textGraphics.putString((screen.getTerminalSize().getColumns()-word.length())/2, screen.getTerminalSize().getRows()/2-2,word,SGR.BOLD);
                        screen.refresh();


                        shift = -7;
                        for (int i = 0 ; i< 2; i++) {
                            col = (screen.getTerminalSize().getColumns()-buttonLabels.length)/2+shift;
                            row = (screen.getTerminalSize().getRows())/2;
                            if (i == choice) {
                                boxes.drawBoldButton(buttonLabels[i], screen,col,row,buttonLabels[i].length()+3,3);
                            } else boxes.drawButton(buttonLabels[i], screen,col,row,buttonLabels[i].length()+3,3);
                            shift += 10;
                        }
                        screen.refresh();
                    }
                screen.clear();
                String resultMsg = "Twój wynik to: " + verbalMemory.getPoints() + "pkt.";
                String quit = "Kliknij ESC aby wrócić do menu głównego";
                textGraphics.putString((screen.getTerminalSize().getColumns()-resultMsg.length())/2, screen.getTerminalSize().getRows()/2-6,resultMsg,SGR.BOLD);
                textGraphics.putString((screen.getTerminalSize().getColumns()-quit.length())/2, screen.getTerminalSize().getRows()/2-2,quit,SGR.BOLD);
                statistics.addVerbalMemoryResult(verbalMemory.getPoints());
                screen.refresh();
                keyStroke = screen.readInput();
                while (keyStroke.getKeyType() != KeyType.Escape){
                    keyStroke = screen.readInput();
                }
                }
        } catch (IOException | InterruptedException e ) {
            e.printStackTrace();
        }
        screen.clear();
    }

    public void measureTyping( Screen screen, Statistics statistics) {
        Typing typing = new Typing();
        KeyStroke keyStroke1;
        screen.clear();
        try {
            int spacing = 3;
            String startTestMsg = "Kliknij ENTER a następnie zacznij pisać aby rozpocząć test";
            screen.setCursorPosition(null);
            final TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - startTestMsg.length() / 2, 1, startTestMsg, SGR.BOLD);
            for (String line:typing.getText()) {
                textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - line.length() / 2, spacing, line, SGR.BOLD);
                spacing+=2;

            }
            screen.refresh();
            spacing = 3;
            keyStroke1 = screen.readInput();
            if (keyStroke1.getKeyType() == KeyType.Enter) {
                typing.setStart_time(System.nanoTime());
                for (String line:typing.getText()) {
                    int x = (screen.getTerminalSize().getColumns() / 2 - line.length() / 2);
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
                textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - resultMsg.length() / 2, screen.getTerminalSize().getRows()/2 + 10, resultMsg, SGR.BOLD);
                statistics.addTypingResult((int)result);
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

    public void displayActivityStats( Screen screen, String label, ArrayList<Integer> results, int x_col, int y_row, boolean reverse) {
        TextGraphics textGraphics = screen.newTextGraphics();
        int shift = 1;
        int position = 1;
        try {
            textGraphics.putString(((screen.getTerminalSize().getColumns() - label.length())/ 4) * x_col,
                    screen.getTerminalSize().getRows()/8 * y_row, label, SGR.BOLD);
            if (reverse) {
                Collections.sort(results, new ReverseSortResults());
            } else {
                Collections.sort(results, new SortResults());
            }
            for (int result: results) {

                textGraphics.putString((screen.getTerminalSize().getColumns()/ 4 )* x_col,
                        screen.getTerminalSize().getRows()/8 * y_row + shift, position + ". " + result , SGR.BOLD);
                screen.refresh();
                shift+=2;
                position++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayStatistics( Screen screen, Statistics statistics) {
        displayActivityStats(screen,"Czas Reakcji [ms]", statistics.getReactionTimeResults(), 1,1, false);
        displayActivityStats(screen,"Pamięć numerów [ilość cyfr]", statistics.getNumberMemoryResults(), 3,1, true);
        displayActivityStats(screen,"Pamięć wyrazów [liczba rund]", statistics.getVerbalMemoryResults(), 1,5, true);
        displayActivityStats(screen,"Pisanie [WPM]", statistics.getTypingResults(), 3,5, true);

        try {
            screen.refresh();
            screen.readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen.clear();
    }
}
