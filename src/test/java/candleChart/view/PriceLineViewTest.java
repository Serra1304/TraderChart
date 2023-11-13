package candleChart.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PriceLineViewTest {

    private PriceLineView priceLineView;

    @BeforeEach
    public void setUp() {
        priceLineView = new PriceLineView();
    }

    @Test
    public void testSetGridYSize() {
        priceLineView.setSizeGridY(10.0);
        assertEquals(10.0, priceLineView.getSizeGridY(), 0.01);
    }

    @Test
    public void testSetCursor() {
        priceLineView.setCursorPosition(50);
        assertEquals(50, priceLineView.getCursorPosition());
    }

    @Test
    public void testSetCurrentPriceLabelVisible() {
        priceLineView.setCurrentPriceLabelVisible(true);
        assertTrue(priceLineView.isCurrentPriceVisible());

        priceLineView.setCurrentPriceLabelVisible(false);
        assertFalse(priceLineView.isCurrentPriceVisible());
    }

    @Test
    public void testSetPriceRange() {
        priceLineView.setPriceRange(100.0, 50.0);
        assertEquals(100.0, priceLineView.getMaxPrice());
        assertEquals(50.0, priceLineView.getMinPrice());
    }
}

