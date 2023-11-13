package candleChart.interfaces;

import candleChart.model.Candle;

import java.util.List;

/**
 * Interface that defines methods for observing and reporting events in a graph.
 * Objects that implement this interface can register as observers in a graph to receive updates about various events.
 */
public interface ChartObserver {
    /**
     * Notify the update of the cursor position on the graph.
     *
     * @param cursorX Position X of the cursor.
     * @param cursorY Position Y of the cursor.
     */
    void updateCursorPosition(int cursorX, int cursorY);

    /**
     * Notify cursor visibility update.
     *
     * @param visibility Cursor visibility.
     */
    void updateCursorVisible(boolean visibility);

    /**
     * Notify the candle on which the cursor is pointing.
     *
     * @param candle Candle on which the cursor.
     */
    void updateCursorCandle(Candle candle);

    /**
     * Notifiy the update of the chart candle list.
     *
     * @param candleList The chart candle list.
     */
    void updateCandleList(List<Candle> candleList);

    /**
     * Notify the update of the relative position of the candle.
     *
     * @param relativePosition The relative position of the candle.
     */
    void updateCandleRelativePosition(int relativePosition);

    /**
     * Notify the update of the chart size.
     *
     * @param width The width of the chart.
     * @param height The height of the chart.
     */
    void updateChartSize(int width, int height);

    /**
     * Notify the update of the  grid size.
     *
     * @param gridSizeX The width of the grid
     * @param gridSizeY The height of the grid.
     */
    void updateGridSize(double gridSizeX, double gridSizeY);

    /**
     * Notify the update of the chart price range.
     *
     * @param maxPrice The maximum price of the chart.
     * @param minPrice The minimum price of the chart.
     */
    void updatePriceRange(double maxPrice, double minPrice);

    /**
     * Notify the update the observer list.
     *
     * @param observers The observer list.
     */
    void updateObservers(List<ChartObserver> observers);
}
