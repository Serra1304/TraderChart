package candleChart.view;

import javax.swing.*;
import java.awt.*;


/**
 * Clase que representa un componente que dibuja la línea de precios de un gráfico.
 * Esta clase permite la renderización de la línea vertical de precios de un gráfico, junto con sus divisiones y el
 * precio actual del puntero del ratón donde se encuentra en ese momento.
 */
public class PriceLine extends JPanel {
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
    public PriceLine() {
        cursorLocationY = 0;
        rangeUp = 0;
        rangeDown = 0;

        currentPrice = new JLabel();
        currentPrice.setBackground(Color.GRAY);
        currentPrice.setForeground(Color.BLACK);
        currentPrice.setHorizontalAlignment(SwingConstants.CENTER);
        currentPrice.setOpaque(true);
        currentPrice.setVisible(false);
        currentPrice.setSize(60, 15);

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

        // Actualización de los precios de las divisiones del gráfico.
        updateChartPrices();
    }


    /**
     * Establece la posición del cursor en el eje Y.
     *
     * @param locationY Posición del cursor en el eje Y.
     */
    public void setCursorLocation(int locationY) {
        int positionY = locationY < PRICE_TAG_HEIGHT/2? 0: locationY - PRICE_TAG_HEIGHT/2;
        currentPrice.setLocation(5, positionY);

        this.cursorLocationY = locationY;
        repaint();
        updateCurrentPrice();
    }


    /**
     * Obtiene la posición del cursor del eje Y.
     *
     * @return Posición del cursor del eje Y.
     */
    public int getCursorLocation() {
        return cursorLocationY;
    }


    /**
     * Establece la visibilidad del cursor y del precio al que apunta el cursor en la línea de precios.
     *
     * @param visibility Visibilidad del cursor.
     */
    public void setCursorVisible(boolean visibility) {
        currentPrice.setVisible(visibility);
        repaint();
        updateCurrentPrice();
    }


    /**
     * Obtiene la visibilidad del cursor y del precio actual al que apunta el cursor en la línea de precios.
     *
     * @return Visibilidad del cursor y del precio al que apunta el cursor.
     */
    public boolean isCursorVisible() {
        return currentPrice.isVisible();
    }


    /**
     * Establece el rando de precios que será representado en la línea de precio.
     *
     * @param rangeUp Rango superior del precio.
     * @param rangeDown Rango inferior del precio.
     */
    public void setPriceRange(double rangeUp, double rangeDown) {
        this.rangeUp = rangeUp;
        this.rangeDown = rangeDown;
        updateChartPrices();
    }


    /**
     * Obtiene el rango superior del precio representado en la línea de precios.
     *
     * @return Rango superior de precio.
     */
    public double getRangeUp() {
        return rangeUp;
    }


    /**
     * Obtiene el rango inferior del precio representado en la línea de precios.
     *
     * @return Rango inferior de precio.
     */
    public double getRangeDown() {
        return rangeDown;
    }


    /**
     * Método que actualiza los valores de las divisiones de la línea de precios.
     */
    private void updateChartPrices() {
        double pricePixel = (rangeUp - rangeDown) / getHeight();

        // Añade nuevas etiquetas al componente en caso de ser necesario.
        if(getComponentCount() <= (getHeight()) / SIZE_GRID) {
            for(int i = getComponentCount(); i <= getHeight() / SIZE_GRID; i++) {
                JLabel label = new JLabel();
                label.setForeground(Color.GRAY);
                label.setSize(PRICE_TAG_WIDTH, PRICE_TAG_HEIGHT);
                label.setOpaque(false);
                add(label);
            }
        }

        // Elimina etiquetas del componente en caso de ser necesario.
        if(getComponentCount() > getHeight() / SIZE_GRID) {
            for(int i = getComponentCount(); i > getHeight() / SIZE_GRID +1; i--) {
                remove(i -1);
            }
        }

        // Actualiza el valor de las etiquetas de las divisiones de la línea de precios.
        for(int i = 1; i < getComponentCount(); i++) {
            String formattedNumber = String.format("%.5f", rangeUp - (getHeight() - (i * SIZE_GRID)) * pricePixel);

            JLabel label = (JLabel) getComponent(i);
            label.setLocation(8, getHeight() - (i * SIZE_GRID) - PRICE_TAG_HEIGHT / 2);
            label.setText(formattedNumber);
        }
    }


    /**
     * Método que actualiza el valor de la etiqueta que muestra el precio donde apunta el cursor.
     */
    private void updateCurrentPrice() {
        double pricePixel = (rangeUp - rangeDown) / getHeight();
        String formattedNumber = String.format("%.5f", rangeUp - cursorLocationY * pricePixel);
        currentPrice.setText(formattedNumber);
    }
}
