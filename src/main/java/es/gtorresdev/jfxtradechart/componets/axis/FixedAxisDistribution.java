package es.gtorresdev.jfxtradechart.componets.axis;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una distribución fija de valores en un eje.
 * Los valores se distribuyen de manera uniforme según un tamaño de intervalo específico.
 */
public class FixedAxisDistribution implements AxisDistribution{
    private static final double DEFAULT_INTERVAL_SIZE = 32;

    private double intervalSize;

    /**
     * Crea una nueva instancia de FixedAxisDistribution
     * con un tamaño de intervalo predeterminado.
     */
    public FixedAxisDistribution() {
        this(DEFAULT_INTERVAL_SIZE);
    }

    /**
     * Crea una nueva instancia de FixedAxisDistribution
     * con el tamaño de intervalo especificado.
     *
     * @param intervalSize el tamaño del intervalo para la distribución
     */
    public FixedAxisDistribution(final double intervalSize) {
        this.intervalSize = intervalSize;
    }

    /**
     * Distribuye los valores a lo largo de la longitud del eje
     * utilizando el tamaño de intervalo especificado.
     *
     * @param length la longitud total del eje
     * @return una lista de valores distribuidos a lo largo del eje
     */
    @Override
    public List<Integer> distribute(final int length) {
        return generateDistributionValues(length);
    }

    /**
     * Genera los valores de distribución a partir de la longitud dada.
     *
     * @param length la longitud total del eje
     * @return una lista de valores de distribución
     */
    private List<Integer> generateDistributionValues(final int length) {
        List<Integer> distributionValues = new ArrayList<>();

        for(double i = 0; i <= length; i += intervalSize) {
            distributionValues.add((int) (length - i));
        }

        return distributionValues;
    }

    /**
     * Obtiene el tamaño del intervalo.
     *
     * @return el tamaño del intervalo
     */
    public double getSizeInterval() {
        return intervalSize;
    }

    /**
     * Establece el tamaño del intervalo.
     *
     * @param intervalSize el nuevo tamaño del intervalo
     */
    public void setSizeInterval(final double intervalSize) {
        this.intervalSize = intervalSize;
    }
}
