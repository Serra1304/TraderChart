//package candleChart.controller;
//
//import candleChart.view.PriceLineView;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.mockito.Mockito.*;
//
//public class PriceLineControllerTest {
//
//    private PriceLineController priceLineController;
//    private PriceLineView priceLineView;
//
//    @BeforeEach
//    public void setUp() {
//        priceLineView = mock(PriceLineView.class);
//        priceLineController = new PriceLineController(priceLineView);
//    }
//
//    @Test
//    public void testUpdateCursorPosition() {
//        priceLineController.updateCursorPosition(50, 100);
//        verify(priceLineView).setCursorPosition(100);
//    }
//
//    @Test
//    public void testUpdateSizeGridY() {
//        priceLineController.updateGridSize(10.0, 50.0);
//        verify(priceLineView).setSizeGridY(50.0);
//    }
//
//    @Test
//    public void testUpdatePriceRange() {
//        priceLineController.updatePriceRange(100.0, 50.0);
//        verify(priceLineView).setPriceRange(100.0, 50.0);
//    }
//
//    @Test
//    public void testUpdateCurrentPriceLabelVisible() {
//        priceLineController.updateCursorVisible(true);
//        verify(priceLineView).setCurrentPriceLabelVisible(true);
//
//        priceLineController.updateCursorVisible(false);
//        verify(priceLineView).setCurrentPriceLabelVisible(false);
//    }
//}
//
