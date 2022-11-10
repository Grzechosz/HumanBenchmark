import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Typing {
    private ArrayList<String> text;
    private int index;

    public Typing() {
        this.index = 0;
        this.text = new ArrayList<>();
        File file = new File("paragraphs.txt");
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (i< 7) {
                String nextLine = scanner.nextLine();
                text.add(nextLine);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void incrementIndex() {
        this.index++;
    }

    public void decrementIndex() {
        this.index--;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<String> getText() {
        return text;
    }
}
