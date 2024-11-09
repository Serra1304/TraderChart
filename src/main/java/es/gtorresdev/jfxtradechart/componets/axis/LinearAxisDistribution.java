package es.gtorresdev.jfxtradechart.componets.axis;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una distribución lineal de valores en un eje.
 * La distribución se realiza de manera que las divisiones tengan un tamaño mínimo especificado
 * y se ajusten a la longitud del eje de acuerdo con la cantidad máxima de divisiones.
 */
public class LinearAxisDistribution implements AxisDistribution {
    private static final int DEFAULT_NUMBER_OF_DIVISION = 5;
    private static final double DEFAULT_MIN_DIVISION_SIZE = 25.0;

    private int numberOfDivision;
    private double minDivisionSize;

    /**
     * Crea una nueva instancia de LinearAxisDistribution con un número predeterminado de divisiones
     * y un tamaño mínimo para cada división.
     */
    public LinearAxisDistribution() {
        this(DEFAULT_NUMBER_OF_DIVISION, DEFAULT_MIN_DIVISION_SIZE);
    }

    /**
     * Crea una nueva instancia de LinearAxisDistribution con un número de divisiones
     * y un tamaño mínimo para cada división proporcionado.
     */
    public LinearAxisDistribution(int numberOfDivision, double minDivisionSize) {
        this.numberOfDivision = numberOfDivision;
        this.minDivisionSize = minDivisionSize;
    }

    /**
     * Distribuye los valores a lo largo de la longitud del eje, utilizando una distribución lineal.
     * Ajusta el número de divisiones y el tamaño de cada una para cumplir con el tamaño mínimo de división.
     *
     * @param length la longitud total del eje
     * @return una lista de valores distribuidos uniformemente a lo largo del eje
     */
    @Override
    public List<Integer> distribute(int length) {
        int numDivision = calculateDivisions(length);
        double divisionSize = (double) length / numDivision;

        return generateDistributionValues(length, divisionSize);
    }

    /**
     * Calcula el número óptimo de divisiones basado en la longitud del eje y el tamaño mínimo de división.
     *
     * @param length la longitud total del eje
     * @return el número de divisiones ajustadas para cumplir con el tamaño mínimo
     */
    private int calculateDivisions(int length) {
        int numDivision = numberOfDivision;
        while (numDivision > 0 && (double) length / numDivision < minDivisionSize) {
            numDivision--;
        }
        return numDivision;
    }

    /**
     * Genera los valores de distribución a lo largo del eje, utilizando el tamaño de división especificado.
     *
     * @param length la longitud total del eje
     * @param divisionSize el tamaño de cada división calculado
     * @return una lista de valores distribuidos uniformemente a lo largo del eje
     */
    private List<Integer> generateDistributionValues(int length, double divisionSize) {
        List<Integer> distributionValues = new ArrayList<>();

        if (length > 0) {
            for (double i = 0; i < length; i += divisionSize) {
                distributionValues.add((int) Math.round(i));
            }

            if (distributionValues.isEmpty() || distributionValues.getLast() < length) {
                distributionValues.add(length);
            }
        }
        return distributionValues;
    }

    /**
     * Obtiene el número máximo de divisiones.
     *
     * @return el número de divisiones configurado
     */
    public int getNumberOfDivision() {
        return numberOfDivision;
    }

    /**
     * Establece el número máximo de divisiones.
     *
     * @param numberOfDivision el nuevo número máximo de divisiones
     */
    public void setNumberOfDivision(int numberOfDivision) {
        this.numberOfDivision = numberOfDivision;
    }

    /**
     * Obtiene el tamaño mínimo de cada división.
     *
     * @return el tamaño mínimo de la división
     */
    public double getMinDivisionSize() {
        return minDivisionSize;
    }

    /**
     * Establece el tamaño mínimo de cada división.
     *
     * @param minDivisionSize el nuevo tamaño mínimo de la división
     */
    public void setMinDivisionSize(double minDivisionSize) {
        this.minDivisionSize = minDivisionSize;
    }
}
