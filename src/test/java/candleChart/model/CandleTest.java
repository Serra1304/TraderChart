package candleChart.model;

import candleChart.charts.candle.Candle;
import candleChart.charts.candle.InvalidCandleException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CandleTest {
    private final LocalDateTime dateTime = LocalDateTime.of(2013, 10, 15, 12, 55);
    private final double openPrice = 1.22046;
    private final double highPrice = 1.22921;
    private final double lowPrice = 1.22009;
    private final double closePrice = 1.22532;

    @Test
    public void testConstructor_ValidParameters() {
        Candle candle = new Candle(dateTime, openPrice, highPrice, lowPrice, closePrice);

        assertEquals(dateTime, candle.dateTime());
        assertEquals(openPrice, candle.openPrice());
        assertEquals(highPrice, candle.highPrice());
        assertEquals(lowPrice, candle.lowPrice());
        assertEquals(closePrice, candle.closePrice());
    }

    @Test
    public void testConstructor_NullDateTIme() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(null, openPrice, highPrice, lowPrice, closePrice);
        });
        assertEquals("No se puede pasar una fecha nula como parámetro", exception.getMessage());
    }

    @Test
    public void testConstructor_NegativeOpenPrice() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, -0.5, highPrice, lowPrice, closePrice);
        });
        assertEquals("No pueden haber precios negativos", exception.getMessage());
    }

    @Test
    public void testConstructor_OpenPriceInvalidTopRank() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime,highPrice + 0.00001, highPrice, lowPrice, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception.getMessage());
    }

    @Test
    public void testConstructor_OpenPriceInvalidLowerRank() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, lowPrice - 0.00001, highPrice, lowPrice, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception.getMessage());
    }

    @Test
    public void testConstructor_NegativeHighPrice() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, -0.1, lowPrice, closePrice);
        });
        assertEquals("No pueden haber precios negativos", exception.getMessage());
    }

    @Test
    public void testConstructor_HighPriceInvalidTopRank() {
        InvalidCandleException exception1;
        exception1 = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice,openPrice - 0.00001, lowPrice, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception1.getMessage());

        InvalidCandleException exception2;
        exception2 = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice,closePrice - 0.00001, lowPrice, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception2.getMessage());
    }

    @Test
    public void testConstructor_HighPriceInvalidLowerRank() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice,lowPrice - 0.00001, lowPrice, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception.getMessage());
    }

    @Test
    public void testConstructor_NegativeLowPrice() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, highPrice,-0.1, closePrice);
        });
        assertEquals("No pueden haber precios negativos", exception.getMessage());
    }

    @Test
    public void testConstructor_LowPriceInvalidTopRank() {
        InvalidCandleException exception1;
        exception1 = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, highPrice,highPrice + 0.00001, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception1.getMessage());
    }

    @Test
    public void testConstructor_LowPriceInvalidLowerRank() {
        InvalidCandleException exception1;
        exception1 = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, highPrice,openPrice + 0.00001, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception1.getMessage());

        InvalidCandleException exception2;
        exception2 = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, highPrice,closePrice + 0.00001, closePrice);
        });
        assertEquals("Formato de precios incorrecto", exception2.getMessage());
    }

    @Test
    public void testConstructor_NegativeClosePrice() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, highPrice, highPrice, lowPrice,-0.1);
        });
        assertEquals("No pueden haber precios negativos", exception.getMessage());
    }

    @Test
    public void testConstructor_ClosePriceInvalidTopRank() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, highPrice, lowPrice,highPrice + 0.00001);
        });
        assertEquals("Formato de precios incorrecto", exception.getMessage());
    }

    @Test
    public void testConstructor_ClosePriceInvalidLowerRank() {
        InvalidCandleException exception;
        exception = assertThrows(InvalidCandleException.class, () -> {
            new Candle(dateTime, openPrice, highPrice, lowPrice,lowPrice - 0.00001);
        });
        assertEquals("Formato de precios incorrecto", exception.getMessage());
    }
}
