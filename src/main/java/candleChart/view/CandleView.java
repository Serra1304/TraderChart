package candleChart.view;

import candleChart.controller.CandleSize;
import candleChart.model.Candle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase CandleView representa una vista que muestra una serie de velas en un gráfico.
 * Cada vela se representa como una línea y un rectángulo, donde la línea conecta los precios más altos y más bajo,
 * y el rectángulo representa la diferencia entre los precios de apertura y cierre. El color de la vela depende de si
 * el precio de apertura es mayor o menor que el precio de cierre, siendo verde si el precio de cierre es mayor que el
 * precio de apertura y rojo si el precio de cierre es menor que el precio de apertura.
 */
public class CandleView extends JPanel {
    private List<Candle> candleList;

    private CandleSize candleSize;
    private double rangeUp, rangeDown;


    /**
     * Constructor de la clase CandleView.
     * Crea una nueva instancia de CandleView con valores predeterminados para las propiedades.
     */
    public CandleView() {
        candleList = new ArrayList<>();
        candleSize = CandleSize.SMALL;
        rangeUp = 0;
        rangeDown = 0;

        setOpaque(false);
    }


    /**
     * Sobrescribe el método paintComponent para dibujar las velas en un gráfico.
     * Este método se llama automáticamente cuando el componente necesita ser repintado.
     *
     * @param g El contexto gráfico en el que dibujar las velas.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(!candleList.isEmpty()) {
            int candleWidth = candleSize.getCandleWidth();
            // Itera sobre la lista en orden inverso para dibujar las velas más recientes al final.
            for (int i = candleList.size() -1; i >= 0; i--) {
                // Se dibuja las lineas de las velas.
                g.setColor(candleList.get(i).openPrice() > candleList.get(i).closePrice() ? new Color(127, 0, 0) : new Color(0, 127, 0));
                g.drawLine(positionOfCandle(i), positionOfPrice(candleList.get(i).highPrice()),
                           positionOfCandle(i), positionOfPrice(candleList.get(i).lowPrice()));

                // Se dibuja el rectángulo de las velas.
                double maxOpenOrClose = Math.max(candleList.get(i).openPrice(), candleList.get(i).closePrice());
                double minOpenOrClose = Math.min(candleList.get(i).openPrice(), candleList.get(i).closePrice());
                int rectY = positionOfPrice(maxOpenOrClose);
                int rectHeight = positionOfPrice(minOpenOrClose) - rectY;
                g.drawRect(positionOfCandle(i) - (candleWidth / 2) , rectY, candleWidth - 1, rectHeight);
                g.setColor(candleList.get(i).openPrice() > candleList.get(i).closePrice() ? new Color(255, 0, 0) : new Color(0, 255, 0));
                g.fillRect(positionOfCandle(i) - (candleWidth / 2) + 1, rectY, candleWidth - 2, rectHeight);
            }
        }
    }


    /**
     * Establece la lista de velas que será representada en la vista.
     *
     * @param candleList Lista de velas a representar.
     */
    public void setCandleList(List<Candle> candleList) {
        if(candleList != null) {
            this.candleList = candleList;
            repaint();
        }
    }


    /**
     * Obtiene la lista de velas representadas en la vista del gráfico.
     *
     * @return La lista de velas.
     */
    public List<Candle> getCandleList() {
        return candleList;
    }
    

    public void setCandleSize(CandleSize candleSize) {
        this.candleSize = candleSize;
    }

    public CandleSize getCandleSize() {
        return candleSize;
    }


    /**
     * Establece el rango de precios que será representado en el gráfico.
     *
     * @param rangeUp Precio del rango superior.
     * @param rangeDown Precio del rango inferior.
     */
    public void setPriceRange(double rangeUp, double rangeDown) {
        this.rangeUp = rangeUp;
        this.rangeDown = rangeDown;
        repaint();
    }


    /**
     * Obtiene el rango de precio superior representado en el gráfico.
     *
     * @return El precio del rango superior.
     */
    public double getRangeUp() {
        return rangeUp;
    }


    /**
     * Obtiene el rango de precio inferior representado en el gráfico.
     *
     * @return El precio del rango inferior.
     */
    public double getRangeDown() {
        return rangeDown;
    }


    /**
     * Calcula la posición en píxeles en el eje X del índice de vela proporcionado.
     *
     * @param index Índice de vela para calcular su posición.
     * @return Posición en pixel en el eje X.
     */
    private int positionOfCandle(int index) {
        int relativePosition = candleSize.getRelativePosition();
        return index * relativePosition -1;
    }


    /**
     * Calcula la posición en píxeles en el eje Y del precio proporcionado.
     *
     * @param price Precio de vela para calcular su posición.
     * @return Posición en pixel en el eje Y.
     */
    private int positionOfPrice(double price) {
        return (int) ((rangeUp - price) / ((rangeUp - rangeDown) / getHeight()));
    }
}
