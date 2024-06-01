package candleChart;

import candleChart.controller.*;
import candleChart.data.Buffer;

import javax.swing.*;

public class TraderChart extends JPanel {

    private final ChartController chartController;
    private Buffer buffer;

    public TraderChart() {
        chartController = new ChartController(this);
        buffer = new Buffer();
    }


    /**
     * Establece un buffer de datos en el gráfico. Si durante la ejecución, el buffer es modificado, no se reflejarán los
     * cambios asta que no se llame al método updateChart;
     *
     * @param buffer El nuevo buffer.
     * @throws NullPointerException Si el buffer pasado es nulo.
     */
    public void setBuffer(Buffer buffer) {
        if(buffer == null) {
            throw new NullPointerException("No se permite un buffer con valor nulo");
        }
        this.buffer = buffer;
        update();
    }


    /**
     * Obtiene el buffer de datos del grafico.
     *
     * @return El buffer de datos del grafico
     */
    public Buffer getBuffer() {
        return buffer;
    }


    /**
     * Actualiza el buffer de datos del grafico
     */
    public void update() {
        final Buffer bf = buffer;
        chartController.setBuffer(bf);
    }


    /**
     * Establece la visibilidad de la cuadrícula del gráfico. Si visibility se establece a true la cuadrícula será
     * mostrada, o en caso de ser false, esta se ocultará.
     *
     * @param visibility Visibilidad de la cuadrícula.
     */
    public void setGridVisible(boolean visibility) {
        chartController.setGridVisible(visibility);
    }


    /**
     * Obtiene la visibilidad de la cuadrícula del gráfico. En caso de que la cuadrícula este visible devuelve true, en
     * caso contrario devolverá false.
     *
     * @return Estado de la visibilidad de la cuadrícula.
     */
    public boolean isGridVisible() {
        return chartController.isGridVisible();
    }


    /**
     * Establece la visibilidad de las líneas del cursor en el gráfico. En caso de establecer visibility como true,
     * las líneas del cursor serán mostradas en el gráfico. En caso de ser false esta no se mostrarán.
     *
     * @param visibility Visibilidad de las líneas del cursor.
     */
    public void setCursorVisible(boolean visibility) {
        chartController.setCursorVisible(visibility);
    }


    /**
     * Obtiene la visibilidad de las líneas del cursor en el gráfico. En caso de que las líneas se muestre devuelve
     * true, en caso contrario devuelve false.
     *
     * @return Visibilidad de las líneas del cursor.
     */
    public boolean isCursorVisible() {
        return chartController.isCursorVisible();
    }


    /**
     * Establece el tamaño de vela del gráfico.
     *
     * @param candleSize El tamaño de vela a establecer en el gráfico.
     * @throws NullPointerException Si la propiedad 'candleSize' proporcionada es nula.
     */
    public void setCandleSize(CandleSize candleSize) {
        if(candleSize == null) {
            throw new NullPointerException("No se permiten valores nulos para la propiedad 'candleSize");
        }
        chartController.setCandleSize(candleSize);
    }


    /**
     * Obtiene el tamaño de vela establecido en el gráfico.
     *
     * @return El tamaño de vela establecido en el gráfico.
     */
    public CandleSize getCandleSize() {
        return chartController.getCandleSize();
    }


    /**
     * Establece el símbolo del gráfico en la barra de información.
     *
     * @param symbol El símbolo del gráfico.
     */
    public void setSymbol(String symbol) {
        if(symbol == null) {
            throw new NullPointerException("El valor proporcionado para 'symbol' es nulo.");
        }
        chartController.setSymbol(symbol);
    }


    /**
     * Obtiene el símbolo establecido en el gráfico.
     *
     * @return El símbolo del gráfico.
     */
    public String getSymbol() {
        return chartController.getSymbol();
    }


    /**
     * Avanza una posición en el gráfico siempre que haya datos que mostrar.
     */
    public void advance() {
        chartController.advance();
    }


    /**
     * Avanza en el gráfico el número de pasos proporcionado por parámetros.
     *
     * @param steps Número de pasos a avanzar en el buffer.
     * @throws IllegalArgumentException Si el parámetro proporcionado es negativo.
     */
    public void advance(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException("No se permiten valores negativos para la propiedad 'steps'.");
        }
        chartController.advance(steps);
    }


    /**
     * Retrocede en el gráfico una posición siempre que haya datos que mostrar.
     */
    public void retrieve() {
        chartController.retrieve();
    }


    /**
     * Retrocede en el gráfico el número de velas proporcionado por parámetro, o hasta que haya datos que mostrar.
     *
     * @param steps El número de pasos a retroceder en el buffer.
     * @throws IllegalArgumentException Si el parámetro proporcionado es negativo.
     */
    public void retrieve(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException("No se permiten valores negativos para la propiedad 'steps'.");
        }
        chartController.retrieve(steps);
    }
}