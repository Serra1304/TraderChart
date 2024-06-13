package candleChart.controller;

import candleChart.charts.candle.CandleChart;
import candleChart.charts.candle.CandleSize;
import candleChart.data.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import candleChart.model.Candle;
import candleChart.charts.candle.CandleView;

public class CandleControllerTest {
    private CandleChart candleChart;
    private CandleView candleView;

    private Buffer buffer;

    @BeforeEach
    public void setUp() {
        ArrayList<Candle> candleList = new ArrayList<>();
        candleList.add(new Candle(LocalDateTime.now(), 1.25, 1.30, 1.20, 1.22));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));
        candleList.add(new Candle(LocalDateTime.now(), 1.15, 1.20, 1.10, 1.12));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));
        candleList.add(new Candle(LocalDateTime.now(), 1.25, 1.30, 1.20, 1.22));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));
        candleList.add(new Candle(LocalDateTime.now(), 1.15, 1.20, 1.10, 1.12));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));

        candleView = Mockito.mock(CandleView.class);
        candleChart = new CandleChart(candleView);

        buffer = spy(new Buffer());
        buffer.addAll(candleList);
    }

    @Test
    public void testSetBuffer_NullBuffer() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            candleChart.setBuffer(null);
        });
        assertEquals("No se permiten valores nulos para el valor 'buffer'.", exception.getMessage());
    }

    @Test
    public void testSetBuffer_ValidBuffer() {
        candleChart.setBuffer(buffer);

        assertEquals(buffer, candleChart.getBuffer());
    }

    @Test
    public void testGetMaxPrice() {
        when(candleView.getWidth()).thenReturn(200);

        candleChart.setBuffer(buffer);
        double maxPrice = buffer.getAll().stream().mapToDouble(Candle::highPrice).max().orElse(0);

        assertEquals(maxPrice, candleChart.getMaxPrice());
    }

    @Test
    public void testGetMinPrice() {
        when(candleView.getWidth()).thenReturn(200);

        candleChart.setBuffer(buffer);
        double minPrice = buffer.getAll().stream().mapToDouble(Candle::lowPrice).min().orElse(0);

        assertEquals(minPrice, candleChart.getMinPrice());
    }

    @Test
    public void testSetCandleSize_NullCandleSize() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            candleChart.setCandleSize(null);
        });
        assertEquals("No se permiten valores nulos para 'candleSize'.", exception.getMessage());
    }

    @Test
    public void testSetCandleSize_ValidCandleSize() {
        CandleSize candleSize = CandleSize.VERY_LARGE;
        candleChart.setCandleSize(candleSize);

        assertEquals(candleSize, candleChart.getCandleSize());
    }

    @Test
    public void testAdvance_OneStep() {
        candleChart = spy(new CandleChart(new CandleView()));
        candleChart.advance();

        verify(candleChart, times(1)).advance(1);
    }

    @Test
    public void testAdvance_NegativeStep() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            candleChart.advance(-2);
        });
        assertEquals("No se permiten valores negativos.", exception.getMessage());
    }

    @Test
    public void testAdvance_ValidStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleChart.setBuffer(buffer);
        candleChart.retrieve(4);
        candleChart.advance(2);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(4,5));
    }

    @Test
    public void testAdvance_OverStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleChart.setBuffer(buffer);
        candleChart.retrieve(4);
        candleChart.advance(20);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(6,7));
    }

    @Test
    public void testRetrieve_OneStep() {
        candleChart = spy(new CandleChart(new CandleView()));
        candleChart.retrieve();

        verify(candleChart, times(1)).retrieve(1);
    }

    @Test
    public void testRetrieve_NegativeStep() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            candleChart.retrieve(-2);
        });
        assertEquals("No se permiten valores negativos.", exception.getMessage());
    }

    @Test
    public void testRetrieve_ValidStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleChart.setBuffer(buffer);
        candleChart.retrieve(4);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(2,3));
    }

    @Test
    public void testRetrieve_OverStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleChart.setBuffer(buffer);
        candleChart.retrieve(20);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(0,1));
    }
}
