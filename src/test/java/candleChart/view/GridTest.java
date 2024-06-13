package candleChart.view;

import candleChart.charts.util.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GridTest {
    @Mock
    private Grid grid;

    @BeforeEach
    public void setup() {
        grid = new Grid();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(32, grid.getGridX());
        assertEquals(32, grid.getGridY());
        assertTrue(grid.isGridVisible());
    }

    @Test
    public void testSetGridSize_TriggerRepaint() {
        grid = spy(new Grid());
        grid.setGridSize(80, 80);

        verify(grid, atLeastOnce()).repaint();
    }

    @Test
    public void testGridSize_SetterAndGetter() {
        int gridX = 120;
        int gridY = 80;

        grid.setGridSize(gridX, gridY);

        assertEquals(gridX, grid.getGridX());
        assertEquals(gridY, grid.getGridY());
    }

    @Test
    public void testSetGridVisible_TriggerRepaint() {
        grid = spy(new Grid());
        grid.setGridVisible(true);

        verify(grid, atLeastOnce()).repaint();
    }

    @Test
    public void testGridVisible_SetterAndGetter() {
        grid.setGridVisible(false);
        assertFalse(grid.isGridVisible());

        grid.setGridVisible(true);
        assertTrue(grid.isGridVisible());
    }
}
