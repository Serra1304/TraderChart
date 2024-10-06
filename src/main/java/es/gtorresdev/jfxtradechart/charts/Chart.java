package es.gtorresdev.jfxtradechart.charts;

import es.gtorresdev.jfxtradechart.models.OverflowRange;
import es.gtorresdev.jfxtradechart.componets.SymbolDimension;
import es.gtorresdev.jfxtradechart.models.Range;
import es.gtorresdev.jfxtradechart.services.ChartManager;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que define la estructura básica de un gráfico genérico en la aplicación.
 * Proporciona métodos comunes para gestionar el contexto gráfico, el buffer de elementos
 * y el rango de visualización, además de la interacción con el {@link ChartManager}.
 *
 * @param <T> El tipo de elementos que se agregarán al gráfico.
 */
public abstract class Chart<T> {
    private GraphicsContext graphicsContext;
    private final List<T> buffer;
    private final ChartManager chartManager;
    private OverflowRange overflowRange;
    private final Range localRange;

    /**
     * Constructor que inicializa el gráfico con un {@link ChartManager}.
     *
     * @param chartManager El administrador de gráficos.
     */
    public Chart(ChartManager chartManager) {
        this.chartManager = chartManager;
        this.buffer = new ArrayList<>();
        this.overflowRange = OverflowRange.DYNAMIC;
        this.localRange = new Range();
    }

    /**
     * Agrega un elemento al buffer del gráfico y actualiza el rango.
     *
     * @param value El elemento a agregar al buffer.
     */
    public void addToBuffer(T value) {
        buffer.add(value);
        rangeUpdate();
    }

    /**
     * Agrega una lista de elementos al buffer del gráfico y actualiza el rango.
     *
     * @param values Los elementos a agregar al buffer.
     */
    public void addAllToBuffer(List<T> values) {
        buffer.addAll(values);
        rangeUpdate();
    }

    /**
     * Reemplaza todos los elementos actuales en el buffer con una nueva lista de elementos
     * y actualiza el rango.
     *
     * @param newValues Los nuevos elementos que reemplazarán a los existentes en el buffer.
     */
    public void replaceBuffer(List<T> newValues) {
        buffer.clear();
        buffer.addAll(newValues);
        rangeUpdate();
    }

    /**
     * Devuelve el buffer de elementos actualmente en el gráfico.
     *
     * @return Una lista de elementos en el buffer.
     */
    public List<T> getBuffer() {
        return buffer;
    }

    /**
     * Establece el contexto gráfico para dibujar el gráfico en el canvas.
     *
     * @param graphicsContext El contexto gráfico de JavaFX.
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * Devuelve el contexto gráfico actual.
     *
     * @return El contexto gráfico.
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }


    /**
     * Devuelve el valor actual del tipo de desbordamiento de rango para el gráfico.
     *
     * @return El valor de {@link OverflowRange}.
     */
    public OverflowRange getOverflowRange() {
        return overflowRange;
    }

    /**
     * Establece el tipo de desbordamiento de rango para el gráfico y actualiza el rango
     * si es necesario.
     *
     * @param overflowRange El nuevo valor de {@link OverflowRange}.
     */
    public void setOverflowRange(OverflowRange overflowRange) {
        this.overflowRange = overflowRange;
        
        if (overflowRange == OverflowRange.DYNAMIC) {
            chartManager.mergeRange(localRange);
        } else {
            chartManager.removeRange(localRange);
        }
    }

    /**
     * Actualiza el rango local del gráfico y lo sincroniza con el {@link ChartManager}
     * si el tipo de desbordamiento es dinámico.
     */
    private void rangeUpdate() {
        localRange.setRange(calculateRange());

        if (chartManager != null && overflowRange == OverflowRange.DYNAMIC) {
            chartManager.mergeRange(localRange);
        }
    }

    /**
     * Método abstracto para dibujar el contenido del gráfico. Cada gráfico específico debe
     * implementar su lógica de renderización.
     */
    public abstract void draw();

    /**
     * Método abstracto para calcular el rango de valores a partir de los datos contenidos
     * en el gráfico.
     *
     * @return El rango de valores representado por el gráfico.
     */
    protected abstract Range calculateRange();

    /**
     * Método abstracto que devuelve la información de un elemento del gráfico en una posición
     * específica del canvas.
     *
     * @param x La coordenada X en el canvas.
     * @param y La coordenada Y en el canvas.
     * @return La información asociada con la posición (x, y).
     */
    public abstract String getInfoAtPosition(double x, double y);

    /**
     * Método abstracto para actualizar las dimensiones de simboles del gráfico.
     *
     * @param symbolDimension Las nuevas dimensiones simbólicas del gráfico.
     */
    public abstract void onSymbolDimensionUpdate(SymbolDimension symbolDimension);

    /**
     * Método abstracto para actualizar el rango del gráfico basado en un rango global.
     *
     * @param globalRange El rango global actualizado.
     */
    public abstract void onRangeUpdate(Range globalRange);

    /**
     * Método abstracto para actualizar los límites del gráfico.
     *
     * @param bounds Los nuevos límites del gráfico.
     */
    public abstract void onBoundsUpdate(BoundingBox bounds);
}
