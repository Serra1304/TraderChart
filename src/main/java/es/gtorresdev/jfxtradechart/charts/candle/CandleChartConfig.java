package es.gtorresdev.jfxtradechart.charts.candle;

import javafx.scene.paint.Color;


/**
 * La clase {@code CandleChartConfig} se encarga de almacenar y gestionar la configuración de los colores utilizados
 * en el gráfico de velas, tanto para velas alcistas como bajistas.
 */
public class CandleChartConfig {
    private Color upWardCandleBorderColor;
    private Color upWardCandleFillColor;
    private Color downWardCandleBorderColor;
    private Color downWardCandleFillColor;

    /**
     * Constructor por defecto que inicializa los colores de las velas (alcistas y bajistas).
     * Se establecen valores predeterminados para los colores de las velas:
     * <ul>
     *     <li>Vela alcista: Borde verde oscuro y relleno verde.</li>
     *     <li>Vela bajista: Borde rojo oscuro y relleno rojo.</li>
     * </ul>
     */
    public CandleChartConfig() {
        this.upWardCandleBorderColor = new Color(0, 0.5, 0, 1);
        this.upWardCandleFillColor = new Color(0, 1, 0, 1);
        this.downWardCandleBorderColor = new Color(0.5, 0, 0, 1);
        this.downWardCandleFillColor = new Color(1, 0, 0, 1);
    }

    /**
     * Obtiene el color del borde de las velas alcistas.
     *
     * @return el color del borde de las velas alcistas
     */
    public Color getUpWardCandleBorderColor() {
        return upWardCandleBorderColor;
    }

    /**
     * Establece el color del borde de las velas alcistas.
     *
     * @param upWardCandleBorderColor el nuevo color del borde de las velas alcistas
     */
    public void setUpWardCandleBorderColor(Color upWardCandleBorderColor) {
        this.upWardCandleBorderColor = upWardCandleBorderColor;
    }

    /**
     * Obtiene el color del relleno de las velas alcistas.
     *
     * @return el color del relleno de las velas alcistas
     */
    public Color getUpWardCandleFillColor() {
        return upWardCandleFillColor;
    }

    /**
     * Establece el color del relleno de las velas alcistas.
     *
     * @param upWardCandleFillColor el nuevo color del relleno de las velas alcistas
     */
    public void setUpWardCandleFillColor(Color upWardCandleFillColor) {
        this.upWardCandleFillColor = upWardCandleFillColor;
    }

    /**
     * Obtiene el color del borde de las velas bajistas.
     *
     * @return el color del borde de las velas bajistas
     */
    public Color getDownWardCandleBorderColor() {
        return downWardCandleBorderColor;
    }

    /**
     * Establece el color del borde de las velas bajistas.
     *
     * @param downWardCandleBorderColor el nuevo color del borde de las velas bajistas
     */
    public void setDownWardCandleBorderColor(Color downWardCandleBorderColor) {
        this.downWardCandleBorderColor = downWardCandleBorderColor;
    }

    /**
     * Obtiene el color del relleno de las velas bajistas.
     *
     * @return el color del relleno de las velas bajistas
     */
    public Color getDownWardCandleFillColor() {
        return downWardCandleFillColor;
    }

    /**
     * Establece el color del relleno de las velas bajistas.
     *
     * @param downWardCandleFillColor el nuevo color del relleno de las velas bajistas
     */
    public void setDownWardCandleFillColor(Color downWardCandleFillColor) {
        this.downWardCandleFillColor = downWardCandleFillColor;
    }
}
