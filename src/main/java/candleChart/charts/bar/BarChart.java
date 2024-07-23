package candleChart.charts.bar;

import candleChart.charts.base.Chart;
import candleChart.charts.base.ChartBase;
import candleChart.charts.base.Range;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * BarChart es una clase que representa un gráfico de barras.
 * Extiende de la clase Chart y está diseñada para trabajar con objetos de tipo Double.
 */
public class BarChart extends Chart<Double> {
    private int relativePosition;
    private int elementWidth;
    private final Range range;
    private Color barColor;


    /**
     * Constructor de BarChart.
     *
     * @param chartBase gráfico base que proporciona la configuración común, los métodos compartidos y las
     *                  actualizaciones del gráfico.
     */
    public BarChart(ChartBase chartBase) {
        super(chartBase);

        relativePosition = 0;
        elementWidth = 1;
        range = new Range(0, 0);
        barColor = Color.BLUE;
    }


    /**
     * Dibuja el gráfico de barras en el contexto gráfico proporcionado.
     *
     * @param g2d el contexto gráfico donde se dibuja el gráfico.
     */
    @Override
    protected void drawChart(Graphics2D g2d) {
        List<Double> valuesList = getBuffer();
        g2d.setColor(barColor);

        if(!valuesList.isEmpty()) {
            // Itera sobre la lista y dibuja las barras.
            for (int i = 0; i < valuesList.size(); i++) {
                double value = valuesList.get(i);
                int maxValue = Math.max(yAxisPosition(0), yAxisPosition(value));
                int minValue = Math.min(yAxisPosition(0), yAxisPosition(value));
                int xPositionInit = xAxisPosition(i);
                int yPositionInit = value >= 0 ? minValue : yAxisPosition(0);
                int xPositionFinish = elementWidth;
                int yPositionFinish = (value >= 0 ? yAxisPosition(0) : maxValue) - yPositionInit;

                g2d.fillRect(xPositionInit, yPositionInit, xPositionFinish, yPositionFinish);
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
        double lowerRange = 0;
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
    protected void onRangeUpdate(@NotNull Range range) {
        this.range.setRange(range.getLowerRange(), range.getUpperRange());
    }


    /**
     * Actualiza la posición relativa de los elementos del gráfico.
     *
     * @param relativePosition la posición relativa actualizada.
     */
    @Override
    protected void onRelativePositionUpdate(int relativePosition) {
        this.relativePosition = relativePosition;
    }


    /**
     * Actualiza la anchura de los elementos del gráfico.
     *
     * @param elementWidth el ancho de los elementos actualizado.
     */
    @Override
    protected void onElementWidthUpdate(int elementWidth) {
        this.elementWidth = elementWidth;
    }


    /**
     * Obtiene el color de las barras del gráfico.
     *
     * @return el color de las barras del gráfico.
     */
    public Color getBarColor() {
        return barColor;
    }


    /**
     * Establece el color de las barras del gráfico.
     *
     * @param barColor el nuevo color de las barras del gráfico.
     */
    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }


    /**
     * Obtiene la posición en pixel en el eje X del índice del elemento proporcionado.
     *
     * @param elementIndex índice del elemento del que se quiere obtener la posición en el eje X.
     * @return la posición en el eje X del índice del elemento proporcionado.
     */
    private int xAxisPosition(int elementIndex) {
        return elementIndex * relativePosition - 1 - elementWidth / 2;
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
