package candleChart.controller;

import candleChart.data.Buffer;
import candleChart.model.Candle;
import candleChart.view.CandleView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * Controller for candle view (CandleView) on the candle chart.
 */
public class CandleController {

    private final CandleView candleView;

    private Buffer buffer;
    private List<Candle> candleList;

    private double maxPrice, minPrice;

    private CandleSize candleSize;
    private int candlePosition;

    public CandleController(CandleView candleView) {
        this.candleView = candleView;

        candlePosition = 0;
        candleSize = CandleSize.SMALL;
        buffer = new Buffer();
        setupView();
    }

    /**
     * Establece un buffer de datos en el controlador.
     *
     * @param buffer El buffer de datos a establecer en el controlador.
     */
    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
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

    public void setCandleSize(CandleSize candleSize) {
        this.candleSize = candleSize;
        candleView.setCandleSize(candleSize);
        updateCandleView();
    }

    public CandleSize getCandleSize() {
        return candleSize;
    }

    public void advance() {
        candlePosition--;
        updateCandleView();
    }

    public void advance(int steps) {
        candlePosition -= steps;
        updateCandleView();
    }

    public void retrieve() {
        candlePosition++;
        updateCandleView();
    }

    public void retrieve(int steps) {
        candlePosition += steps;
        updateCandleView();
    }

    private void setupView() {
        candleView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateCandleView();

                candleView.setCandleSize(candleSize);
            }
        });
    }

    private void updateCandleView() {
        updateCandleList();
        updatePriceRange();

    }

    private void updateCandleList() {
        int numVelas = Math.max(candleView.getWidth() / candleSize.getRelativePosition(), 0);
        int candleLast = buffer.size() -1 - candlePosition;
        int candleFirst = Math.max(candleLast - numVelas, 0);

        List<Candle> candleList = buffer.getAll().subList(candleFirst, candleLast);
        this.candleList = candleList;
        candleView.setCandleList(candleList);
    }

    private void updatePriceRange() {
        maxPrice = -999999999;
        minPrice = 999999999;

        for (Candle candle : candleList) {
            if (candle.highPrice() > maxPrice) {
                maxPrice = candle.highPrice();
            }
            if (candle.lowPrice() < minPrice) {
                minPrice = candle.lowPrice();
            }
        }
        if (candleList.isEmpty()) {
            maxPrice = 0;
            minPrice = 0;
        }
        candleView.setPriceRange(maxPrice, minPrice);
    }
}
