package candleChart.controller;

import candleChart.interfaces.ChartObserver;
import candleChart.interfaces.ChartObserverAdapter;
import candleChart.model.Candle;
import candleChart.view.CandleView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for candle view (CandleView) on the candle chart.
 */
public class CandleController extends ChartObserverAdapter {
    private final int MAXIMUM_CANDLE_SIZE = 9;
    private final int MINIMUM_CANDLE_SIZE = 3;
    private final int CANDLE_SPACING = 4;

    private final CandleView candleView;
    private List<ChartObserver> observers;
    private List<Candle> candleBuffer;
    private List<Candle> candleList;
    private Candle cursorCandle;

    private double maxPrice, minPrice;
    private int candleWidth;
    private int relativePosition;
    private int cursorPositionX, cursorPositionY;

    /**
     * Creates an instance of the candle controller with the associated CandleView.
     *
     * @param candleView The view of candles to control.
     */
    public CandleController(CandleView candleView) {
        this.candleView = candleView;
        candleBuffer = new ArrayList<>();
        candleList = new ArrayList<>();
        cursorCandle = null;
        candleWidth = MAXIMUM_CANDLE_SIZE;
        relativePosition = candleWidth + CANDLE_SPACING;
        observers = new ArrayList<>();

        updateCandleRelativePosition();
        candleView.setRelativePosition(relativePosition);
        candleView.setCandleWidth(candleWidth);
        candleView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateCandleList();
                updatePriceRange();
            }
        });
    }

    /**
     * Update cursor position.
     *
     * @param cursorPositionX The cursor position on the X axis.
     * @param cursorPositionY The cursor position on the Y axis.
     */
    @Override
    public void updateCursorPosition(int cursorPositionX, int cursorPositionY) {
        this.cursorPositionX = cursorPositionX;
        this.cursorPositionY = cursorPositionY;
        updateCandleCursor();
    }

    /**
     * Update the observer list.
     * @param observers The observer list.
     */
    @Override
    public void updateObservers(List<ChartObserver> observers) {
        if(observers != null) {
            this.observers = observers;
            notifyPriceRange();
            notifyCandleRelativePosition();
            notifyCandleList();
            notifyUpdateCandleCursor();
        }
    }

    /**
     * Notify observers about updated candles displayed in the view.
     */
    private void notifyCandleList() {
        for (ChartObserver observer : observers) {
            observer.updateCandleList(candleList);
        }
    }

    /**
     * Updates the list of candles displayed in the view based on the current size and content.
     */
    private void updateCandleList() {
        int numeroVisibleDeVelas = Math.max(candleView.getWidth() / relativePosition + 1, 0);
        int candleFirst = 0;
        int candleLast = Math.min(numeroVisibleDeVelas, candleBuffer.size());

        List<Candle> candleList = candleBuffer.subList(candleFirst, candleLast);
        this.candleList = candleList;
        candleView.setCandleList(candleList);
        notifyCandleList();

    }

    /**
     * Notify observers to updates the maximum and minimum price range.
     */
    private void notifyPriceRange() {
        for(ChartObserver observer : observers) {
            observer.updatePriceRange(maxPrice, minPrice);
        }
    }

    /**
     * Updates the high and low price range based on the displayed candles.
     */
    private void updatePriceRange() {
        maxPrice = -999999999;
        minPrice = 999999999;

        for (Candle candle : candleList) {
            if (candle.highPrice() > maxPrice) {
                maxPrice = candle.highPrice();
            }
            if (candle.lowPrice() < minPrice) {
                minPrice = candle.lowPrice();
            }
        }
        if (candleList.isEmpty()) {
            maxPrice = 0;
            minPrice = 0;
        }
        candleView.setMaxPrice(maxPrice);
        candleView.setMinPrice(minPrice);
        notifyPriceRange();
    }

    /**
     * Notify observers of cursor candle update.
     */
    private void notifyUpdateCandleCursor() {
        for(ChartObserver observer : observers) {
            observer.updateCursorCandle(cursorCandle);
        }
    }

    /**
     * Update candle cursor.
     */
    private void updateCandleCursor() {
        int index = ((candleView.getWidth() +2) - cursorPositionX) / relativePosition;
        if(index >= 0 && index < candleList.size()) {
            cursorCandle = candleList.get(index);
        } else {
            cursorCandle = null;
        }
        notifyUpdateCandleCursor();
    }

    /**
     * Notify observers of update candle relative position.
     */
    private void notifyCandleRelativePosition() {
        for(ChartObserver observer : observers) {
            observer.updateCandleRelativePosition(relativePosition);
        }
    }

    /**
     * Update candle relative position
     */
    private void updateCandleRelativePosition() {
        relativePosition = candleWidth + CANDLE_SPACING;
        candleView.setRelativePosition(relativePosition);
        notifyCandleRelativePosition();
    }

    /**
     * Increases the size of the candles by 2 units if the maximum size is not reached.
     *
     * @return `true` if the size has been increased, otherwise `false`.
     */
    public boolean upCandleSize() {
        if(candleWidth < MAXIMUM_CANDLE_SIZE) {
            candleWidth += 2;
            candleView.setCandleWidth(candleWidth);
            updateCandleRelativePosition();
            return true;
        }
        return false;
    }

    /**
     * Reduce the size of the candles by 2 units if the minimum size is not reached.
     *
     * @return `true` if the size has been reduced, otherwise `false`.
     */
    public boolean downCandleSize() {
        if(candleWidth > MINIMUM_CANDLE_SIZE) {
            candleWidth -= 2;
            candleView.setCandleWidth(candleWidth);
            updateCandleRelativePosition();
            return true;
        }
        return false;
    }

    /**
     * Sets the width of the candle, as long as it is within the established limits.
     * If the value is not allowed, being within the limits, the next higher allowed value will be established.
     *
     * @param candleWidth The width candle.
     *
     * @return 'true' if the candle size could be set. 'false' otherwise.
     */
    public boolean setCandleWidth(int candleWidth) {
        if(candleWidth <= MAXIMUM_CANDLE_SIZE && candleWidth >= MINIMUM_CANDLE_SIZE) {
            this.candleWidth = candleWidth % 2 == 0? candleWidth +1: candleWidth;
            updateCandleRelativePosition();
            return true;
        }
        return false;
    }

    /**
     * Gets the width of the candle.
     *
     * @return The width of the candle.
     */
    public int getCandleWidth() {
        return candleWidth;
    }

    /**
     * Gets the current relative position of the candles in the view.
     *
     * @return The current relative position of the candles.
     */
    public int getRelativePosition() {
        return relativePosition;
    }

    /**
     * Sets the buffer of candles to be displayed in the candle view.
     *
     * @param candleBuffer The list of candles to set as a buffer.
     */
    public void setCandleBuffer(List<Candle> candleBuffer) {
        if(candleBuffer != null) {
            this.candleBuffer = candleBuffer;
            updateCandleList();
        }
    }

    /**
     * Gets the candle buffer displayed in the candle view.
     *
     * @return The buffered list of candles.
     */
    public List<Candle> getCandleBuffer() {
        return candleBuffer;
    }
}
