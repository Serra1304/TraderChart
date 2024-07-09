package candleChart.controller;


import candleChart.charts.base.ElementDimension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ElementDimensionTest {

    @Test
    public void testVerySmall() {
        ElementDimension candle = ElementDimension.VERY_SMALL;
        assertEquals(1, candle.getIndex());
        assertEquals(4, candle.getRelativePosition());
        assertEquals(3, candle.getWidth());
    }

    @Test
    public void testSmall() {
        ElementDimension candle = ElementDimension.SMALL;
        assertEquals(2, candle.getIndex());
        assertEquals(8, candle.getRelativePosition());
        assertEquals(5, candle.getWidth());
    }

    @Test
    public void testLarge() {
        ElementDimension candle = ElementDimension.LARGE;
        assertEquals(3, candle.getIndex());
        assertEquals(16, candle.getRelativePosition());
        assertEquals(11, candle.getWidth());
    }

    @Test
    public void testVeryLarge() {
        ElementDimension candle = ElementDimension.VERY_LARGE;
        assertEquals(4, candle.getIndex());
        assertEquals(32, candle.getRelativePosition());
        assertEquals(19, candle.getWidth());
    }

    @Test
    public void testGetByIndex_VerySmall() {
        ElementDimension size = ElementDimension.getByIndex(1);
        assertEquals(ElementDimension.VERY_SMALL, size);
    }

    @Test
    public void testGetByIndex_Small() {
        ElementDimension size = ElementDimension.getByIndex(2);
        assertEquals(ElementDimension.SMALL, size);
    }

    @Test
    public void testGetByIndex_Large() {
        ElementDimension size = ElementDimension.getByIndex(3);
        assertEquals(ElementDimension.LARGE, size);
    }

    @Test
    public void testGetByIndex_VeryLarge() {
        ElementDimension size = ElementDimension.getByIndex(4);
        assertEquals(ElementDimension.VERY_LARGE, size);
    }

    @Test
    public void testGetByIndex_InvalidIndex() {
        ElementDimension size = ElementDimension.getByIndex(5);
        assertNull(size);
    }
}
