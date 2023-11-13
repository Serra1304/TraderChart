package candleChart.controller;

import candleChart.interfaces.ChartObserver;
import candleChart.model.Candle;
import candleChart.view.ChartView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChartControllerTest {
    private ChartController chartController;

    @Mock
    private ChartView chartViewMock;

    @Mock
    private ChartObserver chartObserverMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chartController = new ChartController(chartViewMock);
    }

    @Test void testAddObserverCallsUpdateObservers() {
        chartController.addObserver(chartObserverMock);

        verify(chartObserverMock).updateObservers(Mockito.anyList());
    }

    @Test
    public void testAddObserver_AddObserverToList() {
        chartController.addObserver(chartObserverMock);

        List<ChartObserver> observerList = chartController.getObservers();
        assertTrue(observerList.contains(chartObserverMock));
    }

    @Test
    public void testRemoveObserver_CallUpdateObservers() {
        chartController.addObserver(chartObserverMock);
        chartController.removeObserver(chartObserverMock);

        verify(chartObserverMock, Mockito.times(1)).updateObservers(Mockito.anyList());
    }

    @Test
    public void testRemovedObserver_RemoveObserverToList() {
        chartController.addObserver(chartObserverMock);
        chartController.removeObserver(chartObserverMock);

        List<ChartObserver> observerList = chartController.getObservers();
        assertFalse(observerList.contains(chartObserverMock));
    }

    @Test
    public void testSetCursorVisible_SetCorrectValue() {
        chartController.setCursorVisible(true);
        assertTrue(chartController.isCursorVisible());

        chartController.setCursorVisible(false);
        assertFalse(chartController.isCursorVisible());
    }

    @Test
    public void testSetGridVisible_SetCorrectValue() {
        when(chartViewMock.isGridVisible()).thenReturn(true).thenReturn(false);

        chartController.setGridVisible(true);
        assertTrue(chartController.isGridVisible());

        chartController.setGridVisible(false);
        assertFalse(chartController.isGridVisible());
    }

    @Test
    public void testSetGridVisible_CallChartViewGridVisible() {
        chartController.setGridVisible(true);
        verify(chartViewMock).setGridVisible(eq(true));

        chartController.setGridVisible(false);
        verify(chartViewMock).setGridVisible(eq(false));
    }

    @Test
    public void testSetCandleBuffer() {
        List<Candle> candleList = new ArrayList<>();
        candleList.add(new Candle(LocalDateTime.of(2023, 10, 2, 0, 0),1.04798,1.048,1.0477,1.0478));
        candleList.add(new Candle(LocalDateTime.of(2023, 10, 2, 23 ,59),1.04804,1.04804,1.0479,1.04798));
        candleList.add(new Candle(LocalDateTime.of(2023, 10, 2, 23 ,58),1.048,1.04805,1.0478,1.04804));
        candleList.add(new Candle(LocalDateTime.of(2023, 10, 2, 23 ,57),1.04805,1.04815,1.0479,1.04802));
        chartController.setCandleBuffer(candleList);

        assertEquals(candleList, chartController.getCandleBuffer());
    }
}
