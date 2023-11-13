package candleChart.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChartViewTest {
    private ChartView chartView;

    @BeforeEach
    public void setUp() {
        chartView = new ChartView();
    }

    @Test
    public void testDefaultCursorVisibility() {
        assertFalse(chartView.isCursorVisible());
    }

    @Test
    public void testSetCursorVisible() {
        chartView.setCursorVisible(true);
        assertTrue(chartView.isCursorVisible());

        chartView.setCursorVisible(false);
        assertFalse(chartView.isCursorVisible());
    }

    @Test
    public void testDefaultGridVisibility() {
        assertTrue(chartView.isGridVisible());
    }

    @Test
    public void testSetGridVisible() {
        chartView.setGridVisible(true);
        assertTrue(chartView.isGridVisible());

        chartView.setGridVisible(false);
        assertFalse(chartView.isGridVisible());
    }

    @Test
    public void testSetCursor() {
        chartView.setCursor(10, 20);
        assertEquals(10, chartView.getCursorX());
        assertEquals(20, chartView.getCursorY());
    }

    @Test
    public void testSetGridSize() {
        chartView.setGridSize(10.0, 20.0);
        assertEquals(10.0, chartView.getGridXSize());
        assertEquals(20.0, chartView.getGridYSize());
    }
}
