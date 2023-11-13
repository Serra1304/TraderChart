package candleChart.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class that represents the top view, which shows the name of the current chart and the prices of the candlestick on
 * which the mouse pointer is currently located.
 */
public class TitleView extends JPanel {
    private static final Color FONT_COLOR = Color.GRAY;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Font FONT = new Font("Arial", Font.PLAIN, 12);

    private final JLabel title;
    private final JLabel prices;

    /**
     * Constructor that sets the view's default data.
     */
    public TitleView() {
        title = new JLabel();
        title.setForeground(FONT_COLOR);
        title.setFont(FONT);

        prices = new JLabel();
        prices.setForeground(FONT_COLOR);
        prices.setFont(FONT);
        prices.setVisible(false);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(BACKGROUND_COLOR);
        add(title);
        add(prices);
    }

    /**
     * Sets the title that will be displayed in the view in case the value passed by parameter is not null.
     *
     * @param title Title to show.
     */
    public void setTitle(String title) {
        if(title != null) {
            this.title.setText(title);
        }
    }

    /**
     * Gets the title displayed at the top of the chart.
     *
     * @return Chart title.
     */
    public String getTitle() {
        return title.getText();
    }

    /**
     * Sets the prices of the candle that will be displayed in the view in case the value passed by parameter is not null.
     *
     * @param prices Prices to show.
     */
    public void setPrices(String prices) {
        if(prices != null) {
            this.prices.setText(prices);
        }
    }

    /**
     * Gets a string of the prices of the candle shown at the top of the chart.
     *
     * @return Candle price.
     */
    public String getPrices() {
        return prices.getText();
    }

    /**
     * Sets whether the candle prices at the top are visible.
     *
     * @param visible Price visibility.
     */
    public void setVisiblePrice(boolean visible) {
        prices.setVisible(visible);
    }

    /**
     * Gets whether the candle prices at the top ara visible.
     *
     * @return Price visibility.
     */
    public boolean getIsVisiblePrice() {
        return prices.isVisible();
    }
}
