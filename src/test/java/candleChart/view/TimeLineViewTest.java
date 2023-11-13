package candleChart.view;

import candleChart.model.Candle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimeLineViewTest {
    private TimeLineView timeLineView;

    @BeforeEach
    public void setUp() {
        timeLineView = new TimeLineView();
    }

    @AfterEach
    public void tearDown() {
        timeLineView = null;
    }

    @Test
    public void testSetChartWidth() {
        timeLineView.setChartWidth(100);
        assertEquals(100, timeLineView.getChartWidth());
    }

    @Test
    public void testSetSizeGridX() {
        timeLineView.setSizeGridX(10.5);
        assertEquals(10.5, timeLineView.getSizeGridX());
    }

    @Test
    public void testSetCursorX() {
        timeLineView.setCursorX(50);
        JLabel currentTime = (JLabel) timeLineView.getComponent(0);
        assertEquals(50 - (double) currentTime.getWidth() / 2, currentTime.getLocation().getX());
    }

    @Test
    public void testSetCurrentTimeVisible() {
        timeLineView.setCurrentTimeVisible(true);
        JLabel currentTime = (JLabel) timeLineView.getComponent(0);
        assertTrue(currentTime.isVisible());

        timeLineView.setCurrentTimeVisible(false);
        assertFalse(currentTime.isVisible());
    }

    @Test
    public void testSetCurrentTimeText() {
        timeLineView.setCurrentTimeText("12:00");
        JLabel currentTime = (JLabel) timeLineView.getComponent(0);
        assertEquals("12:00", currentTime.getText());
    }

    @Test
    public void testSetRelativePosition() {
        timeLineView.setRelativePosition(5);
        assertEquals(5, timeLineView.getRelativePosition());
    }

    @Test
    public void testSetCandleList() {
        List<Candle> candles = new ArrayList<>();
        candles.add(new Candle(LocalDateTime.of(2023, 10, 2, 0, 0), 100.0, 120.0, 90.0, 110.0));
        timeLineView.setCandleList(candles);
        assertEquals(candles, timeLineView.getCandleList());
    }

    @Test
    public void testSetCandleListWithNull() {
        timeLineView.setCandleList(null);
        assertNotNull(timeLineView.getCandleList());
    }

    @Test
    public void testSetCandleListWithEmptyList() {
        timeLineView.setCandleList(new ArrayList<>());
        assertNotNull(timeLineView.getCandleList());
        assertEquals(0, timeLineView.getCandleList().size());
    }
}
