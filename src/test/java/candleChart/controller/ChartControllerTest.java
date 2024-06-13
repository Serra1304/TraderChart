package candleChart.controller;

import candleChart.charts.candle.CandleSize;
import candleChart.data.Buffer;
import candleChart.model.Candle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChartControllerTest {

    private Buffer buffer;
    private ChartController chartController;

    @BeforeEach
    public void setup() {
        chartController = new ChartController(new JPanel());

        ArrayList<Candle> candleList = new ArrayList<>();
        candleList.add(new Candle(LocalDateTime.now(), 1.25, 1.30, 1.20, 1.22));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));
        candleList.add(new Candle(LocalDateTime.now(), 1.15, 1.20, 1.10, 1.12));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));
        candleList.add(new Candle(LocalDateTime.now(), 1.25, 1.30, 1.20, 1.22));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));
        candleList.add(new Candle(LocalDateTime.now(), 1.15, 1.20, 1.10, 1.12));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));

        buffer = new Buffer();
        buffer.addAll(candleList);
    }

    @Test
    public void testSetBuffer_NullBuffer() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            chartController.setBuffer(null);
        });
        assertEquals("No se permiten valores nulos para el valor 'buffer'.", exception.getMessage());
    }

    @Test
    public void testSetBuffer_ValidBuffer() {
        chartController.setBuffer(buffer);

        assertEquals(buffer, chartController.getBuffer());
    }

    @Test
    public void testSetGridVisible() {
        chartController.setGridVisible(true);
        assertTrue(chartController.isGridVisible());

        chartController.setGridVisible(false);
        assertFalse(chartController.isGridVisible());
    }

    @Test
    public void testSetCursorVisible() {
        chartController.setCursorVisible(true);
        assertTrue(chartController.isCursorVisible());

        chartController.setCursorVisible(false);
        assertFalse(chartController.isCursorVisible());
    }

    @Test
    public void testSetCandleSize_NullCandleSize() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            chartController.setCandleSize(null);
        });

        assertEquals("La propiedad 'candleSize' no puede ser nula", exception.getMessage());
    }

    @Test
    public void testSetCandleSize_ValidCandleSize() {
        chartController.setCandleSize(CandleSize.VERY_SMALL);
        assertEquals(CandleSize.VERY_SMALL, chartController.getCandleSize());

        chartController.setCandleSize(CandleSize.SMALL);
        assertEquals(CandleSize.SMALL, chartController.getCandleSize());

        chartController.setCandleSize(CandleSize.VERY_LARGE);
        assertEquals(CandleSize.VERY_LARGE, chartController.getCandleSize());

        chartController.setCandleSize(CandleSize.LARGE);
        assertEquals(CandleSize.LARGE, chartController.getCandleSize());
    }

    @Test
    public void testSetSymbol() {
        chartController.setSymbol("EUR/USD");
        assertEquals("EUR/USD", chartController.getSymbol());

        chartController.setSymbol("IBEX35");
        assertEquals("IBEX35", chartController.getSymbol());
    }
}
