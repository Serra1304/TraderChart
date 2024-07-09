package candleChart.charts.candle;

import candleChart.charts.base.Chart;
import candleChart.charts.base.ChartBase;
import candleChart.charts.base.Range;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * CandleChart es una clase que representa un gráfico de velas para mostrar datos financieros.
 * Extiende la clase Chart y está diseñada para trabajar con objetos de tipo Candle.
 */
public class CandleChart extends Chart<Candle> {
    private int relativePosition;
    private int elementWidth;
    private final Range range;

    private Color upWardCandleBorderColor;
    private Color upWardCandleFillColor;
    private Color downWardCandleBorderColor;
    private Color downWardCandleFillColor;


    /**
     * Constructor de CandleChart.
     *
     * @param chartBase gráfico base que proporciona la configuración común, los métodos compartidos y las
     *                  actualizaciones del gráfico.
     */
    public CandleChart(ChartBase chartBase) {
        super(chartBase);

        // Valores predeterminado de los parámetros del gráfico.
        relativePosition = 1;
        elementWidth = 1;
        range = new Range(0, 0);

        // Establece el color por defecto de las velas del gráfico.
        upWardCandleBorderColor = new Color(0, 127, 0);
        upWardCandleFillColor = new Color(0, 255, 0);
        downWardCandleBorderColor = new Color(127, 0, 0);
        downWardCandleFillColor = new Color(255, 0, 0);
    }


    /**
     * Dibuja el gráfico de velas en el contexto gráfico proporcionado.
     *
     * @param g2d el contexto gráfico donde se dibuja el gráfico.
     */
    @Override
    protected void drawChart(Graphics2D g2d) {
        List<Candle> candles = getBuffer();

        if(!candles.isEmpty()) {
            // Iteración sobre la lista de velas.
            for (int i = 0; i < candles.size(); i++) {
                // Se dibuja las lineas de las velas.
                g2d.setColor(candles.get(i).openPrice() > candles.get(i).closePrice()?  // Establece el color de la línea de la vela
                        downWardCandleBorderColor : upWardCandleBorderColor);
                g2d.drawLine(xAxisPosition(i), yAxisPosition(candles.get(i).highPrice()),   // Pinta la línea de la vela.
                        xAxisPosition(i), yAxisPosition(candles.get(i).lowPrice()));

                // Se dibuja el rectángulo de las velas.
                double maxOpenOrClose = Math.max(candles.get(i).openPrice(), candles.get(i).closePrice());
                double minOpenOrClose = Math.min(candles.get(i).openPrice(), candles.get(i).closePrice());
                int rectY = yAxisPosition(maxOpenOrClose);
                int rectHeight = yAxisPosition(minOpenOrClose) - rectY;
                g2d.drawRect(xAxisPosition(i) - (elementWidth / 2) , rectY,         // Dibuja el borde del cuerpo de la vela
                        elementWidth - 1, rectHeight);
                g2d.setColor(candles.get(i).openPrice() > candles.get(i).closePrice()? // Establece el color del relleno de la vela.
                        downWardCandleFillColor : upWardCandleFillColor);
                g2d.fillRect(xAxisPosition(i) - (elementWidth / 2) + 1,             // Pinta el relleno de la vela.
                        rectY, elementWidth - 2, rectHeight);
            }
        }
    }


    /**
     * Calcula el rango de precios en el que se mostrara el gráfico de velas. Este rango será combinado con el rango del
     * charBase si no se especifica lo contrario.
     *
     * @return un objeto Range que representa el rango de precios.
     */
    @Override
    protected Range calculateRange() {
        List<Candle> buffer = getBuffer();

        if (buffer.isEmpty()) {
            return new Range(0, 0);
        }

        double minPrice = buffer.get(0).lowPrice();
        double maxPrice = buffer.get(0).highPrice();

        for (Candle candle: buffer) {
            minPrice = Math.min(candle.lowPrice(), minPrice);
            maxPrice = Math.max(candle.highPrice(), maxPrice);
        }
        return new Range(minPrice, maxPrice);
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

        for(Candle candle: getBuffer()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm");
            String stringDateTime = candle.dateTime().format(formatter);

            infoList.add(stringDateTime);
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
        this.elementWidth = elementWidth;
    }


    /**
     * Obtiene el color del brode de una vela ascendente.
     *
     * @return el color del borde de una vela ascendente.
     */
    public Color getUpWardCandleBorderColor() {
        return upWardCandleBorderColor;
    }


    /**
     * Establece el color del borde de las velas ascendentes.
     *
     * @param upWardCandleBorderColor el nuevo color del borde de las velas ascendentes.
     */
    public void setUpWardCandleBorderColor(Color upWardCandleBorderColor) {
        this.upWardCandleBorderColor = upWardCandleBorderColor;
    }


    /**
     * Obtiene el color de relleno de las velas ascendentes.
     *
     * @return el color de relleno de las velas ascendentes.
     */
    public Color getUpWardCandleFillColor() {
        return upWardCandleFillColor;
    }


    /**
     * Establece el color de relleno de las velas ascendentes.
     *
     * @param upWardCandleFillColor en nuevo color de relleno de las velas ascendentes.
     */
    public void setUpWardCandleFillColor(Color upWardCandleFillColor) {
        this.upWardCandleFillColor = upWardCandleFillColor;
    }


    /**
     * Obtiene el color del borde de las velas descendentes.
     *
     * @return el color del borde de las velas descendentes.
     */
    public Color getDownWardCandleBorderColor() {
        return downWardCandleBorderColor;
    }


    /**
     * Establece el color del borde de las velas descendentes.
     *
     * @param downWardCandleBorderColor el nuevo color de las velas descendentes.
     */
    public void setDownWardCandleBorderColor(Color downWardCandleBorderColor) {
        this.downWardCandleBorderColor = downWardCandleBorderColor;
    }


    /**
     * Obtiene el color de relleno de las velas descendentes.
     *
     * @return el color de relleno de las velas descendentes.
     */
    public Color getDownWardCandleFillColor() {
        return downWardCandleFillColor;
    }


    /**
     * Establece el color de relleno de las velas descendentes.
     *
     * @param downWardCandleFillColor el nuevo color de relleno de las velas descendentes.
     */
    public void setDownWardCandleFillColor(Color downWardCandleFillColor) {
        this.downWardCandleFillColor = downWardCandleFillColor;
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
