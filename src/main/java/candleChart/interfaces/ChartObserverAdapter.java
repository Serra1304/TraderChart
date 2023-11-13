package candleChart.interfaces;

import candleChart.model.Candle;

import java.util.List;

/**
 * Abstract class that serves as an adapter for the ChartObserver interface.
 * Provides default implementations for all methods of the interface, allowing classes that implement it to override
 * only the methods they need.
 */
public abstract class ChartObserverAdapter implements ChartObserver{
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCursorPosition(int cursorX, int cursorY) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCursorVisible(boolean visibility) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGridSize(double gridX, double gridY) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePriceRange(double maxPrice, double minPrice) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChartSize(int width, int height) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCandleList(List<Candle> candleList) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCursorCandle(Candle candle) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCandleRelativePosition(int relativePosition) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObservers(List<ChartObserver> observers) {
    }
}
