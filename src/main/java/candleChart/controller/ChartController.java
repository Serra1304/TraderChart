package candleChart.controller;

import candleChart.data.Buffer;
import candleChart.view.*;
import candleChart.view.Cursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase encargada de controlar y gestionar los componentes y eventos del gráfico de velas.
 */
public class ChartController {
    private final ChartArea chartArea;
    private final PriceLine priceLine;
    private final TimeLine timeLine;
    private final Info info;
    private final Cursor cursor;
    private final Grid grid;
    private final CandleView candleView;
    private final CandleController candleController;

    /**
     * Constructor de la clase ChartController.
     *
     * @param jPanel El panel principal donde se añadirá el gráfico y sus componentes.
     */
    public ChartController(JPanel jPanel) {
        chartArea = new ChartArea();
        priceLine = new PriceLine();
        timeLine = new TimeLine();
        info = new Info();
        cursor = new Cursor();
        grid = new Grid();
        candleView = new CandleView();
        candleController = new CandleController(candleView);

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
     * Establece un buffer de datos en el gráfico.
     *
     * @param buffer El buffer de datos a agregar al grafico.
     * @throws NullPointerException En caso de que el buffer sea nulo.
     */
    public void setBuffer(Buffer buffer) {
        if(buffer == null) {
            throw new NullPointerException("No se permiten valores nulos para el valor 'buffer'.");
        }
        candleController.setBuffer(buffer);
        updateAxles();
    }


    /**
     * Obtiene el buffer de datos que contiene el gráfico.
     *
     * @return El buffer de datos del grafico.
     */
    public Buffer getBuffer() {
        return candleController.getBuffer();
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


    /**
     * Establece la propiedad del tamaño de vela en el gráfico.
     *
     * @param candleSize El tamaño de vela.
     * @throws NullPointerException En caso de que la propiedad 'candleSize' sea nula.
     */
    public void setCandleSize(CandleSize candleSize) {
        if(candleSize == null) {
            throw new NullPointerException("La propiedad 'candleSize' no puede ser nula");
        }
        candleController.setCandleSize(candleSize);
        updateAxles();
    }


    /**
     * Obtiene el tamaño de vela establecido en el gráfico.
     *
     * @return El tamaño de vela.
     */
    public CandleSize getCandleSize() {
        return candleController.getCandleSize();
    }


    /**
     * Avanza un paso en la representación del gráfico siempre que no haya llegado al principio de este.
     */
    public void advance() {
        candleController.advance();
    }


    /**
     * Avanza en el gráfico el número de pasos proporcionado por parámetros.
     *
     * @param steps Número de pasos a avanzar en el buffer.
     * @throws IllegalArgumentException Si el parámetro proporcionado es negativo.
     */
    public void advance(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException("No se permiten valores negativos para la propiedad 'steps'.");
        }
        candleController.advance(steps);
    }


    /**
     * Retrocede un paso en la representación del gráfico siempre que no haya llegado al final de este.
     */
    public void retrieve() {
        candleController.retrieve();
    }


    /**
     * Retrocede en el gráfico el número de velas proporcionado por parámetro, o hasta que haya datos que mostrar.
     *
     * @param steps El número de pasos a retroceder en el buffer.
     * @throws IllegalArgumentException Si el parámetro proporcionado es negativo.
     */
    public void retrieve(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException("No se permiten valores negativos para la propiedad 'steps'.");
        }
        candleController.retrieve(steps);
    }


    /**
     * Establece el simbol del gráfico en la información del gráfico.
     *
     * @param symbol El símbolo del gráfico representado.
     */
    public void setSymbol(String symbol) {
        info.updateInfo(0, symbol);
    }


    /**
     * Obtiene el símbolo del gráfico representado.
     *
     * @return El símbolo del gráfico.
     */
    public String getSymbol() {
        return info.getInfo(0);
    }


    /**
     * Método auxiliar que actualiza las líneas de tiempo y precio.
     */
    private void updateAxles() {
        priceLine.setPriceRange(candleController.getMaxPrice(), candleController.getMinPrice());
        timeLine.setCandleList(candleView.getCandleList());
        timeLine.setCandleSize(candleController.getCandleSize());
    }


    /**
     * Establece la configuración de los oyentes del ratón.
     */
    private void setupMouse() {
        chartArea.addMouseListener(new MouseAdapter() {

            /**
             * Cuando el ratón sale del gráfico, los elementos del cursor, precio y fecha a la que apunta el cursor e
             * información de la vela, son ocultados.
             *
             * @param e Evento del ratón.
             */
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

            /**
             * Cuando el ratón es movido dentro del gráfico, los elementos del cursor, linea de precio y tiempo e
             * información de vela son actualizados.
             *
             * @param e Evento del ratón.
             */
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


            /**
             * Si el ratón abandona el gráfico mientras se mantiene pulsado el botón, los elementos del ratón son
             * ocultados, y si vuelve a entrar mientras sigue pulsado el botón se vuelven a mostrar estos elementos.
             *
             * @param e Evento del ratón.
             */
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


    /**
     * Establece la configuración del oyente de redimensionado del gráfico.
     */
    private void setupChartListener() {
        candleView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateAxles();
            }
        });
    }
}
