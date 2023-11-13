package candleChart.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class that represents a candle on a financial chart.
 *
 * @param dateTime Date and time associated with the candle.
 * @param openPrice Opening price of the candle.
 * @param highPrice Highest price of the candle.
 * @param lowPrice Lowest price of the candle.
 * @param closePrice Closing price of the candle.
 */
public record Candle(LocalDateTime dateTime, double openPrice, double highPrice, double lowPrice, double closePrice) {

    /**
     * Gets the date associated with the candle.
     *
     * @return The date of the candle.
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Gets the time associated with the candle.
     *
     * @return The time of the candle.
     */
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
