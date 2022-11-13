import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;

public class Boxes {
    private TextColor color;

    public Boxes() {
        this.color = TextColor.ANSI.MAGENTA;
    }

    public void setColor(TextColor color) {
        this.color = color;
    }

    public void drawBox(String label, Screen screen, int shift)  {  // DefaultTerminalFactory terminal,

        TextGraphics textGraphics;
        TerminalSize labelBoxSize = new TerminalSize(label.length() + 2, 3);
        TerminalPosition labelBoxTopLeft = new TerminalPosition(screen.getTerminalSize().getColumns()/2-label.length()/2, screen.getTerminalSize().getRows()/2+shift);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        textGraphics = screen.newTextGraphics();
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.SINGLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.SINGLE_LINE_HORIZONTAL);

        textGraphics.setCharacter(labelBoxTopLeft, Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1), Symbols.SINGLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2), Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1), Symbols.SINGLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2), Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), label);
    }

    public void drawButton(String label, Screen screen, int col, int row, int width, int height) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize labelBoxSize = new TerminalSize(width,height);
        TerminalPosition leftTop;
        leftTop = new TerminalPosition(col,row);
        textGraphics.drawRectangle(leftTop,labelBoxSize,TextCharacter.DEFAULT_CHARACTER.withBackgroundColor(TextColor.ANSI.WHITE));
        textGraphics.putString(leftTop.withRelative((width - label.length())/2, 1), label);
    }

    public void drawBoldButton(String label, Screen screen, int col, int row, int width, int height) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize labelBoxSize = new TerminalSize(width,height);
        TerminalPosition leftTop;
        leftTop = new TerminalPosition(col,row);
        textGraphics.drawRectangle(leftTop,labelBoxSize,TextCharacter.DEFAULT_CHARACTER.withBackgroundColor(color));
        textGraphics.putString(leftTop.withRelative((width - label.length())/2, 1), label);
    }

    public void drawTitle(ArrayList<String> title, Screen screen, int spacing) {
        TextGraphics textGraphics = screen.newTextGraphics();
        for (String line:title) {
            textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - line.length() / 2, spacing, line, SGR.BOLD);
            spacing++;
        }
    }
}
