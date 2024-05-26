package candleChart.controller;

import candleChart.data.Buffer;
import candleChart.model.Candle;
import candleChart.view.CandleView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for candle view (CandleView) on the candle chart.
 */
public class CandleController {
    private static final double DEFAULT_MAX_PRICE = -Double.MAX_VALUE;
    private static final double DEFAULT_MIN_PRICE = Double.MAX_VALUE;

    private final CandleView candleView;
    private List<Candle> candleList;
    private CandleSize candleSize;
    private int currentCandleIndex; // Indice de inicio de la lista de velas.

    private Buffer buffer;
    private double maxPrice, minPrice;

    /**
     * Constructor de la clase CandleController.
     * Crea una nueva instancia de la clase CandleView con los valores predeterminados.
     *
     * @param candleView Instancia de la vista para este controlador.
     */
    public CandleController(CandleView candleView) {
        this.candleView = candleView;

        candleList = new ArrayList<>();
        candleSize = CandleSize.SMALL;
        currentCandleIndex = 0;
        buffer = new Buffer();
        setupView();
    }


    /**
     * Establece un buffer de datos en el controlador.
     *
     * @param buffer El buffer de datos a establecer en el controlador.
     * @throws NullPointerException Si el buffer proporcionado es nulo.
     */
    public void setBuffer(Buffer buffer) {
        if(buffer == null) {
            throw new NullPointerException("No se permiten valores nulos para el valor 'buffer'.");
        }
        this.buffer = buffer;
        updateCandleView();
    }


    /**
     * Obtiene el buffer de datos del controlador.
     *
     * @return El buffer de datos del controlador.
     */
    public Buffer getBuffer() {
        return buffer;
    }


    /**
     * Obtiene el precio máximo que será representado en el gráfico.
     *
     * @return El precio máximo.
     */
    public double getMaxPrice() {
        return maxPrice;
    }


    /**
     * Obtiene el precio mínimo que será representado en el gráfico.
     *
     * @return El precio mínimo.
     */
    public double getMinPrice() {
        return minPrice;
    }


    /**
     * Establece un objeto de tipo 'CandleSize' que proporciona información sobre el tamaño de las velas.
     *
     * @param candleSize Un objeto de tipo 'CandleSize'.
     * @throws NullPointerException Si el valor proporcionado de 'candleSize' es nulo.
     */
    public void setCandleSize(CandleSize candleSize) {
        if(candleSize == null) {
            throw new NullPointerException("No se permiten valores nulos para 'candleSize'.");
        }
        this.candleSize = candleSize;
        candleView.setCandleSize(candleSize);
        updateCandleView();
    }


    /**
     * Obtiene un objeto de tipo 'CandleSize' que proporciona información del tamaño de la vela.
     *
     * @return Objeto de tipo 'CandleSize' con información del tamaño de la vela.
     */
    public CandleSize getCandleSize() {
        return candleSize;
    }


    /**
     * Avanza un paso en la representación del gráfico siempre que no haya llegado al principio de este.
     */
    public void advance() {
        advance(1);
    }


    /**
     * Avanza en el gráfico el número de pasos proporcionado por parámetros. Si el número pasado por parámetro
     * sobrepasa el inicio del buffer, se establece el inicio del buffer como primer valor a representar.
     *
     * @param steps Número de pasos a avanzar en el buffer.
     * @throws IllegalArgumentException Si el parámetro proporcionado es negativo.
     */
    public void advance(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException("No se permiten valores negativos.");
        }

        currentCandleIndex = Math.max(currentCandleIndex - steps, 0);
        updateCandleView();
    }


    /**
     * Retrocede un paso en la representación del gráfico siempre que no se haya llegado al final del buffer.
     */
    public void retrieve() {
        retrieve(1);
    }


    /**
     * Retrocede el número de velas proporcionado por parámetro. Si el número de pasos proporcionados para retroceder
     * supera a los que puede ser representado por el buffer, se establece el máximo permitido para la correcta
     * representación de la fracción del buffer
     *
     * @param steps El número de pasos a retroceder en el buffer.
     * @throws IllegalArgumentException Si el parámetro proporcionado es negativo.
     */
    public void retrieve(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException("No se permiten valores negativos.");
        }

        int maxSteps = buffer.size() - candleList.size() - 1;
        currentCandleIndex = maxSteps - currentCandleIndex > steps? currentCandleIndex + steps: maxSteps ;
        updateCandleView();
    }


    /**
     * Configura los distintos elementos de la vista.
     */
    private void setupView() {
        candleView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateCandleView();
            }
        });
    }


    /**
     * Método encargado de llamar a todos los demás métodos auxiliares para la actualización de la vista.
     */
    private void updateCandleView() {
        updateCandleList();
        updatePriceRange();
    }


    /**
     * Método que establece la lista de velas que deben ser representadas en la vista.
     */
    private void updateCandleList() {
        int visibleCandleCount = Math.max(candleView.getWidth() / candleSize.getRelativePosition(), 0);
        calculateVisibleCandleRange(visibleCandleCount);
        candleView.setCandleList(candleList);
    }


    /**
     * Método auxiliar que establece el rango de velas a mostrar en la vista.
     * @param visibleCandleCount Número de velas que pueden ser representadas en la vista.
     */
    private void calculateVisibleCandleRange(int visibleCandleCount) {
        int bufferSize = buffer.size();
        if (bufferSize == 0) {
            currentCandleIndex = 0;
            return;
        }
        currentCandleIndex = Math.min(currentCandleIndex, bufferSize - 1); // Índice de inicio de las velas a mostrar.
        int candleLast = bufferSize - 1 - currentCandleIndex;   // Índice de la ultima vela a mostrar (Índice inferior).
        int candleFirst = Math.max(candleLast - visibleCandleCount, 0); // Índice de la primera vela (Índice superior).
        candleList = buffer.getAll().subList(candleFirst, candleLast);
    }


    /**
     * Método auxiliar que establece el rango de precio en la vista (candleView). En caso de existir una lista de velas
     * vacía, se establece el rando de precio mínimo y máximo en 0. En caso contrario se establecerá el rango superior
     * con el mayor valor de precio de la lista y el rango inferior con el rango inferior de la lista.
     */
    private void updatePriceRange() {
        if (candleList.isEmpty()) {
            maxPrice = 0;
            minPrice = 0;
        } else {
            maxPrice = candleList.stream().mapToDouble(Candle::highPrice).max().orElse(DEFAULT_MAX_PRICE);
            minPrice = candleList.stream().mapToDouble(Candle::lowPrice).min().orElse(DEFAULT_MIN_PRICE);
        }
        candleView.setPriceRange(maxPrice, minPrice);
    }
}
