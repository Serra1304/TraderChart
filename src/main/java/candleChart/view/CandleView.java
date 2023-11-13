package candleChart.view;

import candleChart.model.Candle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CandleView class represents a view that displays a series of candles on a chart.
 * Each candlestick is represented as a line and a rectangle, where the line connects the highest and lowest prices,
 * and the rectangle represents the difference between the opening and closing prices.
 * The color of the candle depends on whether the opening price is higher or lower than the closing price, being green
 * if the closing price is higher than the opening price and red if the closing price is lower than the opening price.
 */
public class CandleView extends JPanel {
    private List<Candle> candleList;

    private int candleWidth;
    private int relativePosition;
    private double maxPrice, minPrice;

    /**
     * Constructor of the CandleView class.
     */
    public CandleView() {
        candleList = new ArrayList<>();
        candleWidth = 0;
        relativePosition = 0;
        maxPrice = 0;
        minPrice = 0;

        setOpaque(false);
    }

    /**
     * Method in charge of drawing the candles on the chart.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(!candleList.isEmpty()) {
            for (int i = 0; i < candleList.size(); i++) {
                // Paint the line
                g.setColor(candleList.get(i).openPrice() > candleList.get(i).closePrice() ? new Color(127, 0, 0) : new Color(0, 127, 0));
                g.drawLine(positionXOfCandle(i), positionYOfPrice(candleList.get(i).highPrice()),
                           positionXOfCandle(i), positionYOfPrice(candleList.get(i).lowPrice()));

                // Paint the rectangle
                double maxOpenOrClose = Math.max(candleList.get(i).openPrice(), candleList.get(i).closePrice());
                double minOpenOrClose = Math.min(candleList.get(i).openPrice(), candleList.get(i).closePrice());
                int rectY = positionYOfPrice(maxOpenOrClose);
                int rectHeight = positionYOfPrice(minOpenOrClose) - rectY;
                g.drawRect(positionXOfCandle(i) - (candleWidth / 2), rectY, candleWidth, rectHeight);
                g.setColor(candleList.get(i).openPrice() > candleList.get(i).closePrice() ? new Color(255, 0, 0) : new Color(0, 255, 0));
                g.fillRect(positionXOfCandle(i) - (candleWidth / 2), rectY, candleWidth, rectHeight);
            }
        }
    }

    /**
     * Sets the list of candles to display on the chart.
     *
     * @param candleList The list of candles.
     */
    public void setCandleList(List<Candle> candleList) {
        if(candleList != null) {
            this.candleList = candleList;
            repaint();
        }
    }

    /**
     * Gets the list of candles to display on the chart.
     *
     * @return The list of candles.
     */
    public List<Candle> getCandleList() {
        return candleList;
    }

    /**
     * Set the width of the candles in pixels.
     *
     * @param candleWidth The width of the candles.
     */
    public void setCandleWidth(int candleWidth) {
        this.candleWidth = candleWidth;
        repaint();
    }

    /**
     * Gets the width of the candles in pixels.
     *
     * @return The width of the candles.
     */
    public int getCandleWidth() {
        return candleWidth;
    }

    /**
     * Sets the relative position of the candles on the chart.
     *
     * @param relativePosition The relative position.
     */
    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
        repaint();
    }

    /**
     * Gets the relative position of the candles on the chart.
     *
     * @return The relative position.
     */
    public int getRelativePosition() {
        return relativePosition;
    }

    /**
     * Set the maximum price on the chart.
     *
     * @param maxPrice The maximum price.
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Gets the maximum price on the chart.
     *
     * @return The maximum price.
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Set the minimum price on the chart.
     *
     * @param minPrice The minimum price.
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * Get the minimum price on the chart.
     *
     * @return The minimum price.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Calculates the position in pixels on the X axis of the candle whose index is provided.
     *
     * @param index Candle index.
     * @return The position in pixels on the X axis.
     */
    private int positionXOfCandle(int index) {
        return ((getWidth()/relativePosition) - index) * relativePosition - 1;
    }

    /**
     * Calculates the position in pixel on the Y axis of the provided price.
     *
     * @param price Price for calculation.
     * @return The position in pixels on the Y axis.
     */
    private int positionYOfPrice(double price) {
        return (int) ((maxPrice - price) / ((maxPrice - minPrice) / getHeight()));
    }
}
