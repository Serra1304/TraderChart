package candleChart.charts.base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class Chart<T> extends JPanel {
    private final ChartBase chartBase;
    private List<T> buffer;
    private boolean xAxisInfoVisibility;
    private boolean infoVisibility;
    private boolean rangeOverflowVisibility;
    private String title;
    private Range range;

    public Chart(ChartBase chartBase) {
        this.chartBase = chartBase;
        buffer = new ArrayList<>();
        xAxisInfoVisibility = true;
        infoVisibility = true;
        rangeOverflowVisibility = true;
        title = this.getClass().getSimpleName();

        chartConfig();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawChart(g2d);
    }

    // ignora los eventos del raton.
    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    public void updateChart() {
        this.repaint();
    }

    private void chartConfig() {
        setOpaque(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                repaint();
            }
        });
    }

    public List<T> getBuffer() {
        return buffer;
    }

    public void setBuffer(List<T> buffer) {
        this.buffer = buffer;
        range = calculateRange();

        if(rangeOverflowVisibility) {
            chartBase.mergeRange(range);
        }

        if (xAxisInfoVisibility) {
            chartBase.setXAxisInfo(xAxisInfo());
        }

        updateChart();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected String getElementInfo(int index) {
        String info;

        if (buffer.size() > index && index >= 0) {
            info = buffer.get(index).toString();
        } else {
            info = "";
        }
        return info;
    }

    public boolean isInfoVisibility() {
        return infoVisibility;
    }

    public void setInfoVisibility(boolean aFlag) {
        infoVisibility = aFlag;
    }

    public boolean getXAxisInfoVisible() {
        return xAxisInfoVisibility;
    }

    public void setXAxisInfoVisible(boolean aFlag) {
        xAxisInfoVisibility = aFlag;
    }

    public boolean isRangeOverflowVisibility() {
        return rangeOverflowVisibility;
    }

    public void setRangeOverflowVisibility(boolean aFlag) {
        rangeOverflowVisibility = aFlag;
        if(rangeOverflowVisibility && buffer != null) {
            chartBase.mergeRange(range);
        } else {
            chartBase.deleteRange(range);
        }
    }

    protected abstract void drawChart(Graphics2D g2d);
    protected abstract Range calculateRange();
    protected abstract List<String> xAxisInfo();
    protected abstract void onRangeUpdate(Range range);
    protected abstract void onRelativePositionUpdate(int relativePosition);
    protected abstract void onElementWidthUpdate(int elementWidth);
}
