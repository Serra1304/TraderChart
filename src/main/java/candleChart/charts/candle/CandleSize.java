package candleChart.charts.candle;

import java.util.HashMap;
import java.util.Map;


/**
 * Enumeración que representa diferentes tamaños de velas con sus propiedades asociadas.
 */
public enum CandleSize {
    /**
     * Vela muy pequeña con un índice de 1, posición relativa de 4 y ancho de 3.
     */
    VERY_SMALL(1, 4, 3),

    /**
     * Vela pequeña con un índice de 2, posición relativa de 8 y ancho de 5.
     */
    SMALL(2, 8, 5),

    /**
     * Vela grande con un índice de 3, posición relativa de 16 y ancho de 11.
     */
    LARGE(3, 16, 11),

    /**
     * Vela muy grande con un índice de 4, posición relativa de 32 y ancho de 19.
     */
    VERY_LARGE(4, 32, 19);

    private final int index;
    private final int relativePosition;
    private final int candleWidth;

    private static final Map<Integer, CandleSize> INDEX_MAP = new HashMap<>();

    // Bloque estático para inicializar el mapa de índices
    static {
        for (CandleSize size : CandleSize.values()) {
            INDEX_MAP.put(size.index, size);
        }
    }


    /**
     * Constructor para CandleSize.
     *
     * @param index El índice del tamaño de la vela.
     * @param relativePosition La posición relativa del tamaño de la vela.
     * @param candleWidth El ancho del tamaño de la vela.
     */
    CandleSize(int index, int relativePosition, int candleWidth) {
        this.index = index;
        this.relativePosition = relativePosition;
        this.candleWidth = candleWidth;
    }


    /**
     * Obtiene el índice del tamaño de la vela.
     *
     * @return El índice del tamaño de la vela.
     */
    public int getIndex() {
        return index;
    }


    /**
     * Obtiene la posición relativa del tamaño de la vela.
     *
     * @return La posición relativa del tamaño de la vela.
     */
    public int getRelativePosition() {
        return relativePosition;
    }


    /**
     * Obtiene el ancho del tamaño de la vela.
     *
     * @return El ancho del tamaño de la vela.
     */
    public int getCandleWidth() {
        return candleWidth;
    }


    /**
     * Obtiene una instancia de CandleSize por su índice.
     *
     * @param index el índice del tamaño de la vela
     * @return la instancia de CandleSize correspondiente al índice, o null si no se encuentra
     */
    public static CandleSize getByIndex(int index) {
        return INDEX_MAP.get(index);
    }
}









//package candleChart.charts.candle;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * Enumeración que representa diferentes tamaños de velas con sus propiedades asociadas.
// */
//public enum CandleSize {
//    /**
//     * Vela muy pequeña con un índice de 1, posición relativa de 4 y ancho de 3.
//     */
//    VERY_SMALL(1, 4, 3),
//
//    /**
//     * Vela pequeña con un índice de 2, posición relativa de 8 y ancho de 5.
//     */
//    SMALL(2, 8, 5),
//
//    /**
//     * Vela grande con un índice de 3, posición relativa de 16 y ancho de 11.
//     */
//    LARGE(3, 16, 11),
//
//    /**
//     * Vela muy grande con un índice de 4, posición relativa de 32 y ancho de 19.
//     */
//    VERY_LARGE(4, 32, 19);
//
//    private final int index;
//    private final int relativePosition;
//    private final int candleWidth;
//
//    private static final Map<Integer, CandleSize> INDEX_MAP = new HashMap<>();
//
//    // Bloque estático para inicializar el mapa de índices
//    static {
//        for (CandleSize size : CandleSize.values()) {
//            INDEX_MAP.put(size.index, size);
//        }
//    }
//
//
//    /**
//     * Constructor para CandleSize.
//     *
//     * @param index El índice del tamaño de la vela.
//     * @param relativePosition La posición relativa del tamaño de la vela.
//     * @param candleWidth El ancho del tamaño de la vela.
//     */
//    CandleSize(int index, int relativePosition, int candleWidth) {
//        this.index = index;
//        this.relativePosition = relativePosition;
//        this.candleWidth = candleWidth;
//    }
//
//
//    /**
//     * Obtiene el índice del tamaño de la vela.
//     *
//     * @return El índice del tamaño de la vela.
//     */
//    public int getIndex() {
//        return index;
//    }
//
//
//    /**
//     * Obtiene la posición relativa del tamaño de la vela.
//     *
//     * @return La posición relativa del tamaño de la vela.
//     */
//    public int getRelativePosition() {
//        return relativePosition;
//    }
//
//
//    /**
//     * Obtiene el ancho del tamaño de la vela.
//     *
//     * @return El ancho del tamaño de la vela.
//     */
//    public int getCandleWidth() {
//        return candleWidth;
//    }
//
//
//    /**
//     * Obtiene una instancia de CandleSize por su índice.
//     *
//     * @param index el índice del tamaño de la vela
//     * @return la instancia de CandleSize correspondiente al índice, o null si no se encuentra
//     */
//    public static CandleSize getByIndex(int index) {
//        return INDEX_MAP.get(index);
//    }
//}
