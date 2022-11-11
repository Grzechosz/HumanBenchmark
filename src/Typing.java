import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Typing {
    private ArrayList<String> text;
    private int index;
    public long start_time;
    public long end_time;

    public Typing() {
        this.index = 0;
        this.text = new ArrayList<>();
        File file = new File("paragraphs.txt");
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (i< 5) {
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

    public int getTypingTime() {
        return (int) ((end_time-start_time)/1000000/6000);
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

}
