package candleChart.view;

import candleChart.charts.util.Cursor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CursorTest {
    private Cursor cursor;

    @BeforeEach
    public void setup() {
        cursor = new Cursor();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, cursor.getLocationX());
        assertEquals(0, cursor.getLocationY());
        assertFalse(cursor.isCursorVisible());
    }

    @Test
    public void testSetCursorLocation_TriggerRepaint() {
        cursor = spy(new Cursor());
        cursor.setCursorLocation(20,20);

        verify(cursor, atLeastOnce()).repaint();
    }

    @Test
    public void testCursorLocation_SetterAndGetter() {
        int locationX = 56;
        int locationY = 22;

        cursor.setCursorLocation(locationX, locationY);

        assertEquals(locationX, cursor.getLocationX());
        assertEquals(locationY, cursor.getLocationY());
    }

    @Test
    public void testSetCursorVisible_TriggerRepaint() {
        cursor = spy(new Cursor());
        cursor.setCursorVisible(true);

        verify(cursor, atLeastOnce()).repaint();
    }

    @Test
    public void testCursorVisible_SetterAndGetter() {
        cursor.setCursorVisible(true);
        assertTrue(cursor.isCursorVisible());

        cursor.setCursorVisible(false);
        assertFalse(cursor.isCursorVisible());
    }
}
