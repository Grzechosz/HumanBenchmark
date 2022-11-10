import java.util.Random;

public class NumberMemory {
    private long number;
    private int level;
    private Random rnd;

    public NumberMemory() {
        this.level =1;
        this.rnd = new Random();
    }

    public int getLevel() {
        return level;
    }

    public long drawNewNumber() {
        return number = rnd.nextLong(createMax()-createMin())+createMin();
    }

    public long createMax() {
        String max = "0";
        return Long.parseLong( 1+max.repeat(level));
    }

    public long createMin() {
        if (level == 1) {
            return 0;
        } else {
            String min = "0";
            return Long.parseLong(1+min.repeat(level-1));
        }
    }

    public boolean compareInput(String input) {
        int result =  String.valueOf(number).compareTo(input);
        if (result == 0)
            return true;
        else return false;
    }

    public void nextLevel() {
        this.level++;
    }
}
