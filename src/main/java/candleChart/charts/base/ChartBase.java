package candleChart.charts.base;

import candleChart.charts.util.Grid;
import candleChart.charts.util.Cursor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ChartBase extends JPanel {

    private final JPanel chartArea;
    private final Grid grid;
    private final Cursor cursor;

    private XAxis xAxis;
    private YAxis yAxis;
    private Info info;

    GridBagConstraints gbc = new GridBagConstraints();

    public ChartBase() {
        chartArea = new JPanel();
        grid = new Grid();
        cursor = new Cursor();

        setBackground(Color.BLACK);
        setOpaque(true);
        //setDoubleBuffered(true);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());

        gbc.fill = GridBagConstraints.BOTH;

        setChartArea();
        setXAxis(new XAxis());      //TODO Implementacion provisional
        setYAxis(new YAxis());      //TODO Implementacion provisional
    }

    private void setChartArea() {
        chartArea.setLayout(new OverlayLayout(chartArea));
        chartArea.setOpaque(false);
        chartArea.setBorder(new LineBorder(Color.GRAY));
        chartArea.add(cursor);
        chartArea.add(grid);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(chartArea, gbc);
    }


    public void setGridVisible(boolean aFlag) {
        grid.setVisible(aFlag);
    }

    public boolean isGridVisible() {
        return grid.isVisible();
    }

    public void setXAxis(XAxis xAxis) {
        this.xAxis = xAxis;
        cursor.addCursorListener(this.xAxis);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        add(this.xAxis, gbc);
    }

    public void setYAxis(YAxis yAxis) {
        this.yAxis = yAxis;
        cursor.addCursorListener(this.yAxis);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(this.yAxis, gbc);
    }

    public void setInfo(Info info) {
        this.info = info;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(this.info, gbc);
    }

    public void setChart(Chart chart) {
        if(xAxis != null) {
            chart.addListener(xAxis);
        }

        if(yAxis != null) {
            chart.addListener(yAxis);
        }

        if(info != null) {
            chart.setCharInfo(info);
        }

        cursor.addCursorListener(chart);
        chartArea.add(chart.getGraphicView(), 0);
    }
}
