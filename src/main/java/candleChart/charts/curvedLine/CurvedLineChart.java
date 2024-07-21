package candleChart.charts.curvedLine;

import candleChart.charts.base.Chart;
import candleChart.charts.base.ChartBase;
import candleChart.charts.base.Range;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;


/**
 * CurvedLineChart es una clase que representa un gráfico de línea con aristas suaves.
 * Extiende de la clase Chart y está diseñada para trabajar con objetos de tipo Double.
 */
public class CurvedLineChart extends Chart<Double> {
    private int relativePosition;
    private final Range range;
    private Color lineColor;


    /**
     * Constructor de CurvedLineChart.
     *
     * @param chartBase gráfico base que proporciona la configuración común, los métodos compartidos y las
     *                  actualizaciones del gráfico.
     */
    public CurvedLineChart(ChartBase chartBase) {
        super(chartBase);

        relativePosition = 0;
        range = new Range(0, 0);
        lineColor = Color.YELLOW;
    }


    /**
     * Dibuja el gráfico de líneas curvas en el contexto gráfico proporcionado.
     *
     * @param g2d el contexto gráfico donde se dibuja el gráfico.
     */
    @Override
    protected void drawChart(Graphics2D g2d) {
        List<Double> valuesList = getBuffer();
        g2d.setColor(lineColor);

        Path2D p2d = new Path2D.Double();
        p2d.moveTo(xAxisPosition(0), yAxisPosition(valuesList.get(0)));

        // Itera sobre los elementos para trazar el gráfico.
        for (int i = 0; i < valuesList.size() -1; i++) {
            double xPoint0 = xAxisPosition(i - 1);
            double xPoint1 = xAxisPosition(i);
            double xPoint2 = xAxisPosition(i + 1);

            double yPoint0 = i > 0 ? yAxisPosition(valuesList.get(i - 1)) : 0;
            double yPoint1 = yAxisPosition(valuesList.get(i));
            double yPoint2 = yAxisPosition(valuesList.get(i + 1));
            double yPoint3 = i < valuesList.size() - 2 ? yAxisPosition(valuesList.get(i + 2)) : yPoint2;

            double ctrlX0;
            double ctrlY0;
            double ctrlX1;
            double ctrlY1;

            double max = Math.max(yPoint1, yPoint2);
            double min = Math.min(yPoint1, yPoint2);

            double backSlope = i > 0 ? slope(yPoint0, yPoint1) : 0;
            double currentSlope = slope(yPoint1, yPoint2);
            double forwardSlope = slope(yPoint2, yPoint3);

            String slopeContinuity = i == 0 ? "ContinueToChange" : slopeContinuity(backSlope, currentSlope, forwardSlope);

            switch (slopeContinuity) {
                // Trazo en que la línea proviene de un cambio de dirección y pretende continuar o volver a cambiar de dirección.
                case "ChangeToChange", "ChangeToContinue":
                    ctrlX0 = xPoint1 + relativePosition * 0.5;
                    ctrlY0 = Math.min(Math.max(yPoint0 + backSlope * (ctrlX0 - xPoint0), min), max);
                    ctrlX1 = xPoint2 - relativePosition * 0.5;
                    ctrlY1 = Math.min(Math.max(yPoint2 + forwardSlope * (ctrlX1 - xPoint2), min), max);
                    break;

                // Trazo en que la línea proviene de la misma dirección y pretende hacer un cambio de dirección.
                case "ContinueToChange":
                    ctrlX0 = xPoint1;
                    ctrlY0 = yPoint1;
                    ctrlX1 = xPoint1 + relativePosition * 0.5;
                    ctrlY1 = Math.min(Math.max(yPoint2 + forwardSlope * (ctrlX1 - xPoint2), min), max);
                    break;

                // Trazo en que la línea proviene y pretende seguir en la misma dirección.
                case "ContinueToContinue":
                    ctrlX0 = xPoint1;
                    ctrlY0 = yPoint1;
                    ctrlX1 = xPoint1 + relativePosition * 0.3;
                    ctrlY1 = Math.min(Math.max(yPoint2 + forwardSlope * (ctrlX1 - xPoint2), min), max);
                    break;

                // Trazo por defecto (Generalmente el ultimo trazo del grafico).
                default:
                    ctrlX0 = xPoint1 + relativePosition * 0.5;
                    ctrlY0 = Math.min(Math.max(yPoint0 + backSlope * (ctrlX0 - xPoint0), min), max);
                    ctrlX1 = xPoint2;
                    ctrlY1 = yPoint2;
            }
            p2d.curveTo(ctrlX0, ctrlY0, ctrlX1, ctrlY1, xPoint2, yPoint2);
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.draw(p2d);
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

        for (Double value : valuesList) {
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

        for (double value : getBuffer()) {
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
        return elementIndex * relativePosition - 1;
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


    /**
     * Obtiene le pendiente entre los valores y0 e y1.
     *
     * @param y0 posición inicial de la línea en el eje Y.
     * @param y1 posición final de la línea en el eje Y.
     * @return el factor de la pendiente entre los puntos proporcionados.
     */
    private double slope(double y0, double y1) {
        return (y1 - y0) / relativePosition;
    }


    /**
     * Obtiene el trazado que pretende seguir la línea. Este trazado es utilizado para los cálculos de los puntos de
     * control de las curvas de las líneas a trazar.
     *
     * @param backSlope dirección del trazo de la linea anterior.
     * @param currentSlope dirección del trazo de la línea actual.
     * @param forwardSlope dirección del trazo de la siguiente línea.
     * @return trazado que pretende seguir la línea del grafico.
     */
    private String slopeContinuity(double backSlope, double currentSlope, double forwardSlope) {
        if (backSlope > 0 && currentSlope < 0 && forwardSlope > 0 || backSlope < 0 && currentSlope > 0 && forwardSlope < 0) {
            return "ChangeToChange";
        }

        if (backSlope > 0 && currentSlope < 0 && forwardSlope < 0 || backSlope < 0 && currentSlope > 0 && forwardSlope > 0) {
            return "ChangeToContinue";
        }

        if (backSlope > 0 && currentSlope > 0 && forwardSlope < 0 || backSlope < 0 && currentSlope < 0 && forwardSlope > 0) {
            return "ContinueToChange";
        }

        if (backSlope > 0 && currentSlope > 0 && forwardSlope > 0 || backSlope < 0 && currentSlope < 0 && forwardSlope < 0) {
            return "ContinueToContinue";
        }
        return "Indeterminate";
    }
}

