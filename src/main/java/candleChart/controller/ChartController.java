package candleChart.controller;

import candleChart.interfaces.ChartObserver;
import candleChart.model.Candle;
import candleChart.view.CandleView;
import candleChart.view.ChartView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main driver for the candle chart.
 * It is responsible for coordinating the interaction between the different components of the graph.
 */
public class ChartController {
    private final ChartView chartView;
    private final CandleController candleController;
    private final CursorController cursorController;
    private final GridController gridController;

    private final List<ChartObserver> observers;
    private int width, height;

    /**
     * ChartController constructor.
     *
     * @param chartView The candlestick chart view to monitor.
     */
    public ChartController(ChartView chartView) {
        this.chartView = chartView;
        CandleView candleView = new CandleView();
        observers = new ArrayList<>();

        // Drivers initialization.
        candleController = new CandleController(candleView);
        cursorController = new CursorController(chartView);
        gridController = new GridController(chartView);

        // Observer registration.
        addObserver(candleController);
        addObserver(cursorController);
        addObserver(gridController);

        // View settings.
        chartView.add(candleView);
        chartView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateChartSize();
                gridController.updateGrid();
            }
        });
    }

    /**
     * Adds an observer to the controller.
     *
     * @param observer The observer to register to receive updates.
     */
    public void addObserver(ChartObserver observer) {
        observers.add(observer);
        notifyObservers();
    }

    /**
     * Gets a list of all observers.
     *
     * @return List of observers.
     */
    public List<ChartObserver> getObservers() {
        return observers;
    }

    /**
     * Removes an observer from the observer list.
     *
     * @param observer The observer to remove from the list.
     */
    public void removeObserver(ChartObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all registered observers of registered observers.
     */
    private void notifyObservers() {
        for(ChartObserver observer: observers) {
            observer.updateObservers(observers);
        }
    }

    /**
     * Notify all observers of updated chart size.
     */
    private void notifyUpdateChartSize() {
        for(ChartObserver observer: observers) {
            observer.updateChartSize(width, height);
        }
    }

    /**
     * Update chart resize.
     */
    private void updateChartSize() {
        this.width = chartView.getWidth();
        this.height = chartView.getHeight();
        notifyUpdateChartSize();
    }

    /**
     * Sets cursor visibility.
     *
     * @param visibility Cursor visibility;
     */
    public void setCursorVisible(boolean visibility) {
        cursorController.setGlobalCursosVisible(visibility);
    }

    /**
     * Gets cursor visibility;
     *
     * @return 'true' if the cursor is visible or 'false' otherwise.
     */
    public boolean isCursorVisible() {
        return cursorController.isGlobalCursosVisible();
    }

    /**
     * Sets grid visibility.
     *
     * @param currentVisibility Grid visibility.
     */
    public  void setGridVisible(boolean currentVisibility) {
        gridController.setGridVisible(currentVisibility);
    }

    /**
     * Gets grid visibility.
     *
     * @return 'true' if the grid is visible or 'false' otherwise.
     */
    public boolean isGridVisible() {
        return gridController.isGridVisible();
    }

    /**
     * Increases the size of the candle. If this size can be increased, the number of candles per grid is reduced.
     */
    public void upCandleSize() {
        if(candleController.upCandleSize()) {
            gridController.downGridSize();
        }
    }

    /**
     * Reduce the size of the candle. If this size can be reduced, the number of candles per grid is increased.
     */
    public void downCandleSize() {
        if(candleController.downCandleSize()) {
            gridController.upGridSize();
        }
    }

    /**
     * Sets the candle buffer that will be represented on the chart.
     *
     * @param candleList Candle buffer.
     */
    public void setCandleBuffer(List<Candle> candleList) {
        candleController.setCandleBuffer(candleList);
    }

    /**
     * Gets the candle buffer that will be represented on the chart.
     *
     * @return The candle buffer.
     */
    public List<Candle> getCandleBuffer() {
        return candleController.getCandleBuffer();
    }
}
