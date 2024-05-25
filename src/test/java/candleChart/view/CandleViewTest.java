package candleChart.view;

import candleChart.controller.CandleSize;
import candleChart.model.Candle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CandleViewTest {

    private  CandleView candleView;

    @BeforeEach
    public void setup() {
        candleView = new CandleView();
    }

    @Test
    public void testSetCandleList_TriggerRepaint() {
        candleView = spy(new CandleView());
        candleView.setCandleList(new ArrayList<>());

        verify(candleView, atLeastOnce()).repaint();
    }

    @Test
    public void testSetCandleList_NullCandleList() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            candleView.setCandleList(null);
        });
        assertEquals("No se permiten valores nulos", exception.getMessage());
    }

    @Test
    public void testSetCandleList_ValidCandleList() {
        ArrayList<Candle> candleList = new ArrayList<>();
        candleList.add(new Candle(LocalDateTime.now(), 1.25, 1.30, 1.20, 1.22));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));

        candleView.setCandleList(candleList);

        assertEquals(candleList, candleView.getCandleList());
    }

    @Test
    public void testSetCandleSize_TriggerRepaint() {
        candleView = spy(new CandleView());

        candleView.setCandleSize(CandleSize.VERY_SMALL);

        verify(candleView, atLeastOnce()).repaint();
    }

    @Test
    public void testSetCandleSize_NullCandleSize() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            candleView.setCandleSize(null);
        });
        assertEquals("No se permiten valores nulos para 'candleSize'.", exception.getMessage());
    }

    @Test
    public void testSetCandleSize_ValidCandleSize() {
        CandleSize candleSize = CandleSize.VERY_LARGE;
        candleView.setCandleSize(candleSize);

        assertEquals(candleSize, candleView.getCandleSize());
    }

    @Test
    public void testSetPriceRange_RangeUpLessThanRangeDown() {
        double rangeUp = 1.0003;
        double rangeDown = 1.0025;

        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            candleView.setPriceRange(rangeUp, rangeDown);
        });
        assertEquals("El rango superior no puede ser menos que el rango inferior.", exception.getMessage());
    }

    @Test
    public void testSetPriceRange_TriggerRepaint() {
        double rangeUp = 1.00078;
        double rangeDown = 1.00023;

        candleView = spy(new CandleView());
        candleView.setPriceRange(rangeUp, rangeDown);

        verify(candleView, atLeastOnce()).repaint();
    }

    @Test
    public void testSetPriceRange_ValidRange() {
        double rangeUp = 1.00078;
        double rangeDown = 1.00023;

        candleView.setPriceRange(rangeUp, rangeDown);

        assertEquals(rangeUp, candleView.getRangeUp());
        assertEquals(rangeDown, candleView.getRangeDown());
    }
}
