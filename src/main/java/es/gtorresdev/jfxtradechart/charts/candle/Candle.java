package es.gtorresdev.jfxtradechart.charts.candle;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * La clase {@code Candle} representa una vela financiera utilizada en gráficos de velas japonesas.
 * Cada vela contiene información sobre un periodo de tiempo específico, que incluye el precio de apertura,
 * el máximo, el mínimo, el cierre y el volumen de transacciones.
 *
 * <p>Se garantiza que los valores no serán negativos y se valida que los precios estén en el formato correcto:
 * - El valor "high" debe ser mayor o igual que el "low".
 * - Los valores "open" y "close" deben estar dentro del rango de "high" y "low".
 */
public record Candle(@NotNull LocalDateTime dateTime,
                     double open,
                     double high,
                     double low,
                     double close,
                     int volume) {

    /**
     * Constructor que inicializa una nueva instancia de {@code Candle} y asegura que todos los valores sean validos.
     *
     * @param dateTime el momento de la vela (fecha y hora).
     * @param open el precio de apertura.
     * @param high el precio más alto del periodo.
     * @param low el precio más bajo del periodo.
     * @param close el precio de cierre.
     * @param volume el volumen de transacciones durante el periodo.
     * @throws InvalidCandleException si alguno de los valores es negativo o si los valores no están en el formato correcto.
     */
    public Candle {
        validateValues(open, high, low, close, volume);
    }

    /**
     * Valída los valores de la vela.
     * <ul>
     *   <li>Los precios de apertura, máximo, mínimo, cierre y el volumen no pueden ser negativos.</li>
     *   <li>El valor máximo (high) debe ser mayor o igual que el valor mínimo (low).</li>
     *   <li>El valor de apertura (open) debe estar dentro del rango entre el valor máximo (high) y el valor mínimo (low).</li>
     *   <li>El valor de cierre (close) debe estar dentro del rango entre el valor máximo (high) y el valor mínimo (low).</li>
     * </ul>
     *
     * @param open el precio de apertura.
     * @param high el precio más alto del periodo.
     * @param low el precio más bajo del periodo.
     * @param close el precio de cierre.
     * @param volume el volumen de transacciones durante el periodo.
     * @throws InvalidCandleException si alguno de los valores es negativo o si los precios no están en el formato correcto.
     */
    private static void validateValues(double open, double high, double low, double close, int volume) {
        // Verifica que todos los valores sean mayor que cero.
        if (open < 0 || high < 0 || low < 0 || close < 0 || volume < 0) {
            throw new InvalidCandleException("Los valores no pueden ser negativos");
        }

        // Verifica el correcto formato de valores pasado en los parameters.
        if (high < low || open > high || open < low || close > high || close < low) {
            throw new InvalidCandleException("Formato de valores incorrecto");
        }
    }

    /**
     * Devuelve una cadena de texto que contiene la fecha y hora de la vela en formato "dd MMM HH:mm".
     *
     * @return una cadena con la fecha y hora de la vela.
     */
    public @NotNull String getInfo() {
        return this.dateTime().format(DateTimeFormatter.ofPattern("dd MMM HH:mm"));
    }

    /**
     * Devuelve una representación en cadena de la vela con información detallada
     * sobre los precios de apertura, máximo, mínimo, cierre, volumen y fecha.
     *
     * <p>El formato de salida será el siguiente:
     * <pre>
     * O: [open]  H: [high]  L: [low]  C: [close]  V: [volume]  D: [date]
     * </pre>
     *
     * @return una cadena que representa la vela.
     */
    @Override
    public @NotNull String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("u.M.dd HH:mm");  // Formato de fecha a mostrar.
        return String.format("O: %.5f  H: %.5f  L: %.5f  C: %.5f  V: %d  D: %s",
                open, high, low, close, volume, dateTime.format(formatter));
    }
}
