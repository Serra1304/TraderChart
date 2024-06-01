package candleChart;

import candleChart.controller.CandleSize;
import candleChart.controller.ChartController;
import candleChart.data.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraderChartTest {

    @Mock
    private ChartController chartControllerMock;

    private TraderChart traderChart;
    private Buffer buffer;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        traderChart = new TraderChart();
        buffer = new Buffer();

        // Se inyecta dependencias de campos privados en traderChart por reflexion.
        Field chartControllerField = TraderChart.class.getDeclaredField("chartController");
        chartControllerField.setAccessible(true);
        chartControllerField.set(traderChart, chartControllerMock);
    }

    @Test
    public void testSetBuffer_NullBuffer() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> traderChart.setBuffer(null));
        assertEquals("No se permite un buffer con valor nulo", exception.getMessage());
    }

    @Test
    public void testSetBuffer_TriggerUpdate() {
        traderChart = spy(new TraderChart());
        traderChart.setBuffer(buffer);

        verify(traderChart, times(1)).update();
    }

    @Test
    public void testSetBuffer_ValidBuffer() {
        traderChart.setBuffer(buffer);

        assertEquals(buffer, traderChart.getBuffer());
    }

    @Test
    public void testUpdate_TriggerChartController() {
        traderChart.update();

        verify(chartControllerMock, times(1)).setBuffer(any());
    }

    @Test
    public void testSetGridVisible_TriggerChartController() {
        traderChart.setGridVisible(true);
        verify(chartControllerMock, times(1)).setGridVisible(true);

        traderChart.setGridVisible(false);
        verify(chartControllerMock, times(1)).setGridVisible(false);
    }

    @Test
    public void testIsGridVisible() {
        when(chartControllerMock.isGridVisible()).thenReturn(true);
        assertTrue(traderChart.isGridVisible());

        when(chartControllerMock.isGridVisible()).thenReturn(false);
        assertFalse(traderChart.isGridVisible());
    }

    @Test
    public void testSetCursorVisible_TriggerChartController() {
        traderChart.setCursorVisible(true);
        verify(chartControllerMock, times(1)).setCursorVisible(true);

        traderChart.setCursorVisible(false);
        verify(chartControllerMock, times(1)).setCursorVisible(false);
    }

    @Test
    public void testIsCursorVisible() {
        when(chartControllerMock.isCursorVisible()).thenReturn(true);
        assertTrue(traderChart.isCursorVisible());

        when(chartControllerMock.isCursorVisible()).thenReturn(false);
        assertFalse(traderChart.isCursorVisible());
    }

    @Test
    public void testSetCandleSize_TriggerChartController() {
        traderChart.setCandleSize(CandleSize.VERY_SMALL);
        verify(chartControllerMock, times(1)).setCandleSize(CandleSize.VERY_SMALL);

        traderChart.setCandleSize(CandleSize.SMALL);
        verify(chartControllerMock, times(1)).setCandleSize(CandleSize.SMALL);

        traderChart.setCandleSize(CandleSize.LARGE);
        verify(chartControllerMock, times(1)).setCandleSize(CandleSize.LARGE);

        traderChart.setCandleSize(CandleSize.VERY_LARGE);
        verify(chartControllerMock, times(1)).setCandleSize(CandleSize.VERY_LARGE);
    }

    @Test
    public void testSetCandleSize_NullCandleSize() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> traderChart.setCandleSize(null));
        assertEquals("No se permiten valores nulos para la propiedad 'candleSize", exception.getMessage());
    }

    @Test
    public void testGetCandleSize() {
        when(chartControllerMock.getCandleSize()).thenReturn(CandleSize.VERY_SMALL);
        assertEquals(CandleSize.VERY_SMALL, traderChart.getCandleSize());

        when(chartControllerMock.getCandleSize()).thenReturn(CandleSize.SMALL);
        assertEquals(CandleSize.SMALL, traderChart.getCandleSize());

        when(chartControllerMock.getCandleSize()).thenReturn(CandleSize.LARGE);
        assertEquals(CandleSize.LARGE, traderChart.getCandleSize());

        when(chartControllerMock.getCandleSize()).thenReturn(CandleSize.VERY_LARGE);
        assertEquals(CandleSize.VERY_LARGE, traderChart.getCandleSize());
    }

    @Test
    public void testSetSymbol_NullSymbol() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> traderChart.setSymbol(null));
        assertEquals("El valor proporcionado para 'symbol' es nulo.", exception.getMessage());
    }

    @Test
    public void testSetSymbol_TriggerChartController() {
        traderChart.setSymbol("EUR/USD");
        verify(chartControllerMock, times(1)).setSymbol("EUR/USD");
    }

    @Test
    public void testGetSymbol() {
        when(chartControllerMock.getSymbol()).thenReturn("EUR/USD");

        traderChart.setSymbol("EUR/USD");
        assertEquals("EUR/USD", traderChart.getSymbol());
    }

    @Test
    public void testAdvance_OneSteps() {
        traderChart.advance();
        verify(chartControllerMock, times(1)).advance();
    }

    @Test
    public void testAdvance_MultiSteps() {
        traderChart.advance(7);
        verify(chartControllerMock, times(1)).advance(7);
    }

    @Test
    public void testAdvance_NegativeSteps() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> traderChart.advance(-5));
        assertEquals("No se permiten valores negativos para la propiedad 'steps'.", exception.getMessage());
    }

    @Test
    public void testRetrieve_OneSteps() {
        traderChart.retrieve();
        verify(chartControllerMock, times(1)).retrieve();
    }

    @Test
    public void testRetrieve_MultiRetrieve() {
        traderChart.retrieve(4);
        verify(chartControllerMock, times(1)).retrieve(4);
    }

    @Test
    public void testRetrieve_NegativeSteps() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> traderChart.retrieve(-3));
        assertEquals("No se permiten valores negativos para la propiedad 'steps'.", exception.getMessage());
    }
}
