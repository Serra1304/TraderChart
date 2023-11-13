package candleChart.controller;

import candleChart.interfaces.ChartObserverAdapter;
import candleChart.model.Candle;
import candleChart.view.TitleView;

/**
 * Controller class responsible for managing the candlestick chart title view.
 * This controller communicates with TitleView to set the chart title and update price information.
 */
public class TitleController extends ChartObserverAdapter {
    private final TitleView titleView;

    /**
     * Constructs a new TitleController with a given TitleView.
     *
     * @param titleView The TitleView associated with this controller.
     */
    public TitleController(TitleView titleView) {
        this.titleView = titleView;
    }

    /**
     * Sets the chart title displayed in the associated TitleView.
     *
     * @param chartTitle The chart title to set.
     */
    public void setChartTitle(String chartTitle) {
        titleView.setTitle(chartTitle);
    }

    /**
     * Retrieves the chart title currently displayed in the associated TitleView.
     *
     * @return The chart title.
     */
    public String getChartTitle() {
        return titleView.getTitle();
    }

    /**
     * Updates the TitleView based on the provided Candle data.
     * If the Candle is not null, it displays the open, high, low, and close prices.
     * If the Candle is null, it hides the price information in the TitleView.
     *
     * @param candle The Candle data to update the view.
     */
    @Override
    public void updateCursorCandle(Candle candle) {
        if(candle != null) {
            titleView.setVisiblePrice(true);
            titleView.setPrices("O: " + candle.openPrice() +
                    "   H: " + candle.highPrice() +
                    "   L: " + candle.lowPrice() +
                    "   C: " + candle.closePrice());
        } else {
            titleView.setVisiblePrice(false);
        }
    }

    /**
     * Updates the visibility of price information in the TitleView.
     *
     * @param visibility true to make the price information visible, false to hide it.
     */
    @Override
    public void updateCursorVisible(boolean visibility) {
        titleView.setVisiblePrice(visibility);
    }
}
