package es.gtorresdev.jfxtradechart.componets.axis;

import java.util.List;

/**
 * La interfaz {@code AxisDistribution} define el contrato para distribuir puntos a lo largo de un eje gráfico.
 * Implementaciones de esta interfaz son responsables de calcular la posición de las divisiones o puntos de referencia
 * en un eje dado, en función de su longitud.
 * <p>
 * Esto es particularmente útil para generar divisiones en escalas lineales, logarítmicas o geométricas, dependiendo
 * de la implementación específica.
 * </p>
 */
public interface AxisDistribution {

    /**
     * Distribuye los puntos a lo largo de un eje con la longitud especificada.
     *
     * @param axisLength la longitud del eje en el que se distribuirán los puntos.
     * @return una lista de enteros que representan las posiciones de cada punto distribuido a lo largo del eje.
     */
    List<Integer> distribute(int axisLength);
}
