package candleChart.view;

import candleChart.model.Candle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandleViewTest {

    private CandleView candleView;

    @BeforeEach
    public void setUp() {
        candleView = new CandleView();
    }

    @Test
    public void testSetCandleList() {
        List<Candle> candleList = new ArrayList<>();
        candleList.add(new Candle(LocalDateTime.of(2023, 10, 2, 0, 0), 100, 150, 80, 120));
        candleList.add(new Candle(LocalDateTime.of(2023, 10, 2, 1, 0), 120, 160, 110, 140));

        candleView.setCandleList(candleList);
        assertEquals(candleList, candleView.getCandleList());
    }

    @Test
    public void testSetCandleWidth() {
        int candleWidth = 10;

        candleView.setCandleWidth(candleWidth);
        assertEquals(candleWidth, candleView.getCandleWidth());
    }

    @Test
    public void testSetRelativePosition() {
        int relativePosition = 6;

        candleView.setRelativePosition(relativePosition);
        assertEquals(relativePosition, candleView.getRelativePosition());
    }

    @Test
    public void testSetMaxPrice() {
        double maxPrice = 200.0;
        candleView.setMaxPrice(maxPrice);

        assertEquals(maxPrice, candleView.getMaxPrice(), 0.001);
    }

    @Test
    public void testSetMinPrice() {
        double minPrice = 50.0;
        candleView.setMinPrice(minPrice);

        assertEquals(minPrice, candleView.getMinPrice(), 0.001);
    }
}

