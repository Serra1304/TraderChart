package candleChart.charts.base;

import candleChart.charts.util.Cursor;
import candleChart.charts.util.Grid;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ChartUiManager {
    private final JPanel parent;
    private final JPanel chartArea;
    private final Grid grid;
    private final Cursor cursor;

    private final GridBagConstraints gbc;

    public ChartUiManager(JPanel parent) {
        this.parent = parent;
        chartArea = new JPanel();
        grid = new Grid();
        cursor = new Cursor();

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        setupUI();
    }

    private void setupUI() {
        parent.setBackground(Color.BLACK);
        parent.setOpaque(true);
        parent.setDoubleBuffered(true);
        parent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        parent.setLayout(new GridBagLayout());
        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                parent.repaint();
            }
        });

        setupChartArea();
    }

    private void setupChartArea() {
        chartArea.setLayout(new OverlayLayout(chartArea));
        chartArea.setOpaque(false);
        chartArea.setBorder(new LineBorder(Color.GRAY));
        chartArea.add(cursor);
        chartArea.add(grid);
        chartArea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                chartArea.repaint();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        parent.add(chartArea, gbc);
    }

    protected void setXAxis(XAxis xAxis) {
        cursor.addCursorListener(xAxis);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        parent.add(xAxis, gbc);
    }

    protected void setYAxis(YAxis yAxis) {
        cursor.addCursorListener(yAxis);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        parent.add(yAxis, gbc);
    }

    protected void setInfo(Info info) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        parent.add(info, gbc);
    }

    protected Grid getGrid() {
        return grid;
    }

    protected Cursor getCursor() {
        return cursor;
    }

    protected JPanel getChartArea() {
        return chartArea;
    }
}
