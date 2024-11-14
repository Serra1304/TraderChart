package es.gtorresdev.jfxtradechart.componets.axis;

import es.gtorresdev.jfxtradechart.models.Range;

import java.util.List;

/**
 * Interfaz que define la capacidad de distribuir valores dentro de un rango específico en un eje.
 * Implementa la interfaz {@link AxisDistribution} para extender la funcionalidad
 * de distribución de valores en un gráfico.
 *
 * <p>
 * Las clases que implementen esta interfaz deben proporcionar una implementación para la
 * distribución de valores a lo largo de un eje, ajustándose al rango especificado.
 * </p>
 */
public interface RangeDistributable extends AxisDistribution{

    /**
     * Distribuye valores a lo largo del rango especificado en función de la longitud del eje.
     *
     * @param range el objeto {@link Range} que representa los límites del rango (inferior y superior)
     *              dentro del cual se distribuirán los valores.
     * @param axisLength la longitud del eje utilizada para determinar el número de divisiones
     *                   y calcular la distancia entre los valores.
     * @return una lista de valores distribuidos de manera uniforme o no uniforme a lo largo del rango.
     */
    List<Double> rangeDistribute(Range range, int axisLength);
}
