package es.gtorresdev.jfxtradechart.models;

/**
 * Enum que representa el tipo de manejo del rango en un gráfico.
 */
public enum OverflowRange {
    /** Rango fijo. Si el gráfico contiene valores fuera del rango establecido, estos no serán visibles. */
    FIXED,

    /** Rango dinámico. El rango será ajustado automáticamente si el gráfico contiene valores fuera dle rango actual. */
    DYNAMIC
}
