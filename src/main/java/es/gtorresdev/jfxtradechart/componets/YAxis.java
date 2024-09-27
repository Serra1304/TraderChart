package es.gtorresdev.jfxtradechart.componets;

import es.gtorresdev.jfxtradechart.models.Range;
import javafx.geometry.BoundingBox;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * La clase YAxis representa el eje Y de un gráfico en JavaFX, permitiendo la visualización de valores en dicho eje.
 * Se encarga de dibujar líneas de referencia y mostrar los valores correspondientes dentro de un rango especificado.
 */
public class YAxis {
    private final GraphicsContext graphicsContext;
    private Range range;

    private double gridSpacing;
    private BoundingBox bounds;
    private Pos alignment;
    private Color color;
    private String formattedNumber;

    /**
     * Constructor de la clase YAxis.
     *
     * @param graphicsContext El contexto gráfico donde se dibujará el eje Y.
     */
    public YAxis(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        range = new Range(0, 0);

        gridSpacing = 32;
        bounds = new BoundingBox(0, 0, 0, 0);
        alignment = Pos.BOTTOM_LEFT;
        color = Color.GRAY;
        formattedNumber = "%.5f";
    }

    /**
     * Dibuja el eje Y en el gráfico según las configuraciones actuales, como el rango, la alineación y el espaciado.
     */
    private void drawYAxis() {
        double startY = calculateStartPositionsY();

        graphicsContext.setStroke(color);

        for (double y = startY; y <= bounds.getHeight() + bounds.getMinY(); y += gridSpacing) {
            graphicsContext.strokeLine(bounds.getMinX(), y, bounds.getMinX() + 5, y);
            drawValues(y);
        }

    }

    /**
     * Dibuja los valores correspondientes al eje Y en la posición especificada.
     *
     * @param posY La posición en el eje Y donde se va a dibujar el valor.
     */
    private void drawValues(double posY) {
        double valuePerPixel = range.getRangeWidth() / bounds.getHeight();
        double value = range.getUpperRange() - (posY - bounds.getMinY()) * valuePerPixel;
        String number = String.format(formattedNumber, value);

        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillText(number, bounds.getMinX() + 10, posY + 3);
    }

    /**
     * Calcula la posición inicial en el eje Y en función de la alineación.
     *
     * @return La posición Y inicial desde la que se comenzará a dibujar.
     */
    private double calculateStartPositionsY() {
        return switch (alignment) {
            case TOP_LEFT, TOP_RIGHT, TOP_CENTER -> bounds.getMinY();
            case BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER -> bounds.getMinY() + Math.floor(bounds.getHeight() % gridSpacing);
            case CENTER -> bounds.getMinY() + Math.floor((bounds.getHeight() / 2) % gridSpacing);
            default -> 0.5;
        };
    }

    /**
     * Establece los límites (bounding box) donde se dibujará el eje Y.
     *
     * @param bounds Los límites en los que el eje Y será dibujado.
     */
    public void setBounds(BoundingBox bounds) {
        this.bounds = bounds;
    }

    /**
     * Obtiene los límites (bounding box) actuales del eje Y.
     *
     * @return Los límites del eje Y.
     */
    public BoundingBox getBounds() {
        return bounds;
    }

    /**
     * Establece la alineación del eje Y en relación con su área de dibujo.
     *
     * @param alignment La nueva alineación del eje Y.
     */
    public void setAlignment(Pos alignment) {
        this.alignment = alignment;
    }

    /**
     * Obtiene la alineación actual del eje Y.
     *
     * @return La alineación del eje Y.
     */
    public Pos getAlignment() {
        return alignment;
    }

    /**
     * Establece el espaciado entre las líneas de la cuadrícula del eje Y.
     *
     * @param gridSpacing El nuevo espaciado entre las líneas de la cuadrícula.
     */
    public void setGridSpacing(double gridSpacing) {
        this.gridSpacing = gridSpacing;
    }

    /**
     * Obtiene el espaciado actual entre las líneas de la cuadrícula.
     *
     * @return El espaciado entre las líneas de la cuadrícula.
     */
    public double getGridSpacing() {
        return gridSpacing;
    }

    /**
     * Obtiene el contexto gráfico (GraphicsContext) donde se dibuja el eje Y.
     *
     * @return El contexto gráfico.
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    /**
     * Establece el color de las líneas y los valores del eje Y.
     *
     * @param color El nuevo color a aplicar.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Obtiene el color actual utilizado para el eje Y.
     *
     * @return El color del eje Y.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Establece el rango de valores que se representarán en el eje Y.
     *
     * @param range El nuevo rango de valores.
     */
    public void setRange(Range range) {
        this.range = range;
    }


    /**
     * Obtiene el rango actual de valores que se están representando en el eje Y.
     *
     * @return El rango de valores del eje Y.
     */
    public Range getRange() {
        return range;
    }

    /**
     * Establece el formato para representar los valores numéricos en el eje Y.
     *
     * @param pattern El patrón de formato (por ejemplo, "%.2f").
     */
    public void setFormattedNumber(String pattern) {
        this.formattedNumber = pattern;
    }

    /**
     * Obtiene el formato actual utilizado para representar los valores numéricos.
     *
     * @return El patrón de formato de los números.
     */
    public String getFormattedNumber() {
        return formattedNumber;
    }

    /**
     * Fuerza una actualización/redibujado del eje Y.
     */
    public void update() {
        drawYAxis();
    }
}
