package candleChart.charts.line;

import candleChart.charts.base.Chart;
import candleChart.charts.base.ChartBase;
import candleChart.charts.base.Range;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * LineChart es una clase que representa un gráfico de línea.
 * Extiende la clase Chart y está diseñada para trabajar con objetos de tipo Double.
 */
public class LineChart extends Chart<Double> {
    private int relativePosition;
    private final Range range;
    private Color lineColor;


    /**
     * Constructor de LineChart.
     *
     * @param chartBase gráfico base que proporciona la configuración común, los métodos compartidos y las
     *                  actualizaciones del gráfico.
     */
    public LineChart(ChartBase chartBase) {
        super(chartBase);

        relativePosition = 0;
        range = new Range(0, 0);
        lineColor = Color.YELLOW;
    }


    /**
     * Dibuja el gráfico de línea en el contexto gráfico proporcionado.
     *
     * @param g2d el contexto gráfico donde se dibuja el gráfico.
     */
    @Override
    protected void drawChart(Graphics2D g2d) {
        List<Double> valuesList = getBuffer();
        g2d.setColor(lineColor);

        if(!valuesList.isEmpty()) {
            // Itera sobre la lista y dibuja las líneas.
            for (int i = 0; i < valuesList.size() - 1; i++) {
                Double priceInit = valuesList.get(i);
                Double priceFinish = valuesList.get(i + 1);

                if(priceInit != null && priceFinish != null) {
                    g2d.drawLine(xAxisPosition(i), yAxisPosition(priceInit),
                            xAxisPosition(i +1), yAxisPosition(priceFinish));
                }
            }
        }
    }


    /**
     * Calcula el rango de precios en el que se mostrara el gráfico de línea. Este rango será combinado con el rango del
     * charBase si no se especifica lo contrario.
     *
     * @return un objeto Range que representa el rango de precios.
     */
    @Override
    protected Range calculateRange() {
        List<Double> valuesList = getBuffer();
        double lowerRange = Double.MAX_VALUE;
        double upperRange = Double.MIN_VALUE;

        for(Double value: valuesList) {
            lowerRange = Math.min(lowerRange, value);
            upperRange = Math.max(upperRange, value);
        }
        return new Range(lowerRange, upperRange);
    }


    /**
     * Proporciona la información que será representada en el eje X del gráfico.
     * Esta información podrá ser omitida desde la clase heredada Chart.
     *
     * @return una lista de cadena de texto que representa la información del eje X.
     */
    @Override
    protected List<String> xAxisInfo() {
        List<String> infoList = new ArrayList<>();

        for (double value: getBuffer()) {
            infoList.add(Double.toString(value));
        }
        return infoList;
    }


    /**
     * Actualiza el rango del gráfico. Este rango es el establecido en el chartBase, por lo que no tiene por qué
     * coincidir con el calculado en {@link #calculateRange()}, sino que será una combinación del rango de todos los
     * gráficos contenidos en el charBase.
     *
     * @param range el rango del gráfico actualizado.
     */
    @Override
    public void onRangeUpdate(@NotNull Range range) {
        this.range.setRange(range.getLowerRange(), range.getUpperRange());
    }


    /**
     * Actualiza la posición relativa de los elementos del gráfico.
     *
     * @param relativePosition la posición relativa actualizada.
     */
    @Override
    public void onRelativePositionUpdate(int relativePosition) {
        this.relativePosition = relativePosition;
    }


    /**
     * Actualiza la anchura de los elementos del gráfico.
     *
     * @param elementWidth el ancho de los elementos actualizado.
     */
    @Override
    public void onElementWidthUpdate(int elementWidth) {
    }


    /**
     * Obtiene el color de la línea del gráfico.
     *
     * @return el color de la línea del gráfico.
     */
    public Color getLineColor() {
        return lineColor;
    }


    /**
     * Establece el color de la línea del gráfico.
     *
     * @param lineColor el nuevo color de línnea del gráfico.
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }


    /**
     * Obtiene la posición en pixel en el eje X del índice del elemento proporcionado.
     *
     * @param elementIndex índice del elemento del que se quiere obtener la posición en el eje X.
     * @return la posición en el eje X del índice del elemento proporcionado.
     */
    private int xAxisPosition(int elementIndex) {
        return elementIndex * relativePosition -1;
    }


    /**
     * Obtiene la posición en pixel en el eje Y del valor proporcionado.
     *
     * @param value valor del que se quiere obtener la posición en el eje Y.
     * @return la posición en el eje Y del valor proporcionado.
     */
    private int yAxisPosition(double value) {
        return (int) ((range.getUpperRange() - value) / (range.difRange() / getHeight()));
    }
}
