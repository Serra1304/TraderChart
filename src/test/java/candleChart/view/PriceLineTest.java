//package candleChart.view;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class PriceLineTest {
//    PriceLine priceLine;
//
//    @BeforeEach
//    public void Setup() {
//        priceLine = new PriceLine();
//    }
//
//    @Test
//    public void testSetCursorLocation_TriggerRepaint() {
//        priceLine = spy(new PriceLine());
//        priceLine.setCursorLocation(20);
//
//        verify(priceLine, atLeastOnce()).repaint();
//    }
//
//    @Test
//    public void testCursorLocation_GettersAndSetters() {
//        priceLine.setCursorLocation(78);
//
//        assertEquals(78, priceLine.getCursorLocation());
//    }
//
//    @Test
//    public void testSetCursorVisible_TriggerRepaint() {
//        priceLine = spy(new PriceLine());
//        priceLine.setCursorVisible(false);
//
//        verify(priceLine, atLeastOnce()).repaint();
//    }
//
//    @Test
//    public void testCursorVisible_GettersAndSetters() {
//        priceLine.setCursorVisible(true);
//        assertTrue(priceLine.isCursorVisible());
//
//        priceLine.setCursorVisible(false);
//        assertFalse(priceLine.isCursorVisible());
//    }
//
//    @Test
//    public void testSetPriceRange_ValidParameters() {
//        double rangeUp = 1.0052;
//        double rangeDown = 1.0048;
//        priceLine.setPriceRange(rangeUp, rangeDown);
//
//        assertEquals(rangeUp, priceLine.getRangeUp());
//        assertEquals(rangeDown, priceLine.getRangeDown());
//    }
//
//    @Test
//    public void testSetPriceRange_RangeUpLessThanRangeDown() {
//        double rangeUp =  1.0159;
//        double rangeDown = 1.0258;
//
//        IllegalArgumentException exception;
//        exception = assertThrows(IllegalArgumentException.class, () -> {
//            priceLine.setPriceRange(rangeUp, rangeDown);
//        });
//        assertEquals("El rango superior no puede ser menos que el rango inferior.", exception.getMessage());
//    }
//}
//
