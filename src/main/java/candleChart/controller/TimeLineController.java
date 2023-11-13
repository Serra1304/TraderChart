package candleChart.controller;

import candleChart.interfaces.ChartObserverAdapter;
import candleChart.model.Candle;
import candleChart.view.TimeLineView;

import java.util.List;

/**
 * Controller for timeline view.
 */
public class TimeLineController extends ChartObserverAdapter {
    private final TimeLineView timeLineView;

    /**
     * Constructor of the TimeLineController class.
     *
     * @param timeLineView The timeline view to control.
     */
    public TimeLineController(TimeLineView timeLineView) {
        this.timeLineView = timeLineView;
    }

    /**
     * Updates the width of the chart in the view.
     *
     * @param width Width of the chart.
     * @param height Height of the graph.
     */
    @Override
    public void updateChartSize(int width, int height) {
        timeLineView.setChartWidth(width);
    }

    /**
     * Updates the width of the grid in the view for rendering slices on the timeline.
     *
     * @param gridX Position of the grid on the X axis.
     * @param gridY Position of the grid on the Y axis.
     */
    @Override
    public void updateGridSize(double gridX, double gridY) {
        timeLineView.setSizeGridX(gridX);
    }

    /**
     * Updates the mouse cursor position in the timeline view
     *
     * @param cursorX Position X of the cursor.
     * @param cursorY Position Y of the cursor.
     */
    @Override
    public void updateCursorPosition(int cursorX, int cursorY) {
        timeLineView.setCursorX(cursorX);
    }

    /**
     * Updates whether the cursor is visible or not depending on whether the mouse is inside or outside the graph.
     *
     * @param visibility True if the cursor is visible, false if it is not.
     */
    @Override
    public void updateCursorVisible(boolean visibility) {
        timeLineView.setCurrentTimeVisible(visibility);
    }

    /**
     * Updates the candle on which the cursor is currently located.
     * In case of receiving a null candle, the cursor showing the current time of the candle will be hidden, otherwise
     * this cursor will be shown and updated.
     *
     * @param candle The current candle, or null if the cursor is not over a candle.
     */
    @Override
    public void updateCursorCandle(Candle candle) {
        if(candle != null) {
            timeLineView.setCurrentTimeVisible(true);
            timeLineView.setCurrentTimeText(candle.getTime().toString());
        } else {
            timeLineView.setCurrentTimeVisible(false);
        }
    }

    /**
     * Updates the list of candles visible on the chart and the relative position between them.
     *
     * @param candleList The list of visible candles.
 //    * @param relativePosition The relative position on the timeline.
     */
    @Override
    public void updateCandleList(List<Candle> candleList) {
        if(candleList != null) {
            timeLineView.setCandleList(candleList);
        }
     //   timeLineView.setRelativePosition(relativePosition);
    }

    @Override
    public void updateCandleRelativePosition(int relativePosition) {
        timeLineView.setRelativePosition(relativePosition);
    }
}
