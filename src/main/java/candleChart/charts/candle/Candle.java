package candleChart.charts.candle;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Representa una vela individual en un gráfico de velas, que representa visualmente el movimiento del precio de un
 * valor durante un período de tiempo específico.
 */
public record Candle(LocalDateTime dateTime, double openPrice, double highPrice, double lowPrice, double closePrice) {

    /**
     * Valída los parámetros proporcionados y crea un nuevo objeto `Candle`.
     *
     * @throws InvalidCandleException si la fecha y hora es nula o los precios violan las siguientes condiciones:
     *                              - Todos los precios deben positivos.
     *                              - El precio alto debe ser mayor que los demás precios de la vela.
     *                              - El precio de apertura debe estar entre el precio alto y bajo (inclusive).
     *                              - El precio de cierre debe estar entre el precio alto y bajo (inclusive).
     *                              - El precio bajo debe ser menor que los demás precios de la vela.
     */
    public Candle {
        if (dateTime == null) {
            throw new InvalidCandleException("No se puede pasar una fecha nula como parámetro");
        }
        if (openPrice < 0 || highPrice < 0 || lowPrice < 0 || closePrice < 0) {
            throw new InvalidCandleException("No pueden haber precios negativos");
        }
        if (highPrice < lowPrice || openPrice > highPrice || openPrice < lowPrice || closePrice > highPrice || closePrice < lowPrice) {
            throw new InvalidCandleException("Formato de precios incorrecto");
        }

        // Las asignaciones de campos se manejan automáticamente por el constructor de registro en Java 17.
    }


    /**
     * Devuelve una representación de cadena de la vela en un formato legible por humanos, incluyendo precios de
     * apertura, máximo, mínimo, cierre y fecha/hora.
     *
     * @return Una cadena formateada que representa los datos de la vela.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("u.M.dd HH:mm");  // Formato de fecha a mostrar.

        return "O: " + openPrice +
                "  H: " + highPrice +
                "  L: " + lowPrice +
                "  C: " + closePrice +
                "  D: " + dateTime.format(formatter);
    }
}
