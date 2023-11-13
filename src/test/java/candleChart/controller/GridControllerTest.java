package candleChart.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import candleChart.view.ChartView;
import org.mockito.MockitoAnnotations;

public class GridControllerTest {
    private GridController gridController;

    @Mock
    private ChartView chartViewMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gridController = new GridController(chartViewMock);
    }

    @Test
    public void testUpGridSize_UpCandleByFraction() {
        gridController.setCandlesByFraction(3);
        gridController.upGridSize();

        assertEquals(4, gridController.getCandlesByFraction());
    }

    @Test
    public void testDownGridSize_DownCandleByFraction() {
        gridController.setCandlesByFraction(4);
        gridController.downGridSize();

        assertEquals(3, gridController.getCandlesByFraction());
    }

    @Test
    public void testSetGridVisible() {
        when(chartViewMock.isGridVisible()).thenReturn(true).thenReturn(false);

        gridController.setGridVisible(true);
        assertTrue(gridController.isGridVisible());

        gridController.setGridVisible(false);
        assertFalse(gridController.isGridVisible());
    }
}
