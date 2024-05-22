package candleChart.view;

import candleChart.controller.CandleSize;
import candleChart.model.Candle;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que representa un componente que dibuja la línea de tiempo de un gráfico.
 * Esta clase permite la renderización de la línea horizontal de tiempo de un gráfico, junto con sus divisiones y la
 * fecha actual del puntero del ratón donde se encuentra en ese momento.
 */
public class TimeLine extends JPanel {
    private static final int DIVIDER_HEIGHT = 4;
    private static final int DIVIDER_SIZE = 64;
    private static final int CURRENT_TIME_WIDTH = 100;
    private static final int CURRENT_TIME_HEIGHT = 15;
    private static final int CURRENT_TIME_LOCATION_X = 0;
    private static final int CURRENT_TIME_LOCATION_Y = 5;

    private int cursorLocationX;
    private final JLabel currentTime;
    private List<Candle> candleList;
    private Candle candleFromCursor;
    private CandleSize candleSize;


    /**
     * Constructor de la clase TimeLine.
     * Crea una nueva instancia de TimeLine con valores predeterminados para las propiedades.
     */
    public TimeLine() {
        candleSize = CandleSize.SMALL;
        cursorLocationX = 0;
        candleList = new ArrayList<>();

        currentTime = new JLabel();
        currentTime.setVisible(false);
        currentTime.setOpaque(true);
        currentTime.setSize(CURRENT_TIME_WIDTH, CURRENT_TIME_HEIGHT);
        currentTime.setHorizontalAlignment(SwingConstants.CENTER);
        currentTime.setLocation(CURRENT_TIME_LOCATION_X, CURRENT_TIME_LOCATION_Y);
        currentTime.setBackground(Color.GRAY);

        setPreferredSize(new Dimension(0, 40));
        setLayout(null);
        setBackground(Color.BLACK);
        add(currentTime);
    }


    /**
     * Sobrescribe el método paintComponent para dibujar la línea de tiempo de un gráfico.
     * Este método se llama automáticamente cuando el componente necesita ser repintado.
     *
     * @param g El contexto gráfico en el que dibujar la línea de tiempo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Se pinta divisiones
        g.setColor(Color.GRAY);
        for (int x = 0; x < getWidth() - 70; x += DIVIDER_SIZE) {
            g.drawLine(x, 0, x, DIVIDER_HEIGHT);
        }

        // Se pinta posición del cursor.
        if (currentTime.isVisible()) {
            g.drawLine(cursorLocationX, 0, cursorLocationX, DIVIDER_HEIGHT);
        }
    }


    /**
     * Establece la posición del ratón en el eje X. Este método actualiza tanto la ubicación del cursor, como la
     * posición de la etiqueta de la fecha a la que apunta esta posición.
     *
     * @param cursorLocationX Posición del ratón en el eje X.
     */
    public void setCursorLocation(int cursorLocationX) {
        int positionX = cursorLocationX < CURRENT_TIME_WIDTH / 2 ? 0 : cursorLocationX - CURRENT_TIME_WIDTH / 2;
        currentTime.setLocation(positionX, CURRENT_TIME_LOCATION_Y);

        this.cursorLocationX = cursorLocationX;
        repaint();
        updateCurrentDate();
    }


    /**
     * Obtiene la posición del cursor en el eje X de la línea de tiempo.
     *
     * @return Posición del cursor en el eje X.
     */
    public int getCursorLocationX() {
        return cursorLocationX;
    }


    /**
     * Establece la visibilidad del cursor en la línea de tiempo. En caso de establecer esta propiedad como true, tanto
     * la línea del cursor como la etiqueta del precio a la que apunta el ratón serán visibles. En caso de establecer
     * esta propiedad como false, estos elementos estarán ocultos.
     *
     * @param visibility Visibilidad del cursor en la línea de tiempo.
     */
    public void setCursorVisible(boolean visibility) {
        currentTime.setVisible(visibility);
        repaint();
    }


    /**
     * Obtiene la visibilidad del cursor en la línea de tiempo. En caso de que el cursor este visible devolverá true,
     * si no devolverá false.
     *
     * @return la visibilidad del cursor en la línea de tiempo.
     */
    public boolean isCursorVisible() {
        return currentTime.isVisible();
    }


