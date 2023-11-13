package candleChart.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandleTest {
    private final LocalDate date = LocalDate.of(2010, 5, 25);
    private final LocalTime time = LocalTime.of(12, 35);
    private final LocalDateTime dateTime = LocalDateTime.of(date, time);
    private final double openPrice = 1.22046;
    private final double highPrice = 1.22921;
    private final double lowPrice = 1.22009;
    private final double closePrice = 1.22532;

    private final Candle candle = new Candle(dateTime, openPrice, highPrice, lowPrice, closePrice);

    @Test
    public void testDateTime() {
        assertEquals(dateTime, candle.dateTime());
    }

    @Test
    public void testGetDate() {
        assertEquals(date, candle.getDate());
    }

    @Test
    public void testGetTime() {
        assertEquals(time, candle.getTime());
    }

    @Test
    public void testOpenPrice() {
        assertEquals(openPrice, candle.openPrice());
    }

    @Test
    public void testHighPrice() {
        assertEquals(highPrice, candle.highPrice());
    }

    @Test
    public void testLowPrice() {
        assertEquals(lowPrice, candle.lowPrice());
    }

    @Test
    public void tesClosePrice() {
        assertEquals(closePrice, candle.closePrice());
    }


}
