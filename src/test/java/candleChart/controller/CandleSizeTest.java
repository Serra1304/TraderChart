package candleChart.controller;


import candleChart.charts.candle.CandleSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CandleSizeTest {

    @Test
    public void testVerySmall() {
        CandleSize candle = CandleSize.VERY_SMALL;
        assertEquals(1, candle.getIndex());
        assertEquals(4, candle.getRelativePosition());
        assertEquals(3, candle.getCandleWidth());
    }

    @Test
    public void testSmall() {
        CandleSize candle = CandleSize.SMALL;
        assertEquals(2, candle.getIndex());
        assertEquals(8, candle.getRelativePosition());
        assertEquals(5, candle.getCandleWidth());
    }

    @Test
    public void testLarge() {
        CandleSize candle = CandleSize.LARGE;
        assertEquals(3, candle.getIndex());
        assertEquals(16, candle.getRelativePosition());
        assertEquals(11, candle.getCandleWidth());
    }

    @Test
    public void testVeryLarge() {
        CandleSize candle = CandleSize.VERY_LARGE;
        assertEquals(4, candle.getIndex());
        assertEquals(32, candle.getRelativePosition());
        assertEquals(19, candle.getCandleWidth());
    }

    @Test
    public void testGetByIndex_VerySmall() {
        CandleSize size = CandleSize.getByIndex(1);
        assertEquals(CandleSize.VERY_SMALL, size);
    }

    @Test
    public void testGetByIndex_Small() {
        CandleSize size = CandleSize.getByIndex(2);
        assertEquals(CandleSize.SMALL, size);
    }

    @Test
    public void testGetByIndex_Large() {
        CandleSize size = CandleSize.getByIndex(3);
        assertEquals(CandleSize.LARGE, size);
    }

    @Test
    public void testGetByIndex_VeryLarge() {
        CandleSize size = CandleSize.getByIndex(4);
        assertEquals(CandleSize.VERY_LARGE, size);
    }

    @Test
    public void testGetByIndex_InvalidIndex() {
        CandleSize size = CandleSize.getByIndex(5);
        assertNull(size);
    }
}
