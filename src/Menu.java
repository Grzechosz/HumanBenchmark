import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


public class Menu {
    public static Font font = new Font("Courier New", Font.BOLD, 15);
    public static final int frameWidth = 100;    //120  50
    public static final int frameHeight = 40;    //50  50
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

    public static void drawMenu( Screen screen, String[] labels, Boxes boxes, int width, int heigth,  int choice) {
        int shift = -3;
        for (int i = 0 ; i< 5; i++) {
                int col = (screen.getTerminalSize().getColumns()-width)/2;
                int row = (screen.getTerminalSize().getRows())/2+shift;
                if (i == choice) {
                    boxes.drawBoldButton(labels[i], screen,col,row,width,heigth);
                } else boxes.drawButton(labels[i], screen,col,row,width,heigth);
                shift += 4;

        }
    }

    public static void main(String[] args) {

        SwingTerminalFontConfiguration cfg = SwingTerminalFontConfiguration.newInstance(font);
        DefaultTerminalFactory terminal = new DefaultTerminalFactory(System.out, System.in, StandardCharsets.UTF_8)
                .setTerminalEmulatorFontConfiguration(cfg)
                .setInitialTerminalSize(new TerminalSize(Menu.frameWidth, Menu.frameHeight))
                .setTerminalEmulatorTitle("Human Benchmark");
//        TerminalSize terminalSize = new TerminalSize(120,120);

        int choice = 0;
        String[] labels = {
                "Czas reakcji",
                "Pamięć numerów",
                "Pamięć wyrazów",
                "Pisanie",
                "Statystyki"
        };
        Measures measures = new Measures();
        Boxes boxes = new Boxes();
        ArrayList<String> title = readTitle();
        Statistics statistics = new Statistics();
        Screen screen = null;
        int width = 20, heigth = 3;
        try {
            screen = terminal.createScreen();
            screen.startScreen();
            screen.setCursorPosition(null);
            boxes.drawTitle(title, screen);
            drawMenu(screen,labels,boxes,width,heigth,choice);
            screen.refresh();
            while(true) {
                screen.doResizeIfNecessary();
                screen.clear();
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                    break;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowDown && choice < 4) {
                    choice++;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowUp && choice > 0) {
                    choice--;
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                    switch (choice) {
                        case 0 :
                            measures.measureReactionTime(screen, statistics);
                            break;
                        case 1 :
                            measures.measureNumberMemory(screen, statistics);
                            break;
                        case 2 :
                            measures.measureVerbalMemory(screen, statistics);
                            break;
                        case 3:
                            measures.measureTyping( screen, statistics);
                            screen.setCursorPosition(null);
                            break;
                        case 4:
                            measures.displayStatistics( screen, statistics);
                            break;
                    }
                }
                boxes.drawTitle(title, screen);
                drawMenu(screen,labels,boxes,width,heigth,choice);
                screen.refresh();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if(screen != null) {
                try {
                    screen.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
