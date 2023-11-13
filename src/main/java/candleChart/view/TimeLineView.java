package candleChart.view;

import candleChart.model.Candle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a timeline view for candlestick charts.
 */
public class TimeLineView extends JPanel {
    private static final int LABEL_WIDTH = 60;
    private static final int LABEL_HEIGHT = 20;
    private static final int DIVIDER_HEIGHT = 6;

    private int chartWidth;
    private int relativePosition;
    private double sizeGridX;

    private final JLabel currentTime;
    private List<Candle> candleList;

    /**
     * Constructor of the TimeLineView class.
     * Initializes the components and sets the appearance of the view.
     */
    public TimeLineView() {
        chartWidth = 0;
        sizeGridX = 0;
        relativePosition = 1;
        candleList = new ArrayList<>();

        currentTime = new JLabel();
        currentTime.setVisible(false);
        currentTime.setOpaque(true);
        currentTime.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        currentTime.setHorizontalAlignment(SwingConstants.CENTER);
        currentTime.setBackground(Color.GRAY);
        currentTime.setForeground(Color.BLACK);

        setLayout(null);
        setBackground(Color.BLACK);
        add(currentTime);
    }

    /**
     * {@inheritDoc}
     * Overrides the paintComponent method to customize the appearance of the timeline view.
     * This paintComponent paints the timeline divisions and the time corresponding to your candle if possible
     *
     * @param g The graphics context for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        for (int x = 0; x < chartWidth; x += (int) sizeGridX) {
            g.drawLine(x, 0, x, DIVIDER_HEIGHT);

            if(candleList != null) {
                int candleIndex = candlePositionOfPixel(x);
                if (x / sizeGridX % 2 == 0) {
                    if (candleIndex < candleList.size()) {
                        String time = candleList.get(candleIndex).getTime().toString();
                        int positionX = x - g.getFontMetrics().stringWidth(time) / 2;
                        g.drawString(time, positionX, LABEL_HEIGHT);
                    }
                }
            }
        }
    }

    /**
     * Set the width of the timeline.
     *
     * @param chartWidth The width of the timeline.
     */
    public void setChartWidth(int chartWidth){
        this.chartWidth = chartWidth;
        repaint();
    }

    /**
     * Gets the width of the timeline.
     *
     * @return The width of the timeline.
     */
    public int getChartWidth() {
        return chartWidth;
    }

    /**
     * Sets the width of the grid.
     *
     * @param sizeGridX The width of each section of the timeline.
     */
    public void setSizeGridX(double sizeGridX) {
        this.sizeGridX = sizeGridX;
        repaint();
    }

    /**
     * Gets the width of the grid.
     *
     * @return The width of the grid.
     */
    public double getSizeGridX() {
        return sizeGridX;
    }

    /**
     * Sets the current time view position of the candle where the cursor is located.
     *
     * @param cursorXPosition The position of the cursor on the X axis.
     */
    public void setCursorX(int cursorXPosition) {
        currentTime.setLocation(cursorXPosition - LABEL_WIDTH / 2, 0);
    }

    /**
     * Sets whether or not to display the current time of the candle where the cursor is located.
     *
     * @param visible `true` to show time, `false` to hide it.
     */
    public void setCurrentTimeVisible(boolean visible) {
        currentTime.setVisible(visible);
    }

    /**
     * Sets the candle time where the mouse pointer is located at the moment.
     *
     * @param timeText The text to display.
     */
    public void setCurrentTimeText(String timeText) {
        currentTime.setText(timeText);
    }

    /**
     * Sets the relative position between the candles.
     *
     * @param relativePosition The relative position.
     */
    public void setRelativePosition(int relativePosition) {
            this.relativePosition = relativePosition;
    }

    /**
     * Gets the relative position between the candles.
     *
     * @return The relative position.
     */
    public int getRelativePosition() {
        return relativePosition;
    }

    /**
     * Sets the list of candles visible on the chart at the moment.
     *
     * @param candleList The list of candles displayed on the chart
     */
    public void setCandleList(List<Candle> candleList) {
        if(candleList != null) {
            this.candleList = candleList;
        }
    }

    /**
     * Gets the list of candles visible on the chart at the moment.
     *
     * @return The list of candles visible.
     */
    public List<Candle> getCandleList() {
        return candleList;
    }

    /**
     * Returns the corresponding candle index according to the X-axis pixel passed by parameter.
     *
     * @param pixel The value in pixels on the X axis.
     * @return The index of the candle corresponding to this pixel.
     */
    private int candlePositionOfPixel(int pixel){
        return (chartWidth - 2 - pixel) / relativePosition;
    }
}
