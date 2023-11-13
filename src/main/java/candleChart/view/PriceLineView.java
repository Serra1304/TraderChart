package candleChart.view;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * View representing a price line on the chart. Shows price divisions and a label of the current price where the mouse
 * pointer is located.
 */
public class PriceLineView extends JPanel {
    private final DecimalFormat dFormat = new DecimalFormat("0.0000");
    private final JLabel currentPriceLabel;

    private int cursor;
    private double sizeGridY;
    private double maxPrice, minPrice;

    /**
     * Constructor of the PriceLineView class. Initializes the view and sets the current price tag.
     */
    public PriceLineView() {
        sizeGridY = 0;
        maxPrice = 0;
        minPrice = 0;

        currentPriceLabel = new JLabel();
        currentPriceLabel.setBackground(Color.GRAY);
        currentPriceLabel.setForeground(Color.BLACK);
        currentPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentPriceLabel.setOpaque(true);
        currentPriceLabel.setVisible(false);
        currentPriceLabel.setSize(60,20);

        setBackground(Color.BLACK);
        setLayout(null);
        add(currentPriceLabel);
        setFocusable(false);
    }

    /**
     * Paint the components of the price line, such as divisions and division prices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int divisions = getHeight() / (int) sizeGridY ;

        g.setColor(Color.GRAY);
        for(int i = 0; i < divisions; i++) {
            int y = (int) Math.floor(i * sizeGridY);
            g.drawLine(0, y, 5, y);
            g.drawString(dFormat.format(maxPrice - (y * pricePixel())), 10, y + 10);
        }
    }

    /**
     * Sets the grid size on the Y axis and updates the view.
     *
     * @param sizeGridY Size of the c on the Y axis.
     */
    public void setSizeGridY(double sizeGridY) {
        this.sizeGridY = sizeGridY;
        repaint();
    }

    /**
     * gets the size of the division on the Y axis.
     *
     * @return Size of the Y axis division.
     */
    public double getSizeGridY() {
        return sizeGridY;
    }

    /**
     * Sets the position of the cursor in the view.
     *
     * @param cursor Position of the cursor on the Y axis.
     */
    public void setCursorPosition(int cursor) {
        this.cursor = cursor;
        updateCurrentPrice();
        repaint();
    }

    /**
     * Gets the position of the cursor.
     *
     * @return Position of the cursor on the Y axis.
     */
    public int getCursorPosition() {
        return cursor;
    }

    /**
     * Sets the visibility of the current price tag.
     *
     * @param visible `true` if the price tag should be visible, `false` if it should be hidden.
     */
    public void setCurrentPriceLabelVisible(boolean visible) {
        currentPriceLabel.setVisible(visible);
    }

    /**
     * Gets the visibility of the current price tag.
     *
     * @return Visibility of the current price tag.
     */
    public boolean isCurrentPriceVisible() {
        return currentPriceLabel.isVisible();
    }

    /**
     * Set the maximum and minimum price range.
     *
     * @param maxPrice Maximum price.
     * @param minPrice Minimum price.
     */
    public void setPriceRange(double maxPrice, double minPrice) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    /**
     * Gets the highest price represented in the chart.
     *
     * @return The maximum price.
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Gets the lowest price represented in the chart.
     *
     * @return The minimum price.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Calculates the relationship between the change in pixels and the change in price.
     *
     * @return The relationship between the change in price and the change in pixels, or what is the same, returns the
     * value for each pixel according to the size of the graph and the price difference existing in it.
     */
    private double pricePixel() {
        return (maxPrice - minPrice) / getHeight();
    }

    /**
     * Updates the position and text of the current price tag where it is located.
     */
    private void updateCurrentPrice(){
        currentPriceLabel.setLocation(0, cursor - currentPriceLabel.getHeight() / 2);
        currentPriceLabel.setText(dFormat.format(maxPrice - cursor * pricePixel()));
    }
}
