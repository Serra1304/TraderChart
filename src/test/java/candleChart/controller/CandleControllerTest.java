package candleChart.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import candleChart.model.Candle;
import candleChart.view.CandleView;

public class CandleControllerTest {
    private CandleController candleController;
    private CandleView candleView;
    private ChartController chartController;

    @BeforeEach
    public void setUp() {
        candleView = Mockito.mock(CandleView.class);
        chartController = Mockito.mock(ChartController.class);
        candleController = new CandleController(candleView);
    }

    @Test
    public void testUpCandleSize() {
        candleController.setCandleWidth(5);

        assertTrue(candleController.upCandleSize());
        assertEquals(7, candleController.getCandleWidth());
        assertEquals(11, candleController.getRelativePosition());
    }
    @Test
    public void testUpCandleSize_Limit() {
        candleController.setCandleWidth(9);

        assertFalse(candleController.upCandleSize());
        assertEquals(9, candleController.getCandleWidth());
        assertEquals(13, candleController.getRelativePosition());
    }

    @Test
    public void testDownCandleSize() {
        candleController.setCandleWidth(7);

        assertTrue(candleController.downCandleSize());
        assertEquals(5, candleController.getCandleWidth());
        assertEquals(9, candleController.getRelativePosition());
    }

    @Test
    public void testDownCandleSize_Limit() {
        candleController.setCandleWidth(3);

        assertFalse(candleController.downCandleSize());
        assertEquals(3, candleController.getCandleWidth());
        assertEquals(7, candleController.getRelativePosition());
    }

    @Test
    public void testSetCandle_WidthInRange() {
        assertTrue(candleController.setCandleWidth(5));
        assertEquals(5, candleController.getCandleWidth());

        assertTrue(candleController.setCandleWidth(6));
        assertEquals(7, candleController.getCandleWidth());
    }

    @Test
    public void testSetCandle_WidthOutRange() {
        assertFalse(candleController.setCandleWidth(2));
        assertFalse(candleController.setCandleWidth(10));
    }

    @Test
    public void testSetCandleBuffer() {
        List<Candle> candleBuffer = new ArrayList<>();
        candleBuffer.add(new Candle(LocalDateTime.of(2023, 10, 2, 0, 0), 10, 20, 5, 15));
        candleBuffer.add(new Candle(LocalDateTime.of(2023, 10, 2, 1, 0), 15, 25, 12, 18));

        candleController.setCandleBuffer(candleBuffer);
        assertEquals(candleBuffer, candleController.getCandleBuffer());
    }
}

