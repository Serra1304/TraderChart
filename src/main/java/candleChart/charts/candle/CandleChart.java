package candleChart.charts.candle;

import candleChart.charts.base.Chart;
import candleChart.charts.base.ChartListener;
import candleChart.charts.base.Info;
import candleChart.data.Buffer;
import candleChart.model.Candle;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for candle view (CandleView) on the candle chart.
 */
public class CandleChart implements Chart{
    private static final double DEFAULT_MAX_PRICE = -Double.MAX_VALUE;
    private static final double DEFAULT_MIN_PRICE = Double.MAX_VALUE;

    private final CandleView candleView;
    private List<Candle> candleList;
    private int currentCandleIndex; // Indice de inicio de la lista de velas.

    private Buffer buffer;
    private double maxPrice, minPrice;
    private int relativePosition;
    private final List<ChartListener> chartListeners;
    private Info info;
    private int indexInfo;

    /**
     * Constructor de la clase CandleChart.
     * Crea una nueva instancia de la clase CandleView con los valores predeterminados.
     */
    public CandleChart() {
        this.candleView = new CandleView();
        chartListeners = new ArrayList<>();
        indexInfo = 0;

        candleList = new ArrayList<>();
        relativePosition = candleView.getCandleSize().getRelativePosition();
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
        relativePosition = candleSize.getRelativePosition();
        candleView.setCandleSize(candleSize);
        updateCandleView();
        notifyRelativePosition(relativePosition);
    }


    /**
     * Obtiene un objeto de tipo 'CandleSize' que proporciona información del tamaño de la vela.
     *
     * @return Objeto de tipo 'CandleSize' con información del tamaño de la vela.
     */
    public CandleSize getCandleSize() {
        return candleView.getCandleSize();
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
        int visibleCandleCount = Math.max((candleView.getWidth() + relativePosition) / relativePosition, 0);
        calculateVisibleCandleRange(visibleCandleCount);
        candleView.setCandleList(candleList);
        notifyXAxisInfo(chartXAxisInfo());
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
        notifyYAxisRange(maxPrice, minPrice);
    }

    @NotNull
    private List<String> chartXAxisInfo() {
        List<String> listInfo = new ArrayList<>();

        for(Candle candle: candleList){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm");
            String stringDateTime = candle.dateTime().format(formatter);

            listInfo.add(stringDateTime);
        }
        return listInfo;
    }

    @Override
    public CandleView getGraphicView() {
        return candleView;
    }


    @Override
    public void addListener(ChartListener listener) {
        chartListeners.add(listener);
        listener.OnRelativePosition(relativePosition);
        listener.OnXAxisInfo(chartXAxisInfo());
    }

    @Override
    public void removeListener(ChartListener listener) {
        chartListeners.remove(listener);
    }

    @Override
    public void notifyRelativePosition(int relativePosition) {
        for(ChartListener listener: chartListeners) {
            listener.OnRelativePosition(relativePosition);
        }
    }

    @Override
    public void notifyXAxisInfo(List<String> xAixisInfoList) {
        for(ChartListener listener: chartListeners) {
            listener.OnXAxisInfo(xAixisInfoList);
        }
    }

    @Override
    public void notifyYAxisRange(double rangeUp, double rangeDown) {
        for(ChartListener listener: chartListeners) {
            listener.OnPriceRange(rangeUp, rangeDown);
        }
    }

    @Override
    public void setCharInfo(@NotNull Info info) {
        this.info = info;
        this.info.addInfo("");
        indexInfo = info.getComponentCount() - 1;
    }

    @Override
    public void cursorMoved(int locationX, int locationY) {
        if (info != null) {
            int indexCandleTime = (locationX + relativePosition / 2) / relativePosition;  // Vela a la que apunta el cursor.

            // Se actualiza el valor de la información con los datos de la vela a la que apunta el cursor.
            if (candleList.size() > indexCandleTime) {
                info.updateInfo(indexInfo, candleList.get(indexCandleTime).toString());
            } else {
                info.updateInfo(indexInfo, "");
            }
        }
    }

    @Override
    public void cursorVisibility(boolean aFlag) {

    }
}
