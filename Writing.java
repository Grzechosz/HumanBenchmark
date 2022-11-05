public class Writing {
    private String text;

    public Writing() {
        this.text = "";
    }

    public void appendText(char key) {
        text = text + key;
    }

    public void cutLastChar() {
        text = text.substring(0,text.length()-1);
    }

    public String getText() {
        return text;
    }
}
