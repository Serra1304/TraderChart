package candleChart;

import candleChart.controller.*;
import candleChart.data.Buffer;

import javax.swing.*;

public class TraderChart extends JPanel {

    private final ChartController chartController;

    public TraderChart() {
        chartController = new ChartController(this);
        //chartController.setSize(CandleSize.VERY_SMALL);
    }

    public void setBuffer(Buffer buffer) {
        chartController.setBuffer(buffer);
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

    public void setCandleSize(CandleSize candleSize) {
        chartController.setCandleSize(candleSize);
    }
    
    public CandleSize getCandleSize() {
        return chartController.getCandleSize();
    }
}