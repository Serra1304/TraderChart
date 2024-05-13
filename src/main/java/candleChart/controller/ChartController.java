package candleChart.controller;

import candleChart.data.Buffer;
import candleChart.view.*;
import candleChart.view.Cursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChartController {
    private final ChartArea chartArea;
    private final PriceLine priceLine;
    private final TimeLine timeLine;
    private final Info info;
    private final Cursor cursor;
    private final Grid grid;
    private final CandleView candleView;
    private final CandleController candleController;
    private Buffer buffer;

    public ChartController(JPanel jPanel) {
        chartArea = new ChartArea();
        priceLine = new PriceLine();
        timeLine = new TimeLine();
        info = new Info();
        cursor = new Cursor();
        grid = new Grid();
        candleView = new CandleView();
        candleController = new CandleController(candleView);
        buffer = new Buffer();

        chartArea.add(candleView);
        chartArea.add(cursor);
        chartArea.add(grid);

        info.addInfo("");
        info.addInfo("");

        jPanel.setLayout(new BorderLayout());
        jPanel.setDoubleBuffered(true);
        jPanel.setBackground(Color.BLACK);
        jPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 5));

        jPanel.add(chartArea, BorderLayout.CENTER);
        jPanel.add(timeLine, BorderLayout.SOUTH);
        jPanel.add(priceLine, BorderLayout.EAST);
        jPanel.add(info, BorderLayout.NORTH);

        setupMouse();
        setupChartListener();
    }

    /**
     * Establece un buffer de datos en el gráfico. Para que cualquier cambio en el buffer sea representado gráficamente,
     * es necesario hacer una llamada al método update.
     *
     * @param buffer El buffer de datos a agregar al grafico.
     */
    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
        candleController.setBuffer(buffer);
    }


    /**
     * Obtiene el buffer de datos que contiene el gráfico, con todos los valores almacenado antes de haber llamado al
     * método update. Esto quiere decir que el buffer puede contener elementos que aún no hayan sido actualizados en
     * el gráfico.
     *
     * @return El buffer de datos del grafico.
     */
    public Buffer getBuffer() {
        return buffer;
    }


    /**
     * Establece la visibilidad de la cuadrícula del gráfico. Si visibility se establece a true la cuadrícula será
     * mostrada, o en caso de ser false, esta se ocultará.
     *
     * @param visibility Visibilidad de la cuadrícula.
     */
    public void setGridVisible(boolean visibility) {
        grid.setVisible(visibility);
    }


    /**
     * Obtiene la visibilidad de la cuadrícula del gráfico. En caso de que la cuadrícula este visible devuelve true, en
     * caso contrario devolverá false.
     *
     * @return Estado de la visibilidad de la cuadrícula.
     */
    public boolean isGridVisible() {
        return grid.isVisible();
    }


    /**
     * Establece la visibilidad de las líneas del cursor en el gráfico. En caso de establecer visibility como true,
     * las líneas del cursor serán mostradas en el gráfico. En caso de ser false esta no se mostrarán.
     *
     * @param visibility Visibilidad de las líneas del cursor.
     */
    public void setCursorVisible(boolean visibility) {
        cursor.setVisible(visibility);
    }


    /**
     * Obtiene la visibilidad de las líneas del cursor en el gráfico. En caso de que las líneas se muestre devuelve
     * true, en caso contrario devuelve false.
     *
     * @return Visibilidad de las líneas del cursor.
     */
    public boolean isCursorVisible() {
        return cursor.isVisible();
    }


    public void setSymbol(String symbol) {
        info.updateInfo(0, symbol);
    }

    public String getSymbol() {
        return info.getInfo(0);
    }

    private void setupMouse() {
        chartArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                timeLine.setCursorVisible(false);
                priceLine.setCursorVisible(false);
                cursor.setCursorVisible(false);
                info.setVisibilityInfo(1, false);
            }
        });

        chartArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                timeLine.setCursorLocation(e.getX());
                timeLine.setCursorVisible(true);
                priceLine.setCursorLocation(e.getY());
                priceLine.setCursorVisible(true);
                cursor.setCursorLocation(e.getX()-1, e.getY()-1);
                cursor.setCursorVisible(true);

                String strInfo = timeLine.getCandleFromCursor() != null? timeLine.getCandleFromCursor().toString(): "";
                info.updateInfo(1, strInfo);
                info.setVisibilityInfo(1, true);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (e.getX() > 0 && e.getY() > 0 && e.getX() < chartArea.getWidth() && e.getY() < chartArea.getHeight()) {
                    timeLine.setCursorLocation(e.getX());
                    timeLine.setCursorVisible(true);
                    priceLine.setCursorLocation(e.getY());
                    priceLine.setCursorVisible(true);
                    cursor.setCursorLocation(e.getX()-1, e.getY()-1);
                    cursor.setCursorVisible(true);

                    String strInfo = timeLine.getCandleFromCursor() != null? timeLine.getCandleFromCursor().toString(): "";
                    info.updateInfo(1, strInfo);
                    info.setVisibilityInfo(1, true);
                }
            }
        });
    }

    private void setupChartListener() {
        candleView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                priceLine.setPriceRange(candleController.getMaxPrice(), candleController.getMinPrice());
                timeLine.setCandleList(candleView.getCandleList());
                timeLine.setRelativePosition(candleController.getRelativePosition());
            }
        });
    }
}
