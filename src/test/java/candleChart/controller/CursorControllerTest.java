//package candleChart.controller;
//
//import candleChart.interfaces.ChartObserver;
//import candleChart.view.ChartView;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CursorControllerTest {
//    private CursorController cursorController;
//
//    @Mock
//    private ChartObserver chartObserverMock;
//
//    public CursorControllerTest() {
//    }
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        cursorController = new CursorController(new ChartView());
//    }
//
//    @Test
//    public void testUpdateCandleRelativePosition() {
//        cursorController.updateCandleRelativePosition(5);
//        assertEquals(5, cursorController.getRelativePosition());
//    }
//
//    @Test
//    public void testUpdateObservers() {
//        List<ChartObserver> observerList = new ArrayList<>();
//        observerList.add(chartObserverMock);
//        cursorController.updateObservers(observerList);
//
//        assertEquals(observerList, cursorController.getObservers());
//    }
//
//    @Test
//    public void testSetGlobalCursosVisible() {
//        cursorController.setGlobalCursosVisible(false);
//        assertFalse(cursorController.isGlobalCursosVisible());
//
//        cursorController.setGlobalCursosVisible(true);
//        assertTrue(cursorController.isGlobalCursosVisible());
//    }
//}
