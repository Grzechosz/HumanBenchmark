import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Typing {
    public Random rnd;
    private ArrayList<String> text;
    private int index;
    public long start_time;
    public long end_time;
    private int correct;
    private float accuracy;

    public Typing() {
        this.index = 0;
        this.correct = 0;
        this.accuracy = 1;
        this.text = new ArrayList<>();
        this.rnd = new Random();

        File file = new File("paragraphs.txt");
        try {
            Scanner scanner = new Scanner(file);
            int paragraph = rnd.nextInt(7);
            for (int i = 0; i<paragraph*10; i++) {
                scanner.nextLine();
            }

            int i = 0;
            while (i< 6) {
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

    public int getTextLength() {
        int numberOfCharacters = 0;
        for (String line: text) {
            numberOfCharacters+=line.length();
        }
        return numberOfCharacters/5;
    }

    public float getTypingTime() {
        return ((end_time-start_time)/1000000/1000);
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getCorrect() {
        return correct;
    }

    public void incrementCorrect() {
        this.correct++;
    }

    public float getAccuracy() {
        int numberOfCharacters = 0;
        for (String line: text) {
            numberOfCharacters+=line.length();
        }
        accuracy = (float) correct / (float) numberOfCharacters;
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
