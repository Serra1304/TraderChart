package candleChart.charts.base;

import candleChart.charts.util.CursorListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class XAxis extends JPanel implements CursorListener, ChartListener {
    private static final int DIVIDER_HEIGHT = 4;            // Anchura de las divisiones
    private static final int DIVIDER_SIZE = 64;             // Espacio entre divisiones
    private static final int CURRENT_LABEL_WIDTH = 100;
    private static final int CURRENT_LABEL_HEIGHT = 15;
    private static final int CURRENT_LABEL_LOCATION_X = 0;
    private static final int CURRENT_LABEL_LOCATION_Y = 5;

    private int cursorLocationX;
    private int relativePosition;
    private final JLabel currentTime;
    private List<String> infoList;


    /**
     * Constructor de la clase TimeLine.
     * Crea una nueva instancia de TimeLine con valores predeterminados para las propiedades.
     */
    public XAxis() {
        cursorLocationX = 0;
        relativePosition = 1;
        infoList = new ArrayList<>();

        currentTime = new JLabel();
        currentTime.setVisible(false);
        currentTime.setOpaque(true);
        currentTime.setSize(CURRENT_LABEL_WIDTH, CURRENT_LABEL_HEIGHT);
        currentTime.setHorizontalAlignment(SwingConstants.CENTER);
        currentTime.setLocation(CURRENT_LABEL_LOCATION_X, CURRENT_LABEL_LOCATION_Y);
        currentTime.setBackground(Color.GRAY);

        setPreferredSize(new Dimension(0, 40));
        setLayout(null);
        setOpaque(false);
        add(currentTime);
    }


    /**
     * Sobrescribe el método paintComponent para dibujar el eje X de un gráfico.
     * Este método se llama automáticamente cuando el componente necesita ser repintado.
     *
     * @param g El contexto gráfico en el que dibujar la línea de tiempo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Se pinta divisiones del eje X.
        g.setColor(Color.GRAY);
        for (int x = 0; x < getWidth(); x += DIVIDER_SIZE) {
            g.drawLine(x, 0, x, DIVIDER_HEIGHT);
        }

        // Se pinta posición del cursor si es necesario.
        if (currentTime.isVisible()) {
            g.drawLine(cursorLocationX, 0, cursorLocationX, DIVIDER_HEIGHT);
        }
    }


    /**
     * Este método sobrescribe el método `cursorMoved` de la interfaz ´CursorListener´ que monitorea el movimiento del
     * ratón dentro del gráfico.
     *
     * @param locationX La coordenada X de la ubicación actual del ratón dentro del componente.
     * @param locationY La coordenada Y de la ubicación actual del ratón dentro del componente.
     *
     * @see CursorListener
     */
    @Override
    public void cursorMoved(int locationX, int locationY) {
        cursorLocationX = locationX + 1;
        int positionX;

        // Posición de la etiqueta que muestra información actual del cursor al aproximarse al borde izquierdo.
        if(cursorLocationX < CURRENT_LABEL_WIDTH / 2) {
            positionX = 0;

        // Posición de la etiqueta que muestra información actual del cursor mientras no se encuentre cerca de los bordes.
        } else if (cursorLocationX < getWidth() - CURRENT_LABEL_WIDTH / 2) {
            positionX = cursorLocationX - CURRENT_LABEL_WIDTH / 2;

        // Posición de la etiqueta que muestra información actual del cursor al aproximarse al borde derecho.
        } else {
            positionX = getWidth() - CURRENT_LABEL_WIDTH;
        }

        // Actualización de la etiqueta que muestra información actual del cursor.
        currentTime.setLocation(positionX, CURRENT_LABEL_LOCATION_Y);
        updateCurrentDate();
        repaint();
    }


    /**
     * Este método sobrescribe el método `cursorVisibility` de la interfaz ´CursorListener´ que monitorea el cambio de
     * visibilidad del ratón en el gráfico.
     *
     * @param aFlag Visibilidad actual del cursor en el gráfico.
     *
     * @see CursorListener
     */
    @Override
    public void cursorVisibility(boolean aFlag) {
        if(!aFlag) {
            currentTime.setVisible(false);
        }
        repaint();
    }

    @Override
    public void OnRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
        updateChartDate();
        repaint();
    }

    @Override
    public void OnChartInfo(String info) {
    }

    @Override
    public void OnXAxisInfo(List<String> stringInfoList) {
        infoList = stringInfoList;
        updateChartDate();
        repaint();
    }

    @Override
    public void OnPriceRange(double rangeUp, double rangeDown) {
    }

    /**
     * Método que actualiza los valores de las fechas de la línea de tiempo.
     */
    private void updateChartDate() {
        int numLabels = (getWidth() - 2) / DIVIDER_SIZE + 1;

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
            int indexCandleTime = i * (DIVIDER_SIZE / relativePosition) - (DIVIDER_SIZE / relativePosition);
            if(infoList.size() > indexCandleTime) {

                JLabel label = (JLabel) getComponent(i);
                label.setLocation(i * DIVIDER_SIZE - DIVIDER_SIZE, 5);
                label.setText("<html>" + infoList.get(indexCandleTime) + "</html>");
            }
        }
    }


    /**
     * Método que actualiza el valor de la etiqueta que representa el valor en el cual se encuentra el cursor del ratón.
     */
    private void updateCurrentDate() {
        int indexCandleTime = (cursorLocationX + relativePosition / 2) / relativePosition;  // Vela a la que apunta el cursor.
        JLabel label = (JLabel) getComponent(0);    // Etiqueta que muestra la fecha de la vela a la que apunta el cursor.

        // Se muestra y actualiza el valor de la vela a la que apunta el cursor en caso de existir.
        if(infoList.size() > indexCandleTime) {
            label.setText(infoList.get(indexCandleTime));
                label.setVisible(true);
        }
        else {
            label.setText("");
            label.setVisible(false);
        }
    }
}
