package es.gtorresdev.jfxtradechart.charts.candle;

import es.gtorresdev.jfxtradechart.charts.Chart;
import es.gtorresdev.jfxtradechart.componets.SymbolDimension;
import es.gtorresdev.jfxtradechart.models.Range;
import es.gtorresdev.jfxtradechart.services.ChartManager;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Clase que representa un gráfico de velas japonesas (Candle Chart) para análisis financiero.
 * <p>
 * Esta clase extiende de {@link Chart} y utiliza objetos {@link Candle} para dibujar velas,
 * además de gestionar configuraciones relacionadas con el aspecto visual del gráfico.
 */
public class CandleChart extends Chart<Candle> {
    private final CandleChartConfig config;
    private BoundingBox bounds;
    private Range range;
    private double elementWidth;
    private double relativePosition;

    /**
     * Constructor por defecto que inicializa el gráfico con un {@link ChartManager} proporcionado y los valores
     * predeterminados de las dimensiones y configuraciones del gráfico.
     *
     * @param chartManager El administrador de gráficos.
     */
    public CandleChart(ChartManager chartManager) {
        super(chartManager);
        this.config = new CandleChartConfig();
        this.bounds = new BoundingBox(20, 20, 100, 100);
        this.range = new Range(10, 0);
        this.elementWidth = SymbolDimension.LARGE.getWidth();
        this.relativePosition = SymbolDimension.LARGE.getRelativePosition();
    }

    /**
     * Dibuja el gráfico de velas japonesas utilizando el {@link GraphicsContext}.
     * <p>
     * Este método itera sobre los elementos en el buffer de velas ({@link Candle}) y dibuja
     * tanto las líneas (mechas) como los cuerpos de las velas.
     */
    @Override
    public void draw() {
        List<Candle> buffer = getBuffer();

        if(!buffer.isEmpty()) {
            for (int i = 0; i < buffer.size(); i++) {
                drawCandleLine(getGraphicsContext(), buffer.get(i), i);
                drawCandleBody(getGraphicsContext(), buffer.get(i), i);
            }
        }
    }

    /**
     * Calcula el rango de valores (alto y bajo) del gráfico de velas basado en los datos actuales.
     *
     * @return el rango calculado como un objeto {@link Range}.
     */
    @Override
    protected Range calculateRange() {
        double upperRange = Double.MIN_VALUE;
        double lowerRange = Double.MAX_VALUE;

        for (Candle candle : getBuffer()) {
            upperRange = Math.max(candle.high(), upperRange);
            lowerRange = Math.min(candle.low(), lowerRange);
        }
        return new Range(upperRange, lowerRange);
    }

    /**
     * Obtiene la información de la vela en una posición específica del gráfico.
     *
     * @param x posición X en el gráfico.
     * @param y posición Y en el gráfico.
     * @return la información de la vela en formato {@link String} o una cadena vacía si no hay información.
     */
    @Override
    public String getInfoAtPosition(double x, double y) {
        if (x > bounds.getMinX() && x < bounds.getWidth() + bounds.getMinX()) {
            List<Candle> candles = getBuffer();
            int index = (int) Math.round((x - bounds.getMinX()) / relativePosition);

            if (candles.size() > index) {
                return candles.get(index).getInfo();
            }
        }
        return "";
    }

    /**
     * Actualiza las dimensiones del símbolo (ancho y posición relativa) basado en el nuevo valor de {@link SymbolDimension}.
     *
     * @param symbolDimension el objeto {@link SymbolDimension} que contiene las nuevas dimensiones del símbolo.
     */
    @Override
    public void onSymbolDimensionUpdate(@NotNull SymbolDimension symbolDimension) {
        relativePosition = symbolDimension.getRelativePosition();
        elementWidth = symbolDimension.getWidth();
    }

    /**
     * Actualiza el rango de valores en el que se muestra el gráfico. Este rango hace referencia al contexto global
     * donde el gráfico es dibujado, mientras que el rango obtenido en {@code calculateRange} hace referencia al rango
     * de valores de este gráfico.
     *
     * @param range el nuevo rango como un objeto {@link Range}.
     */
    @Override
    public void onRangeUpdate(@NotNull Range range) {
        this.range = range;
    }

    /**
     * Actualiza los límites del área de dibujo del gráfico.
     *
     * @param bounds un objeto {@link BoundingBox} que define los nuevos límites.
     */
    @Override
    public void onBoundsUpdate(BoundingBox bounds) {
        this.bounds = bounds;
    }

    /**
     * Calcula la posición X en el gráfico basada en el índice del elemento.
     *
     * @param elementIndex índice del elemento dentro del buffer de velas.
     * @return la posición X en el gráfico.
     */
    @Contract(pure = true)
    private double xAxisPosition(int elementIndex) {
        return elementIndex * relativePosition + bounds.getMinX();
    }

    /**
     * Calcula la posición Y en el gráfico basada en el valor de la vela.
     *
     * @param value el valor de la vela.
     * @return la posición Y en el gráfico.
     */
    private int yAxisPosition(double value) {
        return (int) ((range.getUpperRange() - value) / (range.getRangeWidth() / bounds.getHeight()) + bounds.getMinY());
    }

    /**
     * Dibuja la línea (mecha) de una vela en el gráfico.
     *
     * @param gc el contexto gráfico {@link GraphicsContext}.
     * @param candle el objeto {@link Candle} que representa la vela.
     * @param index el índice de la vela en el buffer.
     */
    private void drawCandleLine(@NotNull GraphicsContext gc, Candle candle, int index) {
        gc.setLineDashes(0);
        gc.setStroke(getCandleBorderColor(candle));
        gc.strokeLine(xAxisPosition(index), yAxisPosition(candle.high()), xAxisPosition(index), yAxisPosition(candle.low()));
    }


    /**
     * Dibuja el cuerpo de una vela en el gráfico.
     *
     * @param gc el contexto gráfico {@link GraphicsContext}.
     * @param candle el objeto {@link Candle} que representa la vela.
     * @param index el índice de la vela en el buffer.
     */
    private void drawCandleBody(@NotNull GraphicsContext gc, @NotNull Candle candle, int index) {
        double maxOpenOrClose = Math.max(candle.open(), candle.close());
        double minOpenOrClose = Math.min(candle.open(), candle.close());
        int rectY = yAxisPosition(maxOpenOrClose);
        int rectHeight = yAxisPosition(minOpenOrClose) - rectY;

        gc.setFill(getCandleFillColor(candle));
        gc.strokeRect(xAxisPosition(index) - (elementWidth / 2) + 0.5, rectY + 0.5,elementWidth - 1, rectHeight);
        gc.fillRect(xAxisPosition(index) - (elementWidth / 2) + 1, rectY + 1, elementWidth - 2, rectHeight - 1);
    }

    /**
     * Obtiene el color del borde de la vela dependiendo de si la vela es alcista o bajista.
     *
     * @param candle el objeto {@link Candle}.
     * @return el color del borde de la vela como {@link Color}.
     */
    private Color getCandleBorderColor(@NotNull Candle candle) {
        return candle.open() > candle.close() ? config.getDownWardCandleBorderColor() : config.getUpWardCandleBorderColor();
    }

    /**
     * Obtiene el color de relleno de la vela dependiendo de si la vela es alcista o bajista.
     *
     * @param candle el objeto {@link Candle}.
     * @return el color de relleno de la vela como {@link Color}.
     */
    private Color getCandleFillColor(@NotNull Candle candle) {
        return candle.open() > candle.close() ? config.getDownWardCandleFillColor() : config.getUpWardCandleFillColor();
    }
}
