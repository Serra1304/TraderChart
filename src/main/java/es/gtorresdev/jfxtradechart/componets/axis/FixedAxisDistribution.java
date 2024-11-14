package es.gtorresdev.jfxtradechart.componets.axis;

import es.gtorresdev.jfxtradechart.models.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una distribución fija de valores en un eje.
 * Los valores se distribuyen de manera uniforme según un tamaño de intervalo específico.
 * Además, permite ajustar la alineación de la distribución al inicio o al final del eje.
 */
public class FixedAxisDistribution implements RangeDistributable{
    private static final double DEFAULT_INTERVAL_SIZE = 32;
    private static final AxisAlignment DEFAULT_AXIS_ALIGNMENT = AxisAlignment.START;

    private double intervalSize;
    private AxisAlignment axisAlignment;

    /**
     * Crea una nueva instancia de FixedAxisDistribution
     * con un tamaño de intervalo y alineación predeterminados.
     */
    public FixedAxisDistribution() {
        this(DEFAULT_INTERVAL_SIZE, DEFAULT_AXIS_ALIGNMENT);
    }

    /**
     * Crea una nueva instancia de FixedAxisDistribution
     * con el tamaño de intervalo especificado y alineación predeterminada.
     *
     * @param intervalSize el tamaño del intervalo para la distribución
     */
    public FixedAxisDistribution(double intervalSize) {
        this(intervalSize, DEFAULT_AXIS_ALIGNMENT);
    }

    /**
     * Crea una nueva instancia de FixedAxisDistribution con un tamaño de intervalo
     * y una alineación especificada.
     *
     * @param intervalSize el tamaño del intervalo para la distribución
     * @param axisAlignment la alineación de la distribución en el eje (inicio o fin)
     */
    public FixedAxisDistribution(double intervalSize, AxisAlignment axisAlignment) {
        this.intervalSize = intervalSize;
        this.axisAlignment = axisAlignment;
    }

    /**
     * Distribuye los valores a lo largo de la longitud del eje
     * utilizando el tamaño de intervalo especificado.
     *
     * @param length la longitud total del eje
     * @return una lista de valores distribuidos a lo largo del eje
     */
    @Override
    public List<Integer> distribute(int length) {
        return generateDistributionValues(length);
    }

    /**
     * Genera una lista de valores distribuidos de manera fija dentro del rango especificado.
     * La distribución fija se basa en un intervalo constante, donde cada valor es una
     * distancia específica dentro del rango, basada en la longitud del eje y el tamaño del intervalo.
     * La alineación de la distribución puede ajustarse para comenzar al inicio o al final del eje.
     *
     * @param range el objeto {@code Range} que representa los límites inferior y superior del rango
     *              en el cual se distribuirán los valores de manera fija
     * @param axisLength la longitud del eje utilizada para determinar el número de divisiones y
     *                   calcular la distancia fija entre cada valor
     * @return una {@code List<Double>} que contiene los valores distribuidos de manera fija desde
     *         el inicio hasta el final del rango especificado
     */
    @Override
    public List<Double> rangeDistribute(Range range, int axisLength) {
        List<Double> fixedValues = new ArrayList<>();
        double rangePerPixel = range.getRangeWidth() / axisLength;
        double intervalStart = calculateIntervalStart(axisLength);

        for(double i = intervalStart; i <= axisLength; i += intervalSize) {
            fixedValues.add(i * rangePerPixel);
        }
        return fixedValues;
    }

    /**
     * Genera los valores de distribución a partir de la longitud dada.
     * La distribución puede comenzar al inicio o al final del eje, dependiendo de la alineación especificada.
     *
     * @param length la longitud total del eje
     * @return una lista de valores de distribución
     */
    private List<Integer> generateDistributionValues(int length) {
        List<Integer> distributionValues = new ArrayList<>();
        double intervalStart = calculateIntervalStart(length);

        for(double i = intervalStart; i <= length; i += intervalSize) {
            distributionValues.add((int) i);
        }
        return distributionValues;
    }

    /**
     * Calcula el punto inicial del intervalo de distribución basado en la longitud del eje y la alineación especificada.
     * Si la alineación del eje es {@code AxisAlignment.START}, el inicio del intervalo será 0.
     * Si la alineación es {@code AxisAlignment.END}, el inicio del intervalo se ajustará
     * para alinear el final de la distribución con el final del eje.
     *
     * @param length la longitud total del eje en la cual se distribuyen los valores.
     * @return el punto de inicio del intervalo de distribución basado en la alineación especificada.
     */
    private double calculateIntervalStart(int length) {
        double alignmentEnd = length - ((int) (length / intervalSize)) * intervalSize;
        return axisAlignment == AxisAlignment.START ? 0 : alignmentEnd;
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
    public void setSizeInterval(double intervalSize) {
        this.intervalSize = intervalSize;
    }

    /**
     * Obtiene la alineación actual del eje para la distribución.
     *
     * @return la alineación del eje
     */
    public AxisAlignment getAxisAlignment() {
        return axisAlignment;
    }

    /**
     * Establece la alineación del eje para la distribución.
     * La alineación puede ser al inicio o al final del eje.
     *
     * @param axisAlignment la nueva alineación del eje
     */
    public void setAxisAlignment(AxisAlignment axisAlignment) {
        this.axisAlignment = axisAlignment;
    }
}
