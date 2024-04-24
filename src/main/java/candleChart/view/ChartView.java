package candleChart.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * This class represents a custom view for a chart.
 * You can display a cursor and a grid in the view.
 */
public class ChartView extends JPanel{

    private final Grid grid = new Grid();
    private final Cursor cursor = new Cursor();

    /**
     * Chart view builder. Initializes default values.
     */
    public ChartView() {
        setLayout(new OverlayLayout(this));
        setBorder(new LineBorder(Color.GRAY));
        setBackground(Color.BLACK);

        //TODO Implementacion de grid y cursor.
        add(cursor);
        add(grid);
    }

    /**
     * Sets the cursor position and updates the view.
     *
     * @param cursorX The horizontal position of the cursor.
     * @param cursorY The vertical position of the cursor.
     */
    public void setCursor(int cursorX, int cursorY) {
        cursor.setCursorPosition(cursorX, cursorY);
    }

    /**
     * Sets the cursor visibility and updates the view.
     *
     * @param visibility true to show the cursor, false to hide it.
     */
    public void setCursorVisible(boolean visibility) {
        cursor.setCursorVisible(visibility);
    }

    /**
     * Checks if the cursor is visible in the view.
     *
     * @return true if the cursor is visible, false to hide it.
     */
    public boolean isCursorVisible() {
        return cursor.isCursorVisible();
    }

    /**
     * Sets the grid size and updates the view.
     *
     * @param gridX The size of the grid on the X axis.
     * @param gridY The size of the grid on the Y axis.
     */
    public void setGridSize(double gridX, double gridY) {
        grid.setGridSize(gridX, gridY);
    }

    /**
     * Sets the grid visibility and updates the view.
     *
     * @param visibility true to show the grid, false to hide it.
     */
    public void setGridVisible(boolean visibility) {
        grid.setGridVisible(visibility);
    }

    /**
     * Checks if the grid is visible in the view.
     * @return true if the grid is visible, false otherwise.
     */
    public boolean isGridVisible() {
        return grid.isGridVisible();
    }
}
