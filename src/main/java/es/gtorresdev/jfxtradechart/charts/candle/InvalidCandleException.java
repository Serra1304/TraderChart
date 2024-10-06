package es.gtorresdev.jfxtradechart.charts.candle;


/**
 * La clase {@code InvalidCandleException} es una excepción personalizada que se lanza cuando una vela
 * (Candle) en el gráfico de velas no cumple con los criterios de validación.
 * Esta excepción extiende de {@link RuntimeException}, por lo que es una excepción no verificada
 * que puede ser lanzada durante la ejecución.
 */
public class InvalidCandleException extends RuntimeException{

    /**
     * Crea una nueva instancia de {@code InvalidCandleException} con un mensaje de error específico.
     *
     * @param message el mensaje de error detallado que describe la causa de la excepción
     */
    public InvalidCandleException(String message) {
        super(message);
    }
}