    /**
     * Establece la lista de velas representadas en el gráfico de las cuales se extraerá las fechas de la línea de
     * tiempo.
     *
     * @param candleList Lista de velas del gráfico.
     *
     * @throws NullPointerException Si la lista de velas proporcionada es nula.
     */
    public void setCandleList(List<Candle> candleList) {
        if(candleList == null) {
            throw new NullPointerException("No se permiten valores nulos para 'candleList'");
        }

        this.candleList = candleList;
        repaint();
        updateChartDate();
    }


    /**
     * Obtiene la lista de velas utilizada para la representación de las fechas en la línea de tiempo.
     *
     * @return Lista de velas utilizada en la línea de tiempo.
     */
    public List<Candle> getCandleList() {
        return candleList;
    }


    /**
     * Establece la propiedad CandleSize. Esta propieda proporciona informacion relativa a la vela, como tamaño y
     * posición relativa de la misma. Este objeto es utilizado para la obtención de la vela en la cual se
     * encuentra ubicado el cursor del ratón.
     *
     * @param candleSize Objeto de tipo CandleSize que proporciona tamaño y posición relativa de la vela.
     *
     * @throws NullPointerException Si el valor proporcionado es nulo..
     */
    public void setCandleSize(CandleSize candleSize) {
        if(candleSize == null) {
            throw new NullPointerException("No se permiten valores nulos");
        }

        this.candleSize = candleSize;
        repaint();
    }


    /**
     * Obtiene un objeto de tipo CandleSize. Este objeto proporciona información de la vela, como tamaño y posición
     * relativa de la misma.
     *
     * @return Objeto de tipo CandleSize.
     */
    public CandleSize getCandleSize() {
        return candleSize;
    }


    public Candle getCandleFromCursor() {
        return candleFromCursor;
    }


    /**
     * Método que actualiza los valores de las fechas de la línea de tiempo.
     */
    private void updateChartDate() {
        int numLabels = (getWidth() - 71) / DIVIDER_SIZE + 1;

        // Agrega o elimina etiquetas de las divisiones según sea necesario.
        while (getComponentCount() > numLabels +1) {
            remove(getComponentCount() -1);
        }

        while (getComponentCount() <= numLabels) {
            JLabel label = new JLabel();
            label.setForeground(Color.GRAY);
            label.setSize(62, 30);
            label.setOpaque(false);
            add(label);
        }

        // Actualiza los valores de las fechas de las etiquetas.
        for(int i = 1; i < getComponentCount(); i++) {
            int relativePosition = candleSize.getRelativePosition();
            int indexCandleTime = i * (DIVIDER_SIZE / relativePosition) - (DIVIDER_SIZE / relativePosition);
            if(candleList.size() > indexCandleTime) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm");
                String formattedDateTime = candleList.get(indexCandleTime).dateTime().format(formatter);

                JLabel label = (JLabel) getComponent(i);
                label.setLocation(i * DIVIDER_SIZE - DIVIDER_SIZE, 5);
                label.setText("<html>" + formattedDateTime + "</html>");
            }
        }
    }


    /**
     * Método que actualiza el valor de la etiqueta que representa el valor en el cual se encuentra el cursor del ratón.
     */
    private void updateCurrentDate() {
        int relativePosition = candleSize.getRelativePosition();
        int indexCandleTime = (cursorLocationX + relativePosition / 2) / relativePosition;  // Vela a la que apunta el cursor.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm");  // Formato de fecha a mostrar.
        JLabel label = (JLabel) getComponent(0);    // Etiqueta que muestra la fecha de la vela a la que apunta el cursor.

        // Se muestra y actualiza el valor de la vela a la que apunta el cursor en caso de existir.
        if(candleList.size() > indexCandleTime) {
            label.setText(candleList.get(indexCandleTime).dateTime().format(formatter));
            candleFromCursor = candleList.get(indexCandleTime);

            if (currentTime.isVisible()) {
                label.setVisible(true);
            }
        }
        else {
            label.setText("");
            label.setVisible(false);
            candleFromCursor = null;
        }
    }
}
