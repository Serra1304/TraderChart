package candleChart.charts.base;

import candleChart.charts.util.CursorListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChartBase extends JPanel implements CursorListener {
    private XAxis xAxis;
    private YAxis yAxis;
    private Info info;

    private final ChartUiManager chartUiManager;
    private final NotifyService notifyService;
    private final List<Range> rangeList = new ArrayList<>();
    private double lowerRange = Double.MAX_VALUE;
    private double upperRange = Double.MIN_VALUE;
    private final int relativePosition = ElementDimension.SMALL.getRelativePosition();
    private final int elementWidth = ElementDimension.SMALL.getWidth();
    private String title;

    public ChartBase() {
        chartUiManager = new ChartUiManager(this);
        chartUiManager.getCursor().addCursorListener(this);

        notifyService = new NotifyService(this);

        xAxis = new XAxis();
        yAxis = new YAxis();
        info = new Info();

        title = "";
    }

    // Añadir grafico
    public void addChart(Chart<?> chart) {
        chartUiManager.getChartArea().add(chart, 0);

        notifyService.addChartListener(chart);
        notifyService.notifyChart(chart);
    }

    // Eliminar grafico
    public void removeChart(Chart<?> chart) {
        chartUiManager.getChartArea().remove(chart);

        notifyService.removedChartListener(chart);
    }

    public XAxis getXAxis() {
        return xAxis;
    }

    public void setXAxis(XAxis xAxis) {
        this.xAxis = xAxis;
        this.xAxis.onRelativePosition(relativePosition);
        chartUiManager.setXAxis(xAxis);
    }

    public YAxis getYAxis() {
        return yAxis;
    }

    public void setYAxis(YAxis yAxis) {
        this.yAxis = yAxis;
        chartUiManager.setYAxis(yAxis);
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
        this.info.setInfo(title);
        chartUiManager.setInfo(info);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        info.setInfo(title);
    }

    public boolean isGridVisible() {
        return chartUiManager.getGrid().isVisible();
    }

    public void setGridVisible(boolean aFlag) {
        chartUiManager.getGrid().setVisible(aFlag);
    }

    public boolean isCursorVisible() {
        return chartUiManager.getCursor().isCursorVisible();
    }

    public void setCursorVisible(boolean aFlag) {
        chartUiManager.getCursor().setCursorVisible(aFlag);
    }

    protected void setXAxisInfo(List<String> infoList) {
        xAxis.onXAxisInfo(infoList);
    }

    protected void mergeRange(Range range) {
        rangeList.add(range);
        lowerRange = Double.MAX_VALUE;
        upperRange = Double.MIN_VALUE;
            for (Range objRange: rangeList) {
                lowerRange = Math.min(lowerRange, objRange.getLowerRange());
                upperRange = Math.max(upperRange, objRange.getUpperRange());
            }
        notifyService.notifyRange();
    }

    protected void deleteRange(Range range) {
        rangeList.remove(range);

        lowerRange = Double.MAX_VALUE;
        upperRange = Double.MIN_VALUE;
        for (Range objRange: rangeList) {
            lowerRange = Math.min(lowerRange, objRange.getLowerRange());
            upperRange = Math.max(upperRange, objRange.getUpperRange());
        }
        notifyService.notifyRange();
    }

    protected Range getRange() {
        return new Range(lowerRange, upperRange);
    }

    protected int getRelativePosition() {
        return relativePosition;
    }

    protected int getElementWidth() {
        return elementWidth;
    }

    @Override
    public void cursorMoved(int locationX, int locationY) {
        int index = (locationX + relativePosition / 2) / relativePosition;
        String info = "";
        if(!title.isEmpty()) {
            info = title + "     ";
        }

        for (Chart<?> chart: notifyService.getAllChartsListeners()) {
            if (chart.isInfoVisibility()) {
                info = info + chart.getTitle() + ": " + chart.getElementInfo(index) + "     ";
            }
        }
        this.info.setInfo(info);
    }

    @Override
    public void cursorVisibility(boolean aFlag) {
        if(!aFlag) {
            info.setInfo(title);
        }
    }
}
