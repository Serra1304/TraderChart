package es.gtorresdev.jfxtradechart.componets.axis;

/**
 * Enumeración que representa la alineación de un eje en un gráfico.
 * Define si la distribución de los valores en el eje debe comenzar desde el inicio o desde el final.
 *
 * <p>
 * Las opciones disponibles son:
 * </p>
 * <ul>
 *   <li>{@link #START} - Alinea la distribución de los valores desde el inicio del eje.</li>
 *   <li>{@link #END} - Alinea la distribución de los valores desde el final del eje.</li>
 * </ul>
 *
 * Esta enumeración se utiliza en configuraciones donde es necesario especificar
 * la alineación de los valores generados en un eje, permitiendo flexibilidad
 * en la presentación visual de los datos.
 */
public enum AxisAlignment {
    START, END
}
