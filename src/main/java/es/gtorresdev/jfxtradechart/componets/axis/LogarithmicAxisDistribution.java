package es.gtorresdev.jfxtradechart.componets.axis;

import es.gtorresdev.jfxtradechart.models.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase LogarithmicAxisDistribution representa una distribución logarítmica de divisiones en un eje,
 * donde cada división está calculada en función de una base logarítmica específica.
 */
public class LogarithmicAxisDistribution implements RangeDistributable{
    private static final double DEFAULT_BASE = 1.5;
    private static final int DEFAULT_MIN_DIVISION_SIZE = 25;

    private double base;
    private int minDivisionSize;

    /**
     * Constructor predeterminado que inicializa la distribución con la base y el tamaño mínimo de división por defecto.
     */
    public LogarithmicAxisDistribution() {
        this(DEFAULT_BASE, DEFAULT_MIN_DIVISION_SIZE);
    }

    /**
     * Constructor que permite especificar una base logarítmica y un tamaño mínimo de división.
     *
     * @param base la base logarítmica para calcular las divisiones (debe ser mayor a 1).
     * @param minDivisionSize el tamaño mínimo de cada división (debe ser positivo).
     * @throws IllegalArgumentException si la base es menor o igual a 1 o si el tamaño mínimo de división es no positivo.
     */
    public LogarithmicAxisDistribution(double base, int minDivisionSize) {
        if (base <= 1) {
            throw new IllegalArgumentException("Base must be greater than 1.");
        }

        if (minDivisionSize <= 0) {
            throw new IllegalArgumentException("Minimum division size must be positive.");
        }

        this.base = base;
        this.minDivisionSize = minDivisionSize;
    }

    /**
     * Genera una lista de posiciones de divisiones según una distribución logarítmica basada en la base especificada.
     *
     * @param length la longitud total del eje en la cual se distribuirán las divisiones.
     * @return una lista de posiciones enteras representando las divisiones logarítmicas.
     */
    @Override
    public List<Integer> distribute(int length) {
        int numDivision = calculateDivisions(length);
        return generateDistributionValues(length, numDivision);
    }

    /**
     * Genera una lista de valores distribuidos logarítmicamente dentro del rango especificado.
     * La distribución se calcula de acuerdo con la longitud del eje, generando divisiones
     * logarítmicas donde las primeras divisiones están más cerca y las últimas están
     * más separadas entre sí.
     *
     * @param range el objeto {@code Range} que representa los límites inferior y superior del rango
     *              en el cual se distribuirán los valores logarítmicos
     * @param axisLength la longitud del eje utilizada para determinar el número de divisiones
     * @return una {@code List<Double>} que contiene los valores distribuidos logarítmicamente desde
     *         el límite superior hasta el inferior del rango especificado
     */
    @Override
    public List<Double> rangeDistribute(Range range, int axisLength) {
        int numDivision = calculateDivisions(axisLength);
        List<Double> logList = new ArrayList<>();

        double logMax = Math.log(numDivision + 1) / Math.log(base);

        for (int i = 0; i <= numDivision; i++) {
            double logValue = Math.log(i + 1) / Math.log(base);
            double logRange = (logValue / logMax) * range.getRangeWidth();

            if (logRange <= range.getUpperRange()) {
                logList.add(logRange);
            }
        }
        return logList;
    }

    /**
     * Calcula el número de divisiones necesarias para la longitud dada en función de la base logarítmica y
     * el tamaño mínimo de división.
     *
     * @param length la longitud total del eje.
     * @return el número de divisiones calculado.
     */
    private int calculateDivisions(int length) {
        double numDivision = (Math.log((length * (base - 1) / minDivisionSize) + 1) / Math.log(base));
        return (int) Math.ceil(numDivision);
    }

    /**
     * Genera los valores de las posiciones de división basados en el número de divisiones y la longitud del eje.
     *
     * @param length la longitud total del eje.
     * @param numDivisions el número de divisiones calculado.
     * @return una lista de valores enteros que representan las posiciones de división en el eje.
     */
    private List<Integer> generateDistributionValues(int length, int numDivisions) {
        List<Integer> distributionValues = new ArrayList<>();

        double logMax = Math.log(numDivisions + 1) / Math.log(base);

        for (int i = 0; i <= numDivisions; i++) {
            double logValue = Math.log(i + 1) / Math.log(base);
            int position = (int) ((logValue / logMax) * length);

            if (position <= length) {
                distributionValues.add(position);
            }
        }
        return distributionValues;
    }

    /**
     * Obtiene la base logarítmica utilizada en la distribución.
     *
     * @return la base logarítmica.
     */
    public double getBase() {
        return base;
    }

    /**
     * Establece una nueva base logarítmica para la distribución.
     *
     * @param base la nueva base logarítmica (debe ser mayor a 1).
     * @throws IllegalArgumentException si la base es menor o igual a 1.
     */
    public void setBase(double base) {
        if (base <= 1) {
            throw new IllegalArgumentException("Base must be greater than 1.");
        }
        this.base = base;
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
     * Establece un nuevo tamaño mínimo de división para la distribución.
     *
     * @param minDivisionSize el nuevo tamaño mínimo de división (debe ser positivo).
     * @throws IllegalArgumentException si el tamaño mínimo de división es no positivo.
     */
    public void setMinDivisionSize(int minDivisionSize) {
        if (minDivisionSize <= 0) {
            throw new IllegalArgumentException("Minimum division size must be positive.");
        }
        this.minDivisionSize = minDivisionSize;
    }
}
