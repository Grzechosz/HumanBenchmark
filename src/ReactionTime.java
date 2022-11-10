import java.util.Random;

public class ReactionTime {
    public long start_time;
    public long end_time;
    public int sleepSeed;
    public Random rnd;

    public ReactionTime() {
        this.rnd = new Random();
    }

    public void measureReactionTime() {
        sleepSeed = rnd.nextInt(10-1)+1;
        try {
            Thread.sleep(500*sleepSeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getReactionTime() {
        return (int) ((end_time-start_time)/1000000);
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }
}