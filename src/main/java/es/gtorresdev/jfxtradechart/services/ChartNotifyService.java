package es.gtorresdev.jfxtradechart.services;

import es.gtorresdev.jfxtradechart.charts.Chart;
import es.gtorresdev.jfxtradechart.componets.SymbolDimension;
import es.gtorresdev.jfxtradechart.models.Range;
import javafx.geometry.BoundingBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio responsable de notificar cambios a los gráficos registrados. Esta clase gestiona la lista de gráficos que
 * escuchan eventos específicos y se encarga de notificarles sobre actualizaciones de dimensiones, rangos, y redibujado.
 */
public class ChartNotifyService {
    private final List<Chart<?>> chartListeners;

    /**
     * Crea una nueva instancia de {@code ChartNotifyService} e inicializa la
     * lista de gráficos que escucharán los eventos.
     */
    public ChartNotifyService() {
        chartListeners = new ArrayList<>();
    }

    /**
     * Añade un gráfico a la lista de gráficos que recibirán notificaciones.
     *
     * @param chart el gráfico que se va a registrar como listener.
     */
    protected void addChartListener(Chart<?> chart) {
        chartListeners.add(chart);
    }

    /**
     * Obtiene el gráfico registrado en el índice especificado.
     *
     * @param index el índice del gráfico en la lista.
     * @return el gráfico registrado en la posición indicada.
     */
    protected Chart<?> getChartListener(int index) {
        return chartListeners.get(index);
    }

    /**
     * Retorna la lista completa de gráficos registrados para recibir notificaciones.
     *
     * @return una lista de gráficos registrados.
     */
    protected List<Chart<?>> getAllChartsListeners() {
        return chartListeners;
    }

    /**
     * Elimina un gráfico de la lista de gráficos registrados.
     *
     * @param chart el gráfico que se desea eliminar.
     */
    protected void removeChartListener(Chart<?> chart) {
        chartListeners.remove(chart);
    }

    /**
     * Notifica a todos los gráficos registrados de un cambio en las dimensiones
     * del símbolo.
     *
     * @param symbolDimension las nuevas dimensiones del símbolo que se notifican a los gráficos.
     */
    protected void notifySymbolDimension(SymbolDimension symbolDimension) {
        for (Chart<?> chart : chartListeners) {
            chart.onSymbolDimensionUpdate(symbolDimension);
        }
    }

    /**
     * Notifica a todos los gráficos registrados de un cambio en los límites.
     *
     * @param bounds los nuevos límites que se notifican a los gráficos.
     */
    protected void notifyBounds(BoundingBox bounds) {
        for (Chart<?> chart : chartListeners) {
            chart.onBoundsUpdate(bounds);
        }
    }

    /**
     * Notifica a todos los gráficos registrados de un cambio en el rango.
     *
     * @param range el nuevo rango que se notificará a los gráficos.
     */
    protected void notifyRange(Range range) {
        for (Chart<?> chart : chartListeners) {
            chart.onRangeUpdate(range);
        }
    }

    /**
     * Solicita a todos los gráficos registrados que se redibujen.
     */
    protected void notifyRedraw() {
        for (Chart<?> chart : chartListeners) {
            chart.draw();
        }
    }
}
