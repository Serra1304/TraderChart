package candleChart.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * This class represents a custom view for a chart.
 * You can display a cursor and a grid in the view.
 */
public class ChartView extends JPanel{
    private boolean cursorVisible;
    private int cursorX, cursorY;

    private boolean gridVisible;
    private double gridXSize, gridYSize;

    /**
     * Chart view builder. Initializes default values.
     */
    public ChartView() {
        cursorVisible = false;
        cursorX = 0;
        cursorY = 0;

        gridVisible = true;
        gridXSize = 0;
        gridYSize = 0;

        setLayout(new OverlayLayout(this));
        setBorder(new LineBorder(Color.GRAY));
        setBackground(Color.BLACK);
    }

    /**
     * Paints the chart view with the cursor and grid, if enabled.
     *
     * @param g The graphical context in which the view is painted.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Paint the cursor if necessary.
        if (cursorVisible) {
            g.setColor(Color.GRAY);
            g.drawLine(cursorX, 0, cursorX, getHeight());
            g.drawLine(0, cursorY, getWidth(), cursorY);
        }

        // Paint the grid if necessary.
        if(gridVisible) {
            // Set the dashed stroke.
            float[] dashPattern = {5.0f, 4.0f};
            BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0.0f, dashPattern, 0.0f);
            g2d.setStroke(dashedStroke);
            g2d.setColor(Color.DARK_GRAY);

            // Vertical stroke paint.
            for (int x = 0; x < getWidth(); x += (int) gridXSize) {
                g2d.draw(new Line2D.Double(x, 0, x, getHeight()));
            }

            // Horizontal stroke paint.
            for (int i = 0; i < getHeight()/ gridXSize; i++) {
                int y = (int) Math.floor(i * gridYSize);
                g2d.draw(new Line2D.Double(0, y, getWidth(), y));
            }

            g2d.setStroke(new BasicStroke());
        }
    }

    /**
     * Sets the cursor position and updates the view.
     *
     * @param cursorX The horizontal position of the cursor.
     * @param cursorY The vertical position of the cursor.
     */
    public void setCursor(int cursorX, int cursorY) {
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        repaint();
    }

    /**
     * Gets the X axis cursor.
     *
     * @return The X axis cursor.
     */
    public int getCursorX() {
        return cursorX;
    }

    /**
     * Gets the Y axis cursor.
     *
     * @return The Y axis cursor.
     */
    public int getCursorY() {
        return cursorY;
    }

    /**
     * Sets the cursor visibility and updates the view.
     *
     * @param visibility true to show the cursor, false to hide it.
     */
    public void setCursorVisible(boolean visibility) {
        cursorVisible = visibility;
        repaint();
    }

    /**
     * Checks if the cursor is visible in the view.
     *
     * @return true if the cursor is visible, false to hide it.
     */
    public boolean isCursorVisible() {
        return cursorVisible;
    }

    /**
     * Sets the grid size and updates the view.
     *
     * @param gridX The size of the grid on the X axis.
     * @param gridY The size of the grid on the Y axis.
     */
    public void setGridSize(double gridX, double gridY) {
        gridXSize = gridX;
        gridYSize = gridY;
        repaint();
    }

    /**
     * Gets the X axis grid size.
     *
     * @return The x asis grid size.
     */
    public double getGridXSize() {
        return gridXSize;
    }

    /**
     * Gets the Y axis grid size.
     *
     * @return The Y axis grid size.
     */
    public double getGridYSize() {
        return gridYSize;
    }

    /**
     * Sets the grid visibility and updates the view.
     *
     * @param visibility true to show the grid, false to hide it.
     */
    public void setGridVisible(boolean visibility) {
        gridVisible = visibility;
        repaint();
    }

    /**
     * Checks if the grid is visible in the view.

     * @return true if the grid is visible, false otherwise.
     */
    public boolean isGridVisible() {
        return gridVisible;
    }
}
