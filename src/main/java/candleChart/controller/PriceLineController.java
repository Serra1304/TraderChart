package candleChart.controller;

import candleChart.interfaces.ChartObserverAdapter;
import candleChart.view.PriceLineView;

/**
 * PriceLineView view controller.
 */
public class PriceLineController extends ChartObserverAdapter {
    private final PriceLineView priceLineView;

    /**
     * Constructor that sets the view of this controller.
     *
     * @param priceLineView View of this controller.
     */
    public PriceLineController(PriceLineView priceLineView) {
        this.priceLineView = priceLineView;
    }

    /**
     * Updates the position on the Y axis of the cursor in the view.
     *
     * @param cursorX Cursor position on the X axis.
     * @param cursorY Cursor position on the Y axis.
     */
    @Override
    public void updateCursorPosition(int cursorX, int cursorY) {
        priceLineView.setCursorPosition(cursorY);
    }

    /**
     * Update the size of the grid in the view.
     *
     * @param gridX Grid size on the X axis.
     * @param gridY Grid size on the Y axis.
     */
    @Override
    public void updateGridSize(double gridX, double gridY) {
        priceLineView.setSizeGridY(gridY);
    }

    /**
     * Update price range in the view.
     *
     * @param maxPrice Maximum price represented on the chart.
     * @param minPrice Minimum price represented on the chart.
     */
    @Override
    public void updatePriceRange(double maxPrice, double minPrice) {
        priceLineView.setPriceRange(maxPrice, minPrice);
    }

    /**
     * Updates the visibility of the cursor in the view.
     *
     * @param visibility Visibility of the cursor.
     */
    @Override
    public void updateCursorVisible(boolean visibility) {
        priceLineView.setCurrentPriceLabelVisible(visibility);
    }
}
