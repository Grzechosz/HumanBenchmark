import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Boxes {
    public void drawBox(String label,  Screen screen, int shift) throws IOException {  // DefaultTerminalFactory terminal,

        TextGraphics textGraphics;
//        String sizeLabel = label;
        TerminalSize labelBoxSize = new TerminalSize(label.length() + 2, 3);
        TerminalPosition labelBoxTopLeft = new TerminalPosition(screen.getTerminalSize().getColumns()/2-label.length()/2, screen.getTerminalSize().getRows()/2+shift);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        textGraphics = screen.newTextGraphics();
        //This isn't really needed as we are overwriting everything below anyway, but just for demonstrative purpose
        // textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

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

    public void drawBoldBox(String label, Screen screen, int shift) throws IOException {  // Terminal terminal,

        TextGraphics textGraphics;
        TerminalSize labelBoxSize = new TerminalSize(label.length() + 2, 3);
        TerminalPosition labelBoxTopLeft = new TerminalPosition(screen.getTerminalSize().getColumns()/2- label.length()/2, screen.getTerminalSize().getRows()/2+shift);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        textGraphics = screen.newTextGraphics();
        //This isn't really needed as we are overwriting everything below anyway, but just for demonstrative purpose
        // textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);

        textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), label);
    }

    public void drawButton(String label, Screen screen, int col, int row, int width, int height) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize labelBoxSize = new TerminalSize(width,height);
        TerminalPosition leftTop = null;
        leftTop = new TerminalPosition(col,row);
        textGraphics.drawRectangle(leftTop,labelBoxSize,TextCharacter.DEFAULT_CHARACTER.withBackgroundColor(TextColor.ANSI.WHITE));
        textGraphics.putString(leftTop.withRelative((width - label.length())/2, 1), label);
    }

    public void drawBoldButton(String label, Screen screen, int col, int row, int width, int height) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize labelBoxSize = new TerminalSize(width,height);
        TerminalPosition leftTop = null;
        leftTop = new TerminalPosition(col,row);
        textGraphics.drawRectangle(leftTop,labelBoxSize,TextCharacter.DEFAULT_CHARACTER.withBackgroundColor(TextColor.ANSI.MAGENTA_BRIGHT));
        textGraphics.putString(leftTop.withRelative((width - label.length())/2, 1), label);
    }

    public void drawTitle(ArrayList<String> title, Screen screen) { // Terminal terminal
        TextGraphics textGraphics = screen.newTextGraphics();
        int spacing = 2;
        for (String line:title) {
            textGraphics.putString(screen.getTerminalSize().getColumns() / 2 - line.length() / 2, spacing, line, SGR.BOLD);
            spacing++;
        }
    }
}
