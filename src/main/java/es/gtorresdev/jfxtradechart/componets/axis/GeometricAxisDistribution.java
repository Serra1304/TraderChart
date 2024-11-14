package es.gtorresdev.jfxtradechart.componets.axis;

import es.gtorresdev.jfxtradechart.models.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase GeometricAxisDistribution representa una distribución geométrica
 * de divisiones en un eje, donde cada división aumenta según una tasa geométrica.
 */
public class GeometricAxisDistribution implements RangeDistributable{
    private static final double DEFAULT_RATE = 1.5;
    private static final int DEFAULT_MIN_DIVISION_SIZE = 25;

    private double rate;
    private int minDivisionSize;

    /**
     * Constructor predeterminado que inicializa la distribución con la tasa
     * y el tamaño mínimo de división por defecto.
     */
    public GeometricAxisDistribution() {
        this(DEFAULT_MIN_DIVISION_SIZE, DEFAULT_RATE);
    }

    /**
     * Constructor que permite definir la tasa y el tamaño mínimo de división.
     *
     * @param minDivisionSize el tamaño mínimo de cada división.
     * @param rate la tasa geométrica de crecimiento entre divisiones.
     */
    public GeometricAxisDistribution(int minDivisionSize, double rate) {
        this.minDivisionSize = minDivisionSize;
        this.rate = rate;
    }

    /**
     * Genera una lista de posiciones para las divisiones de acuerdo a una
     * distribución geométrica.
     *
     * @param length la longitud total del eje donde se distribuirán las divisiones.
     * @return una lista de posiciones enteras representando las divisiones.
     */
    @Override
    public List<Integer> distribute(int length) {
        int numDivision = calculateDivisions(length);
        return generateDistributionValues(length, numDivision);
    }

    /**
     * Genera una lista de valores distribuidos geométricamente dentro del rango especificado.
     * La distribución geométrica se basa en una progresión geométrica ajustada, donde cada
     * incremento está determinado por un factor de razón y un tamaño mínimo de división.
     * Los valores generados están limitados al ancho del rango proporcionado.
     *
     * @param range el objeto {@code Range} que representa los límites inferior y superior del rango
     *              en el cual se distribuirán los valores geométricos
     * @param axisLength la longitud del eje utilizada para determinar el número de divisiones
     * @return una {@code List<Double>} que contiene los valores distribuidos geométricamente desde
     *         el inicio hasta el límite superior del rango especificado
     */
    @Override
    public List<Double> rangeDistribute(Range range, int axisLength) {
        int numDivision = calculateDivisions(axisLength);
        double currentPositions = 0.0;
        double geometricSum = calculateGeometricSum(numDivision);
        double adjustmentFactor = range.getRangeWidth() / geometricSum;
        List<Double> geomValues = new ArrayList<>();

        geomValues.add(currentPositions);

        for (int i = 0; i < numDivision; i++) {
            currentPositions += minDivisionSize * Math.pow(rate, numDivision - i - 1) * adjustmentFactor;
            geomValues.add(Math.min(currentPositions, range.getRangeWidth()));
        }
        return geomValues;
    }

    /**
     * Calcula el número de divisiones necesarias para la longitud dada, en función
     * de la tasa geométrica y el tamaño mínimo de división.
     *
     * @param length la longitud del eje.
     * @return el número de divisiones calculado.
     */
    private int calculateDivisions (int length) {
        double numDivision = Math.log((length * (rate - 1) / minDivisionSize) + 1) / Math.log(rate);
        return (int) Math.ceil(numDivision);
    }

    /**
     * Genera los valores de las posiciones de división basados en el número
     * de divisiones y la longitud del eje.
     *
     * @param length la longitud del eje.
     * @param numDivision el número de divisiones calculado.
     * @return una lista de valores enteros que representan las posiciones de división.
     */
    private List<Integer> generateDistributionValues(int length, int numDivision) {
        List<Integer> distributionValues = new ArrayList<>();
        double currentPositions = 0.0;
        double geometricSum = calculateGeometricSum(numDivision);
        double adjustmentFactor = length / geometricSum;

        distributionValues.add(0);

        for (int i = 0; i < numDivision; i++) {
            currentPositions += minDivisionSize * Math.pow(rate, numDivision - i - 1) * adjustmentFactor;
            distributionValues.add((int) Math.min(currentPositions, length));
        }
        return distributionValues;
    }

    /**
     * Calcula la suma geométrica de los valores de las divisiones para un número dado de divisiones.
     *
     * @param numberOfDivisions el número de divisiones.
     * @return el valor de la suma geométrica.
     */
    private double calculateGeometricSum(int numberOfDivisions) {
        double sum = 0.0;
        for (int i = 0; i < numberOfDivisions; i++) {
            sum += minDivisionSize * Math.pow(rate, i);
        }
        return sum;
    }

    /**
     * Obtiene la tasa geométrica de crecimiento entre divisiones.
     *
     * @return la tasa de crecimiento.
     */
    public double getRate() {
        return rate;
    }

    /**
     * Establece una nueva tasa geométrica de crecimiento entre divisiones.
     *
     * @param rate la nueva tasa de crecimiento.
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Obtiene el tamaño mínimo de cada división.
     *
     * @return el tamaño mínimo de división.
     */
    public int getMinDivisionSize() {
        return minDivisionSize;
    }

    /**
     * Establece un nuevo tamaño mínimo de división.
     *
     * @param minDivisionSize el nuevo tamaño mínimo de división.
     */
    public void setMinDivisionSize(int minDivisionSize) {
        this.minDivisionSize = minDivisionSize;
    }
}
