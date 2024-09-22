package es.gtorresdev.jfxtradechart;

import javafx.geometry.BoundingBox;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * La clase {@code Grid} representa una cuadrícula que se dibuja sobre un {@link GraphicsContext} en JavaFX.
 * Proporciona funcionalidad para personalizar la visibilidad y el espaciado de las líneas horizontales y verticales,
 * así como la alineación de la cuadrícula dentro de un área definida por {@link BoundingBox}. Además, también
 * permite la personalización de las líneas de la cuadrícula, como su grosor, color o estilo.
 */
public class Grid {
    private final GraphicsContext graphContext;

    private boolean gridVisible;
    private boolean horizontalLineVisible;
    private boolean verticalLineVisible;

    private double gridHorizontalSpacing;
    private double gridVerticalSpacing;
    private BoundingBox bounds;
    private Pos alignment;

    private double lineWidth;
    private double[] dashPattern;
    private Color gridColor;

    /**
     * Constructor para crear una nueva instancia de {@code Grid}.
     *
     * @param graphContext el contexto gráfico en el que se dibuja la cuadrícula.
     */
    public Grid(GraphicsContext graphContext) {
        this.graphContext = graphContext;

        gridVisible = true;
        horizontalLineVisible = true;
        verticalLineVisible = true;

        gridHorizontalSpacing = 32;
        gridVerticalSpacing = 32;
        bounds = new BoundingBox(0, 0, 0, 0);
        alignment = Pos.BOTTOM_LEFT;

        lineWidth = 1;
        dashPattern = new double[]{5, 4};
        gridColor = Color.GRAY;

        drawGrid();
    }

    /**
     * Dibuja la cuadrícula en el contexto gráfico según la configuración actual.
     */
    private void drawGrid() {
        double[] startPositions = calculateStartPositions();

        graphContext.setLineWidth(lineWidth);
        graphContext.setLineDashes(dashPattern);
        graphContext.setStroke(gridColor);

        if (gridVisible) {
            if (verticalLineVisible) {
                drawVerticalLines(startPositions[0]);
            }
            if (horizontalLineVisible) {
                drawHorizontalLines(startPositions[1]);
            }
        }
    }

    /**
     * Dibuja las líneas horizontales de la cuadrícula.
     *
     * @param startY la posición inicial en el eje Y desde donde comenzar a dibujar.
     */
    private void drawHorizontalLines(double startY) {
        for (double i = startY; i < bounds.getHeight() + bounds.getMinY(); i += gridVerticalSpacing) {
            graphContext.strokeLine(bounds.getMinX() + 1, i, bounds.getWidth() - 1, i);
        }
    }

    /**
     * Dibuja las líneas verticales de la cuadrícula.
     *
     * @param startX la posición inicial en el eje X desde donde comenzar a dibujar.
     */
    private void drawVerticalLines(double startX) {
        for (double i = startX; i < bounds.getWidth() + bounds.getMinX(); i += gridHorizontalSpacing) {
            graphContext.strokeLine(i, bounds.getMinY() + 1, i, bounds.getHeight() + bounds.getMinY() - 1);
        }
    }

    /**
     * Calcula las posiciones iniciales en los ejes X e Y según la alineación especificada.
     *
     * @return un arreglo con las posiciones iniciales en X e Y.
     */
    @Contract(" -> new")
    private double @NotNull [] calculateStartPositions() {
        double startX;
        double startY;

        switch (alignment) {
            case TOP_LEFT -> {
                startX = bounds.getMinX() + gridHorizontalSpacing;
                startY = bounds.getMinY() + gridVerticalSpacing;
            }
            case TOP_RIGHT -> {
                startX = bounds.getWidth() % gridHorizontalSpacing == 0 ?
                        gridHorizontalSpacing + bounds.getMinX() :
                        bounds.getWidth() % gridHorizontalSpacing + bounds.getMinX();
                startY = bounds.getMinY() + gridVerticalSpacing;
            }
            case TOP_CENTER -> {
                startX = Math.floor((bounds.getWidth() / 2) % gridHorizontalSpacing) == 0 ?
                        bounds.getMinX() + gridHorizontalSpacing :
                        Math.floor((bounds.getWidth() / 2) % gridHorizontalSpacing) + bounds.getMinX();
                startY = bounds.getMinY() + gridVerticalSpacing;
            }
            case BOTTOM_LEFT -> {
                startX = bounds.getMinX() + gridHorizontalSpacing;
                startY = bounds.getHeight() % gridVerticalSpacing == 0 ?
                        gridVerticalSpacing + bounds.getMinY() :
                        bounds.getHeight() % gridVerticalSpacing + bounds.getMinY();
            }
            case BOTTOM_RIGHT -> {
                startX = bounds.getWidth() % gridHorizontalSpacing == 0 ?
                        gridHorizontalSpacing + bounds.getMinX() :
                        bounds.getWidth() % gridHorizontalSpacing + bounds.getMinX();
                startY = bounds.getHeight() % gridVerticalSpacing == 0 ?
                        gridVerticalSpacing + bounds.getMinY() :
                        bounds.getHeight() % gridVerticalSpacing + bounds.getMinY();
            }
            case BOTTOM_CENTER -> {
                startX = Math.floor((bounds.getWidth() / 2) % gridHorizontalSpacing) == 0 ?
                        bounds.getMinX() + gridHorizontalSpacing :
                        Math.floor((bounds.getWidth() / 2) % gridHorizontalSpacing) + bounds.getMinX();
                startY = bounds.getHeight() % gridVerticalSpacing == 0 ?
                        gridVerticalSpacing + bounds.getMinY() :
                        bounds.getHeight() % gridVerticalSpacing + bounds.getMinY();
            }
            case CENTER -> {
                startX = Math.floor((bounds.getWidth() / 2) % gridHorizontalSpacing) == 0 ?
                        bounds.getMinX() + gridHorizontalSpacing :
                        Math.floor((bounds.getWidth() / 2) % gridHorizontalSpacing) + bounds.getMinX();
                startY = Math.floor((bounds.getHeight() / 2) % gridVerticalSpacing) == 0 ?
                        gridVerticalSpacing + bounds.getMinY() :
                        Math.floor((bounds.getHeight() / 2) % gridVerticalSpacing) + bounds.getMinY();
            }
            default -> {
                startX = 0.5;
                startY = 0.5;
            }
        }
        return new double[]{startX, startY};
    }

