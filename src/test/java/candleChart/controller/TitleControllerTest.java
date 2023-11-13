package candleChart.controller;

import candleChart.model.Candle;
import candleChart.view.TitleView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TitleControllerTest {
    private TitleView titleView;
    private TitleController titleController;

    @BeforeEach
    public void setUp() {
        titleView = Mockito.mock(TitleView.class);
        titleController = new TitleController(titleView);
    }

    @Test
    public void testSetChartTitle() {
        titleController.setChartTitle("Test Chart Title");
        Mockito.verify(titleView).setTitle("Test Chart Title");
    }

    @Test
    public void testGetChartTitle() {
        Mockito.when(titleView.getTitle()).thenReturn("Test Chart Title");
        String chartTitle = titleController.getChartTitle();
        assertEquals("Test Chart Title", chartTitle);
    }

    @Test
    public void testUpdateCandleWithNonNullCandle() {
        Candle candle = new Candle(LocalDateTime.of(2023, 10, 2, 0, 0),1.04798,1.048,1.0477,1.0478);
        titleController.updateCursorCandle(candle);
        Mockito.verify(titleView).setVisiblePrice(true);
        Mockito.verify(titleView).setPrices("O: 1.04798   H: 1.048   L: 1.0477   C: 1.0478");
    }

    @Test
    public void testUpdateCandleWithNullCandle() {
        titleController.updateCursorCandle(null);
        Mockito.verify(titleView).setVisiblePrice(false);
    }

    @Test
    public void testUpdateVisibility() {
        titleController.updateCursorVisible(true);
        Mockito.verify(titleView).setVisiblePrice(true);

        titleController.updateCursorVisible(false);
        Mockito.verify(titleView).setVisiblePrice(false);
    }
}
