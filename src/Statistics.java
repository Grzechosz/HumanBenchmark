import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Statistics {
    private final ArrayList<Integer> typingResults;
    private final ArrayList<Integer> reactionTimeResults;
    private final ArrayList<Integer> numberMemoryResults;
    private final ArrayList<Integer> verbalMemoryResults;

    public Statistics() {
        this.typingResults = new ArrayList<Integer>();
        this.reactionTimeResults = new ArrayList<Integer>();
        this.numberMemoryResults = new ArrayList<Integer>();
        this.verbalMemoryResults = new ArrayList<Integer>();
    }

    public void addReactionTimeResult(int result) {
        if (this.reactionTimeResults.size() < 5) {
            this.reactionTimeResults.add(result);
        } else {
            Collections.sort(reactionTimeResults);
            if (reactionTimeResults.get(4)> result) {
                reactionTimeResults.set(4, result);
            }
        }
    }

    public void addTypingResult(int result) {
        if (this.typingResults.size() < 5) {
            this.typingResults.add(result);
        } else {
            Collections.sort(typingResults);
            Collections.reverse(typingResults);
            if (typingResults.get(0) < result) {
                typingResults.set(0, result);
            }
        }
    }

    public void addNumberMemoryResult(int result) {
        if (this.numberMemoryResults.size() < 5) {
            this.numberMemoryResults.add(result);
        } else {
            Collections.sort(numberMemoryResults);
            Collections.reverse(numberMemoryResults);
            if (numberMemoryResults.get(0) < result) {
                numberMemoryResults.set(0, result);
            }
        }
    }

    public void addVerbalMemoryResult(int result) {
        if (this.verbalMemoryResults.size() < 5) {
            this.verbalMemoryResults.add(result);
        } else {
            Collections.sort(verbalMemoryResults);
            Collections.reverse(verbalMemoryResults);
            if (verbalMemoryResults.get(0) < result) {
                verbalMemoryResults.set(0, result);
            }
        }
    }

    public ArrayList<Integer> getTypingResults() {
        return typingResults;
    }

    public ArrayList<Integer> getReactionTimeResults() {
        return reactionTimeResults;
    }

    public ArrayList<Integer> getNumberMemoryResults() {
        return numberMemoryResults;
    }

    public ArrayList<Integer> getVerbalMemoryResults() {
        return verbalMemoryResults;
    }
}
