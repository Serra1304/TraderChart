package candleChart.view;

import candleChart.model.Candle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TimeLineTest {
    private TimeLine timeLine;

    @BeforeEach
    public void setup() {
        timeLine = new TimeLine();
    }

    @Test
    public void testSetCursorLocation_TriggerRepaint() {
        timeLine = spy(new TimeLine());
        timeLine.setCursorLocation(30);

        verify(timeLine, atLeastOnce()).repaint();
    }

    @Test
    public void testCursorLocation_GettersAndSetters() {
        int cursorLocationX = 50;
        timeLine.setCursorLocation(cursorLocationX);

        assertEquals(cursorLocationX, timeLine.getCursorLocationX());
    }

    @Test
    public void testSetCursorVisible_TriggerRepaint() {
        timeLine = spy(new TimeLine());
        timeLine.setCursorVisible(false);

        verify(timeLine, atLeastOnce()).repaint();
    }

    @Test
    public void testCursorVisible_GettersAndSetters() {
        timeLine.setCursorVisible(true);
        assertTrue(timeLine.isCursorVisible());

        timeLine.setCursorVisible(false);
        assertFalse(timeLine.isCursorVisible());
    }

    @Test
    public void testSetCandleList_NullCandleList() {
        NullPointerException exception;
        exception = assertThrows(NullPointerException.class, () -> {
            timeLine.setCandleList(null);
        });
        assertEquals("No se permiten valores nulos para 'candleList'", exception.getMessage());
    }

    @Test
    public void testSetCandleList_TriggerRepaint() {
        timeLine = spy(new TimeLine());
        timeLine.setCandleList(new ArrayList<>());

        verify(timeLine, atLeastOnce()).repaint();
    }

    @Test
    public void testSetCandleList_ValidCandleList() {
        ArrayList<Candle> candleList = new ArrayList<>();
        candleList.add(new Candle(LocalDateTime.now(), 1.25, 1.30, 1.20, 1.22));
        candleList.add(new Candle(LocalDateTime.now(), 2.25, 2.30, 2.20, 2.22));

        timeLine.setCandleList(candleList);

        assertEquals(candleList, timeLine.getCandleList());
    }

    @Test
    public void testSetRelativePosition_ParameterLessThanOrEqualToZero() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            timeLine.setRelativePosition(0);
        });
        assertEquals("No se permiten valores inferiores o iguales a 0", exception.getMessage());
    }

    @Test
    public void testSetRelativePosition_TriggerRepaint() {
        timeLine = spy(new TimeLine());
        timeLine.setRelativePosition(10);

        verify(timeLine, atLeastOnce()).repaint();
    }

    @Test
    public void testSetRelativePosition_ValidRelativePosition() {
        timeLine.setRelativePosition(40);

        assertEquals(40, timeLine.getRelativePosition());
    }
}
