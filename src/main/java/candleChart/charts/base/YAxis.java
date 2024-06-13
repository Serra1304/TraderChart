package candleChart.charts.base;

import candleChart.charts.util.CursorListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class YAxis extends JPanel implements CursorListener, ChartListener {

    private static final int DIVIDER_WIDTH = 4;
    private static final int PRICE_TAG_WIDTH = 60;
    private static final int PRICE_TAG_HEIGHT = 15;
    private static final int SIZE_GRID = 32;

    private final JLabel currentPrice;

    private int cursorLocationY;
    private double rangeUp, rangeDown;


    /**
     * Constructor de la clase PriceLine.
     * Crea una nueva instancia de PriceLine con valores predeterminados para las propiedades.
     */
    public YAxis() {
        cursorLocationY = 0;
        rangeUp = 0;
        rangeDown = 0;

        currentPrice = new JLabel();
        currentPrice.setBackground(Color.GRAY);
        currentPrice.setForeground(Color.BLACK);
        currentPrice.setHorizontalAlignment(SwingConstants.CENTER);
        currentPrice.setOpaque(true);
        currentPrice.setVisible(false);
        currentPrice.setSize(PRICE_TAG_WIDTH, PRICE_TAG_HEIGHT);

        setPreferredSize(new Dimension(70, 0));
        setBackground(Color.BLACK);
        setLayout(null);
        add(currentPrice);
        setFocusable(false);
    }


    /**
     * Sobrescribe el método paintComponent para dibujar la línea de precios de un gráfico.
     * Este método se llama automáticamente cuando el componente necesita ser repintado.
     *
     * @param g El contexto gráfico en el que dibujar la línea de tiempo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);

        // Pintado de divisiones.
        for (int i = getHeight(); i >= 0; i -= SIZE_GRID) {
            g.drawLine(0, i, DIVIDER_WIDTH, i);
        }

        // Pintado línea actual de precio.
        if(currentPrice.isVisible()) {
            g.drawLine(0, cursorLocationY, 5, cursorLocationY);
        }
    }



    /**
     * Método que actualiza los valores de las divisiones de la línea de precios.
     */
    private void updateChartPrices() {
        double pricePixel = (rangeUp - rangeDown) / getHeight();
        int requiredLabels = (getHeight()) / SIZE_GRID;

        // Agrega o elimina etiquetas de las divisiones según sea necesario.
        while (getComponentCount() > requiredLabels + 1) {
            remove(getComponentCount() - 1);
        }

        while (getComponentCount() <= requiredLabels) {
            JLabel label = new JLabel();
            label.setForeground(Color.GRAY);
            label.setSize(PRICE_TAG_WIDTH, PRICE_TAG_HEIGHT);
            label.setOpaque(false);
            add(label);
        }

        // Actualiza el valor de las etiquetas de las divisiones de la línea de precios.
        for(int i = 1; i < getComponentCount(); i++) {
            String formattedNumber = String.format("%.5f", rangeUp - (getHeight() - (i * SIZE_GRID)) * pricePixel);

            JLabel label = (JLabel) getComponent(i);
            label.setLocation(8, getHeight() - (i * SIZE_GRID) - PRICE_TAG_HEIGHT / 2);
            label.setText(formattedNumber);
        }
        repaint();
    }


    /**
     * Método que actualiza el valor de la etiqueta que muestra el precio donde apunta el cursor.
     */
    private void updateCurrentPrice() {
        double pricePixel = (rangeUp - rangeDown) / getHeight();
        String formattedNumber = String.format("%.5f", rangeUp - cursorLocationY * pricePixel);
        currentPrice.setText(formattedNumber);
    }

    @Override
    public void cursorMoved(int locationX, int locationY) {
        cursorLocationY = locationY +1;
        int positionY;

        // Posición de la etiqueta que muestra información actual del eje Y al aproximarse al borde superior.
        if(cursorLocationY < PRICE_TAG_HEIGHT / 2) {
            positionY = 0;

        // Posición de la etiqueta que muestra información actual del eje Y mientras no se encuentre cerca de los bordes.
        } else if (cursorLocationY >= getHeight() - PRICE_TAG_HEIGHT / 2) {
            positionY = getHeight() - PRICE_TAG_HEIGHT;

        // Posición de la etiqueta que muestra información actual del eje Y al aproximarse al borde inferior.
        } else {
            positionY = cursorLocationY - PRICE_TAG_HEIGHT / 2;
        }

        // Actualización de la etiqueta que muestra información actual del cursor en el eje Y.
        currentPrice.setLocation(5, positionY);
        updateCurrentPrice();
        repaint();
    }

    @Override
    public void cursorVisibility(boolean aFlag) {
        currentPrice.setVisible(aFlag);
        repaint();
    }

    @Override
    public void OnRelativePosition(int relativePosition) {
    }

    @Override
    public void OnChartInfo(String info) {
    }

    @Override
    public void OnXAxisInfo(List<String> stringInfoList) {
    }

    @Override
    public void OnPriceRange(double rangeUp, double rangeDown) {
        this.rangeUp = rangeUp;
        this.rangeDown = rangeDown;
        updateChartPrices();
    }
}
