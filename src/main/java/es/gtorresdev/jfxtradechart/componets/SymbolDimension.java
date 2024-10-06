package es.gtorresdev.jfxtradechart.componets;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración que representa diferentes tamaños de elementos con sus propiedades asociadas.
 */
public enum SymbolDimension {
    /**
     * Elemento muy pequeño con un índice de 1, posición relativa de 4 y ancho de 3.
     */
    VERY_SMALL(1, 4, 3),

    /**
     * Elemento pequeño con un índice de 2, posición relativa de 8 y ancho de 5.
     */
    SMALL(2, 8, 5),

    /**
     * Elemento grande con un índice de 3, posición relativa de 16 y ancho de 11.
     */
    LARGE(3, 16, 11),

    /**
     * Elemento muy grande con un índice de 4, posición relativa de 32 y ancho de 19.
     */
    VERY_LARGE(4, 32, 19);

    private final int index;
    private final int relativePosition;
    private final int width;

    private static final Map<Integer, SymbolDimension> INDEX_MAP = new HashMap<>();

    // Bloque estático para inicializar el mapa de índices
    static {
        for (SymbolDimension size : SymbolDimension.values()) {
            INDEX_MAP.put(size.index, size);
        }
    }

    /**
     * Constructor para ElementDimension.
     *
     * @param index El índice del tamaño del elementDimension.
     * @param relativePosition La posición relativa del elementDimension.
     * @param width El ancho del tamaño del elementoDimension.
     */
    @Contract(pure = true)
    SymbolDimension(int index, int relativePosition, int width) {
        this.index = index;
        this.relativePosition = relativePosition;
        this.width = width;
    }

    /**
     * Obtiene el índice del elementDimension.
     *
     * @return El índice del elementDimension.
     */
    @Contract(pure = true)
    public int getIndex() {
        return index;
    }

    /**
     * Obtiene la posición relativa del elementDimension. Este parámetro indíca la distancia entre elementos
     * representados en un gráfico.
     *
     * @return La posición relativa del elementDimension.
     */
    @Contract(pure = true)
    public int getRelativePosition() {
        return relativePosition;
    }

    /**
     * Obtiene el ancho del elemento representado en un gráfico.
     *
     * @return El ancho del elementDimension.
     */
    @Contract(pure = true)
    public int getWidth() {
        return width;
    }

    /**
     * Obtiene una instancia de ElementDimension por su índice.
     *
     * @param index el índice del tamaño del elementDimension
     * @return la instancia de ElementDimension correspondiente al índice, o null si no se encuentra
     */
    @Contract(pure = true)
    public static SymbolDimension getByIndex(int index) {
        return INDEX_MAP.get(index);
    }
}
