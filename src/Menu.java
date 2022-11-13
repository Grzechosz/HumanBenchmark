import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.Font;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public static Font font = new Font("Monospaced", Font.BOLD, 18);
    public static final int frameWidth = 100;    //120  50
    public static final int frameHeight = 28;    //50  50
    public static ArrayList<String> readTitle() {
        ArrayList<String> title = new ArrayList<>();
        File file = new File("title.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                title.add(nextLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return title;
    }

    public static void drawMenu( Screen screen, String[] labels, Boxes boxes, int width, int heigth,  int choice) {
        int shift = -7;
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

        int choice = 0;
        String[] labels = {
                "Czas reakcji",
                "Pamięć numerów",
                "Pamięć wyrazów",
                "Pisanie",
                "Wyniki/ustawienia"
        };
        Measures measures = new Measures();
        Boxes boxes = new Boxes();
        ArrayList<String> title = readTitle();
        Statistics statistics = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"))) {
            statistics = (Statistics) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            statistics = new Statistics();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Screen screen = null;
        int width = 20, heigth = 3;
        try {
            screen = terminal.createScreen();
            screen.startScreen();
            screen.setCursorPosition(null);
            int spacing = -5;
            while (spacing <= 1) {
                screen.clear();
                boxes.drawTitle(title, screen, spacing);
                screen.refresh();
                Thread.sleep(600);
                spacing++;
            }
            drawMenu(screen,labels,boxes,width,heigth,choice);
            screen.refresh();
            KeyStroke keyStroke = screen.readInput();
            while(true) {
                screen.clear();

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
                            measures.measureVerbalMemory(screen, statistics, boxes);
                            break;
                        case 3:
                            measures.measureTyping( screen, statistics);
                            screen.setCursorPosition(null);
                            break;
                        case 4:
                            TextColor color = measures.displayStatistics( screen, statistics, boxes);
                            boxes.setColor(color);
                            break;
                    }
                }
                boxes.drawTitle(title, screen, 2);
                drawMenu(screen,labels,boxes,width,heigth,choice);
                screen.refresh();
                keyStroke = screen.readInput();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"))) {
            outputStream.writeObject(statistics);
        } catch (IOException e) {
            e.printStackTrace();
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
