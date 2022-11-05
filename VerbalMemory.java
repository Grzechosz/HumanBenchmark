import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class VerbalMemory {
    private String[] words;
    private Random rnd;

    public VerbalMemory() {
        this.rnd = new Random();
        this.words = new String[1000];
        File file = new File("words.txt");
        int index = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                words[index] = word;
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String[] getWords() {
        return words;
    }
}
