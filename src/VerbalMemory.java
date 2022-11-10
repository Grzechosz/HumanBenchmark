import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VerbalMemory {
    private ArrayList<String> words;
    private Random rnd;
    private ArrayList<String> seen;
    private int points;
    private int lives;

    public VerbalMemory() {
        this.rnd = new Random();
        this.words = new ArrayList<String>();
        this.lives = 3;
        this.points = 0;
        this.seen = new ArrayList<String>();
        File file = new File("words.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                words.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getNewOrSeen() {
        return rnd.nextInt(2);
    }

    public String getNewWord() {
        int index = rnd.nextInt(words.size());
        String word = words.get(index);
        words.remove(index);
        return word;
    }

    public String getSeenWord() {
        int index = rnd.nextInt(seen.size());
        String word = seen.get(index);
//        seen.remove(index);
        return word;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void  addSeen(String word) {
        seen.add(word);
    }

    public int isWordInSeen(String word) {
        if (seen.contains(word)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoints() {
        this.points++;
    }

    public int getLives() {
        return lives;
    }

    public void loseLive() {
        this.lives--;
    }
}
