package candleChart.controller;

import candleChart.interfaces.ChartObserver;
import candleChart.interfaces.ChartObserverAdapter;
import candleChart.view.ChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller in charge of managing the graph grid.
 * The GridController is responsible for managing the size of the grid, its visibility, and its updates in response
 * to changes in the graph view.
 */
public class GridController extends ChartObserverAdapter {
    private final ChartView chartView;
    private List<ChartObserver> observers;

    private double gridXSize, gridYSize;
    private int relativePosition;
    private int candlesByFraction;

    /**
     * Creates a GridController instance with the specified graph view.
     *
     * @param chartView The chart view to which the grid controller will be attached.
     */
    public GridController(ChartView chartView) {
        this.chartView = chartView;

        observers = new ArrayList<>();
        relativePosition = 1;
        candlesByFraction = 3;
    }

    /**
     * Updates the relative position of the candles based on the value provided.
     *
     * @param relativePosition Relative position of candles.
     */
    @Override
    public void updateCandleRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    /**
     * Update the observer list on the value provided.
     *
     * @param observers Observer list.
     */
    @Override
    public void updateObservers(List<ChartObserver> observers) {
        this.observers = observers;
    }

    /**
     * Notify observers of grid update
     */
    private void notifyUpdateGrid() {
        for (ChartObserver observer : observers) {
            observer.updateGridSize(gridXSize, gridYSize);
        }
    }

    /**
     * Updates the grid based on current values.
     * This method calculates the size of the grid based on the number of candles entered per fraction, the relative
     * position of the candles and the height of the chart view.
     */
    public void updateGrid() {
        gridXSize = candlesByFraction * relativePosition;

        int divisions = chartView.getHeight() / (int) gridXSize;
        int rest = chartView.getHeight() % (int) gridXSize;
        double restGap = (double) rest / divisions;
        gridYSize = gridXSize + restGap;

        chartView.setGridSize(gridXSize,gridYSize);
        notifyUpdateGrid();
    }

    /**
     * Increases the number of candles that enters by fraction.
     */
    public void upGridSize() {
        candlesByFraction++;
        updateGrid();
    }

    /**
     * Reduces the number of candles that enters by fraction.
     */
    public void downGridSize() {
        candlesByFraction--;
        updateGrid();
    }

    /**
     * Sets the visibility of the grid in the chart view.
     *
     * @param gridVisible `true` to show the grid, `false` to hide it.
     */
    public void setGridVisible(boolean gridVisible) {
        chartView.setGridVisible(gridVisible);
    }

    /**
     * Checks if the grid is visible in the chart view.
     *
     * @return `true` if the grid is visible, `false` if it is hidden.
     */
    public boolean isGridVisible() {
        return chartView.isGridVisible();
    }

    /**
     * Sets the number of candles for each fraction of the chart.
     *
     * @param candlesByFraction The number of candle per fraction.
     */
    public void setCandlesByFraction(int candlesByFraction) {
        this.candlesByFraction = candlesByFraction;
        updateGrid();
    }

    /**
     * Gets the number of candles for each fraction of the chart.
     *
     * @return Number of candle per fraction.
     */
    public int getCandlesByFraction() {
        return candlesByFraction;
    }
}
