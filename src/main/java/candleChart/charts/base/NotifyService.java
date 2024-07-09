package candleChart.charts.base;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class NotifyService {
    private final ChartBase chartBase;
    private final List<Chart<?>> chartsListeners;

    protected NotifyService(ChartBase chartBase) {
        this.chartBase = chartBase;
        chartsListeners = new ArrayList<>();
    }

    protected void addChartListener(Chart<?> chart) {
        chartsListeners.add(chart);
    }

    protected Chart<?> getChartListener(int index) {
        return chartsListeners.get(index);
    }

    protected List<Chart<?>> getAllChartsListeners() {
        return chartsListeners;
    }

    protected void removedChartListener(Chart<?> chart) {
        chartsListeners.remove(chart);
    }

    // Notifica rango del eje Y
    protected void notifyRange() {
        Range range = chartBase.getRange();

        for (Chart<?> chart : chartsListeners) {
            chart.onRangeUpdate(range);
        }
        chartBase.getYAxis().OnPriceRange(range.getUpperRange(), range.getLowerRange());
    }

    // Notifica posicion relativa a los oyentes
    protected void notifyRelativePosition() {
        int relativePosition = chartBase.getRelativePosition();

        for (Chart<?> chart : chartsListeners) {
            chart.onRelativePositionUpdate(relativePosition);
        }
        chartBase.getXAxis().onRelativePosition(relativePosition);
    }

    // Notifica anchura de los elementos
    protected void notifyElementWidth() {
        int elementWidth = chartBase.getElementWidth();

        for (Chart<?> chart : chartsListeners) {
            chart.onElementWidthUpdate(elementWidth);
        }
    }

    // Notifica propiedades al grafico especificado en el parametro
    protected void notifyChart(@NotNull Chart<?> chart) {
        Range range = chartBase.getRange();
        int relativePosition = chartBase.getRelativePosition();
        int elementWidth = chartBase.getElementWidth();

        chart.onRangeUpdate(range);
        chart.onRelativePositionUpdate(relativePosition);
        chart.onElementWidthUpdate(elementWidth);
    }
}
