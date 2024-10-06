package es.gtorresdev.jfxtradechart.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * La clase {@code Range} representa un rango numérico, utilizado principalmente para definir los límites del eje Y
 * en un gráfico. Esta clase asegura que el rango superior siempre sea mayor o igual al rango inferior.
 */
public class Range {
    private double upperRange;
    private double lowerRange;

    /**
     * Crea una instancia de {@code Range} con los valores por defecto de 0 para el rango superior
     * y 0 para el rango inferior.
     */
    @Contract(pure = true)
    public Range() {
        this(0,0);
    }

    /**
     * Crea una instancia de {@code Range} con los valores proporcionados para los rangos superior e inferior.
     * Si el rango superior es menor que el rango inferior, lanza una excepción {@code IllegalArgumentException}.
     *
     * @param upperRange el valor del rango superior
     * @param lowerRange el valor del rango inferior
     * @throws IllegalArgumentException si {@code upperRange} es menor que {@code lowerRange}
     */
    @Contract(pure = true)
    public Range(double upperRange, double lowerRange) {
        if (upperRange < lowerRange) {
            throw new IllegalArgumentException("The upper range must be greater than or equal to the lower range.");
        }

        this.upperRange = upperRange;
        this.lowerRange = lowerRange;
    }

    /**
     * Obtiene el valor del rango superior.
     *
     * @return el valor del rango superior
     */
    public double getUpperRange() {
        return upperRange;
    }

    /**
     * Estable ce un nuevo rango superior. Si este rango fuese inferior al rango inferior se lanzara una
     * {@code IllegalArgumentException.
     * }
     * @param upperRange nuevo rango superior a establecer.
     * @throws IllegalArgumentException si {@code upperRange} es menor que {@code lowerRange}
     */
    public void setUpperRange(double upperRange) {
        if (upperRange < this.lowerRange) {
            throw new IllegalArgumentException("The upper range must be greater than or equal to the lower range.");
        }

        this.upperRange = upperRange;
    }

    /**
     * Obtiene el valor del tango inferior.
     *
     * @return el valor del rango inferior
     */
    public double getLowerRange() {
        return lowerRange;
    }

    /**
     * Estable ce un nuevo rango inferior. Si este rango fuese superior al rango superior se lanzara una
     * {@code IllegalArgumentException.
     * }
     * @param lowerRange nuevo rango inferior a establecer.
     * @throws IllegalArgumentException si {@code lowerRange} es mayor que {@code upperRange}
     */
    public void setLowerRange(double lowerRange) {
        if (lowerRange > this.upperRange) {
            throw new IllegalArgumentException("The lower range must be less than or equal to the upper range.");
        }

        this.lowerRange = lowerRange;
    }

    /**
     * Establece un nuevo {@code Range} con el rango proporcionado.
     *
     * @param range el rango a establecer.
     */
    public void setRange(@NotNull Range range) {
        this.upperRange = range.getUpperRange();
        this.lowerRange = range.getLowerRange();
    }

    /**
     * Establece un nuevo {@code Range} con los valores proporcionados para los rangos superior e inferior.
     * Si el rango superior es menor que el rango inferior, lanza una excepción {@code IllegalArgumentException}.
     *
     * @param upperRange el valor del rango superior
     * @param lowerRange el valor del rango inferior
     * @throws IllegalArgumentException si {@code upperRange} es menor que {@code lowerRange}
     */
    public void setRange(double upperRange, double lowerRange) {
        if (upperRange < lowerRange) {
            throw new IllegalArgumentException("The upper range must be greater than or equal to the lower range.");
        }

        this.upperRange = upperRange;
        this.lowerRange = lowerRange;
    }

    /**
     * Calcula y devuelve la amplitud del rango, que es la diferencia entre el rango superior y el rango inferior.
     *
     * @return la amplitud del rango (upperRange - lowerRange)
     */
    public double getRangeWidth() {
        return upperRange - lowerRange;
    }

    /**
     * Verifica si un valor dado está dentro del rango, incluyendo los límites.
     *
     * @param value el valor a comprobar
     * @return {@code true} si el valor está dentro del rango, {@code false} en caso contrario
     */
    public boolean contains(double value) {
        return value >= lowerRange && value <= upperRange;
    }

    /**
     * Crea una nueva instancia de {@code Range} asegurando que el rango superior sea mayor o igual al rango inferior.
     * Si el rango superior es menor que el inferior, se lanza una excepción {@code IllegalArgumentException}.
     *
     * @param upperRange el valor del rango superior
     * @param lowerRange el valor del rango inferior
     * @return una nueva instancia de {@code Range}
     * @throws IllegalArgumentException si {@code upperRange} es menor que {@code lowerRange}
     */
    @Contract("_, _ -> new")
    public static @NotNull Range createRange(double upperRange, double lowerRange) {
        if (upperRange < lowerRange) {
            throw new IllegalArgumentException("The upper range must be greater than or equal to the lower range.");
        }
        return new Range(upperRange, lowerRange);
    }
}
