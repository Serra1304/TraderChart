//package candleChart.controller;
//
//import candleChart.model.Candle;
//import candleChart.view.TimeLineView;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//public class TimeLineControllerTest {
//
//    private TimeLineController timeLineController;
//    private TimeLineView timeLineView;
//
//    @BeforeEach
//    public void setUp() {
//        timeLineView = mock(TimeLineView.class);
//        timeLineController = new TimeLineController(timeLineView);
//    }
//
//    @Test
//    public void testUpdateChartSize() {
//        timeLineController.updateChartSize(100, 200);
//        verify(timeLineView).setChartWidth(100);
//    }
//
//    @Test
//    public void testUpdateGrid() {
//        timeLineController.updateGridSize(10.5, 20.5);
//        verify(timeLineView).setSizeGridX(10.5);
//    }
//
//    @Test
//    public void testUpdateCursor() {
//        timeLineController.updateCursorPosition(50, 30);
//        verify(timeLineView).setCursorX(50);
//    }
//
//    @Test
//    public void testUpdateCursorVisible() {
//        timeLineController.updateCursorVisible(true);
//        verify(timeLineView).setCurrentTimeVisible(true);
//    }
//
//    @Test
//    public void testUpdateCandleWithNonNullCandle() {
//        Candle candle = new Candle(LocalDateTime.of(2023, 10, 2, 10, 0), 50.0, 60.0, 40.0, 55.0);
//        timeLineController.updateCursorCandle(candle);
//        verify(timeLineView).setCurrentTimeVisible(true);
//        verify(timeLineView).setCurrentTimeText("10:00");
//    }
//
//    @Test
//    public void testUpdateCandleWithNullCandle() {
//        timeLineController.updateCursorCandle(null);
//        verify(timeLineView).setCurrentTimeVisible(false);
//    }
//
//    @Test
//    public void testUpdateCandleList() {
//        List<Candle> candleList = List.of(new Candle(LocalDateTime.of(2023, 10, 2, 0, 0), 50.0, 60.0, 40.0, 55.0));
//        int relativePosition = 3;
//        timeLineController.updateCandleList(candleList);
//        verify(timeLineView).setCandleList(candleList);
//        //verify(timeLineView).setRelativePosition(relativePosition);
//    }
//}
//
