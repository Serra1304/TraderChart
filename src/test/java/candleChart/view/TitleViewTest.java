package candleChart.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TitleViewTest {
    private TitleView titleView;

    @BeforeEach
    public void setUp() {
        titleView = new TitleView();
    }

    @Test
    public void testSetTitle() {
        titleView.setTitle("Test Title");
        assertEquals("Test Title", titleView.getTitle());
    }

    @Test
    public void testSetNullTitle() {
        titleView.setTitle(null);
        assertNotNull(titleView.getTitle());
    }

    @Test
    public void testSetPrices() {
        titleView.setPrices("Test Prices");
        assertEquals("Test Prices", titleView.getPrices());
    }

    @Test
    public void testSetNullPrices() {
        titleView.setPrices(null);
        assertNotNull(titleView.getPrices());
    }
}
