package candleChart.controller;

import candleChart.data.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import candleChart.model.Candle;
import candleChart.view.CandleView;

public class CandleControllerTest {
    private CandleController candleController;
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
        candleController = new CandleController(candleView);

        buffer = spy(new Buffer());
        buffer.addAll(candleList);
    }

    @Test
    public void testSetBuffer_NullBuffer() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            candleController.setBuffer(null);
        });
        assertEquals("No se permiten valores nulos para el valor 'buffer'.", exception.getMessage());
    }

    @Test
    public void testSetBuffer_ValidBuffer() {
        candleController.setBuffer(buffer);

        assertEquals(buffer, candleController.getBuffer());
    }

    @Test
    public void testGetMaxPrice() {
        when(candleView.getWidth()).thenReturn(200);

        candleController.setBuffer(buffer);
        double maxPrice = buffer.getAll().stream().mapToDouble(Candle::highPrice).max().orElse(0);

        assertEquals(maxPrice, candleController.getMaxPrice());
    }

    @Test
    public void testGetMinPrice() {
        when(candleView.getWidth()).thenReturn(200);

        candleController.setBuffer(buffer);
        double minPrice = buffer.getAll().stream().mapToDouble(Candle::lowPrice).min().orElse(0);

        assertEquals(minPrice, candleController.getMinPrice());
    }

    @Test
    public void testSetCandleSize_NullCandleSize() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            candleController.setCandleSize(null);
        });
        assertEquals("No se permiten valores nulos para 'candleSize'.", exception.getMessage());
    }

    @Test
    public void testSetCandleSize_ValidCandleSize() {
        CandleSize candleSize = CandleSize.VERY_LARGE;
        candleController.setCandleSize(candleSize);

        assertEquals(candleSize, candleController.getCandleSize());
    }

    @Test
    public void testAdvance_OneStep() {
        candleController = spy(new CandleController(new CandleView()));
        candleController.advance();

        verify(candleController, times(1)).advance(1);
    }

    @Test
    public void testAdvance_NegativeStep() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            candleController.advance(-2);
        });
        assertEquals("No se permiten valores negativos.", exception.getMessage());
    }

    @Test
    public void testAdvance_ValidStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleController.setBuffer(buffer);
        candleController.retrieve(4);
        candleController.advance(2);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(4,5));
    }

    @Test
    public void testAdvance_OverStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleController.setBuffer(buffer);
        candleController.retrieve(4);
        candleController.advance(20);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(6,7));
    }

    @Test
    public void testRetrieve_OneStep() {
        candleController = spy(new CandleController(new CandleView()));
        candleController.retrieve();

        verify(candleController, times(1)).retrieve(1);
    }

    @Test
    public void testRetrieve_NegativeStep() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            candleController.retrieve(-2);
        });
        assertEquals("No se permiten valores negativos.", exception.getMessage());
    }

    @Test
    public void testRetrieve_ValidStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleController.setBuffer(buffer);
        candleController.retrieve(4);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(2,3));
    }

    @Test
    public void testRetrieve_OverStep() {
        when(candleView.getWidth()).thenReturn(10);
        candleController.setBuffer(buffer);
        candleController.retrieve(20);

        verify(candleView, atLeastOnce()).setCandleList(buffer.getAll().subList(0,1));
    }
}