    /**
     * Establece los límites de la cuadrícula.
     *
     * @param bounds el {@link BoundingBox} que define el área donde se dibuja la cuadrícula.
     */
    public void setBounds(BoundingBox bounds) {
        this.bounds = bounds;
        drawGrid();
    }

    /**
     * Obtiene los límites actuales de la cuadrícula.
     *
     * @return los límites como un {@link BoundingBox}.
     */
    public BoundingBox getBounds() {
        return bounds;
    }

    /**
     * Muestra u oculta la cuadrícula.
     *
     * @param visible si es {@code true}, la cuadrícula será visible.
     */
    public void setGridVisible(boolean visible) {
        gridVisible = visible;
        drawGrid();
    }

    /**
     * Verifica si la cuadrícula es visible.
     *
     * @return {@code true} si la cuadrícula es visible, de lo contrario {@code false}.
     */
    public boolean isGridVisible() {
        return gridVisible;
    }


    /**
     * Muestra u oculta las líneas horizontales de la cuadrícula.
     *
     * @param visible si es {@code true}, las líneas horizontales serán visibles.
     */
    public void setHorizontalLineVisible(boolean visible) {
        horizontalLineVisible = visible;
        drawGrid();
    }

    /**
     * Verifica si las líneas horizontales son visibles.
     *
     * @return {@code true} si las líneas horizontales son visibles, de lo contrario {@code false}.
     */
    public boolean isHorizontalLineVisible() {
        return horizontalLineVisible;
    }

    /**
     * Muestra u oculta las líneas verticales de la cuadrícula.
     *
     * @param visible si es {@code true}, las líneas horizontales serán visibles.
     */
    public void setVerticalLineVisible(boolean visible) {
        verticalLineVisible = visible;
        drawGrid();
    }

    /**
     * Verifica si las líneas verticales son visibles.
     *
     * @return {@code true} si las líneas verticales son visibles, de lo contrario {@code false}.
     */
    public boolean isVerticalLineVisible() {
        return verticalLineVisible;
    }


    /**
     * Establece la alineación de la cuadrícula dentro del área definida.
     *
     * @param alignment la alineación como un {@link Pos}.
     */
    public void setAlignment(Pos alignment) {
        this.alignment = alignment;
        drawGrid();
    }

    /**
     * Obtiene la alineación actual de la cuadrícula.
     *
     * @return la alineación como un {@link Pos}.
     */
    public Pos getAlignment() {
        return alignment;
    }

    /**
     * Establece el espaciado horizontal de la cuadrícula.
     *
     * @param gridHorizontalSpacing el espaciado horizontal en píxeles.
     */
    public void setGridHorizontalSpacing(double gridHorizontalSpacing) {
        if (gridHorizontalSpacing > 0) {
            this.gridHorizontalSpacing = gridHorizontalSpacing;
            drawGrid();
        }
    }

    /**
     * Obtiene el espaciado horizontal de la cuadrícula.
     *
     * @return el espaciado horizontal en píxeles.
     */
    public double getGridHorizontalSpacing() {
        return gridHorizontalSpacing;
    }

    /**
     * Establece el espaciado vertical de la cuadrícula.
     *
     * @param gridVerticalSpacing el espaciado vertical en píxeles.
     */
    public void setGridVerticalSpacing(double gridVerticalSpacing) {
        if (gridVerticalSpacing > 0) {
            this.gridVerticalSpacing = gridVerticalSpacing;
            drawGrid();
        }
    }

    /**
     * Obtiene el espaciado vertical de la cuadrícula.
     *
     * @return el espaciado vertical en píxeles.
     */
    public double getGridVerticalSpacing() {
        return gridVerticalSpacing;
    }

    /**
     * Obtiene el {@link GraphicsContext} asociado donde se dibuja la cuadrícula.
     *
     * @return el contexto gráfico.
     */
    public GraphicsContext getGraphContext() {
        return graphContext;
    }

    /**
     * Establece el formato de las líneas de la cuadrícula.
     *
     * @param lineWidth el grosor de las líneas de la cuadrícula.
     * @param dashPattern el estilo de las líneas.
     * @param color el color de las líneas.
     */
    public void gridFormattedLine(double lineWidth, double[] dashPattern, Color color) {
        this.lineWidth = lineWidth;
        this.dashPattern = dashPattern;
        this.gridColor = color;

        drawGrid();
    }

    /**
     * Establece el color de las líneas de la cuadrícula.
     *
     * @param color el color de las líneas de la cuadrícula.
     */
    public void setColor(Color color) {
        this.gridColor = color;
        drawGrid();
    }

    /**
     * Obtiene el color actual de las líneas de la cuadrícula.
     *
     * @return el color actual de las líneas.
     */
    public Color getColor() {
        return gridColor;
    }
}
