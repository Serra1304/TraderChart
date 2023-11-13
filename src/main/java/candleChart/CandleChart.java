package candleChart;

import candleChart.controller.ChartController;
import candleChart.controller.PriceLineController;
import candleChart.controller.TimeLineController;
import candleChart.controller.TitleController;
import candleChart.model.Candle;
import candleChart.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The CandleChart class acts as the main component that coordinates other controllers and views to display a candle chart.
 */
public class CandleChart extends JPanel {
    // Constant dimensions for the preferred size of different components.
    public static final Dimension TITLE_SIZE = new Dimension(0, 25);
    public static final Dimension TIME_LINE_SIZE = new Dimension(0, 40);
    public static final Dimension PRICE_LINE_SIZE = new Dimension(70, 0);

    // Views of the different components of the chart.
    private final ChartView chartView;
    private final PriceLineView priceLineView;
    private final TimeLineView timeLineView;
    private final TitleView titleView;

    // Controllers associated with the views.
    private final ChartController chartController;
    private final PriceLineController priceLineController;
    private final TimeLineController timeLineController;
    private final TitleController titleController;

    /**
     * Constructor of the CandleChart class. Initializes the views, controllers and configures the chart user interface.
     */
    public CandleChart() {
        // Initialize the view.
        chartView = new ChartView();
        priceLineView = new PriceLineView();
        timeLineView = new TimeLineView();
        titleView = new TitleView();

        // Initialize the controllers associated with the views.
        chartController = new ChartController(chartView);
        priceLineController = new PriceLineController(priceLineView);
        timeLineController = new TimeLineController(timeLineView);
        titleController = new TitleController(titleView);

        // Set preferred sizes for some views.
        priceLineView.setPreferredSize(PRICE_LINE_SIZE);
        timeLineView.setPreferredSize(TIME_LINE_SIZE);
        titleView.setPreferredSize(TITLE_SIZE);

        // Add observers to ChartController to act in response to changes in the chart.
        chartController.addObserver(timeLineController);
        chartController.addObserver(priceLineController);
        chartController.addObserver(titleController);

        // Configure the appearance and layout of the main panel.
        setLayout(new BorderLayout());
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 0));

        // Add the views to the main panel.
        add(titleView, BorderLayout.NORTH);
        add(timeLineView, BorderLayout.SOUTH);
        add(priceLineView, BorderLayout.EAST);
        add(chartView, BorderLayout.CENTER);
    }

    /**
     * Set the visibility of the grid on the chart.
     *
     * @param visibility true to make the grid visible, false to hide it.
     */
    public void setGridVisible(boolean visibility){
        chartController.setGridVisible(visibility);
    }

    /**
     * Gets the visibility of the grid chart.
     *
     * @return true if the grid is visible, false if it is hidden.
     */
    public boolean isGridVisible() {
        return chartController.isGridVisible();
    }

    /**
     * Set the visibility of the cursor on the chart.
     *
     * @param visibility true to make the cursor visible, false to hide it.
     */
    public void setCursorVisible(boolean visibility) {
        chartController.setCursorVisible(visibility);
    }

    /**
     * Gets whether the cursor on the chart is visible.
     *
     * @return true if the cursor is visible, false if it is hidden.
     */
    public boolean isCursorVisible() {
        return chartController.isCursorVisible();
    }

    /**
     * Set the chart title.
     *
     * @param title The new title of the chart.
     */
    public void setTitle(String title) {
        titleController.setChartTitle(title);
    }

    /**
     * Get the chart title.
     *
     * @return The chart title.
     */
    public String getTitle() {
        return titleController.getChartTitle();
    }

    /**
     * Sets the set of candles to display on the chart.
     *
     * @param candleList List of candles to display on the chart.
     */
    public void setCandleBuffer(List<Candle> candleList) {
        chartController.setCandleBuffer(candleList);
    }
}