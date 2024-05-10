package candleChart.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Clase que representa un componente que dibuja una cuadrícula en una interfaz de usuario gráfica.
 * Esta clase permite la renderización de una cuadrícula con tamaño de cuadrícula y visibilidad personalizables.
 */
public class Grid extends JPanel {

    // Propiedades de la cuadrícula
    private int gridX;
    private int gridY;
    private boolean gridVisible;


    /**
     * Construye un nuevo componente con los datos predeterminados de la cuadricula.
     */
    public Grid() {
        // Valores predeterminados de la cuadrícula.
        gridX = 32;
        gridY = 32;
        gridVisible = true;

        // Se establece el fondo de la cuadrícula como transparente.
        setOpaque(false);
    }


    /**
     * Pinta el componente de cuadrícula en el contexto gráfico.
     * Sobrescribe el método paintComponent de JPanel.
     *
     * @param g El contexto gráfico en el que pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Pinta la cuadrícula si gridVisible está establecido como true.
        if (gridVisible) {
            // Se establece la forma del trazo de las líneas.
            float[] dashPattern = {5.0f, 4.0f};
            BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0.0f, dashPattern, 0.0f);
            g2d.setStroke(dashedStroke);
            g2d.setColor(Color.DARK_GRAY);

            // Dibuja las líneas verticales.
            for (int x = -1; x < getWidth(); x += gridX) {
                g2d.draw(new Line2D.Double(x, 0, x, getHeight()));
            }

            // Dibuja las líneas horizontales.
            for (int i = getHeight()+1; i > 0; i -= gridY) {
                g2d.draw(new Line2D.Double(0, i, getWidth(), i));
            }
        }
    }


    /**
     * Método que establece el ancho y alto de la cuadrícula.
     *
     * @param gridX Anchura de la división vertical.
     * @param gridY Altura de la división horizontal.
     */
    public void setGridSize(int gridX, int gridY) {
        if(gridX > 1 && gridY > 1) {
            this.gridX = gridX;
            this.gridY = gridY;
            repaint();
        }
    }


    /**
     * Obtiene la anchura de la división vertical de la cuadricula.
     *
     * @return Anchura de la división vertical.
     */
    public double getGridX() {
        return gridX;
    }


    /**
     * Obtiene la altura de la división horizontal de la cuadricula.
     *
     * @return Altura de la división horizontal.
     */
    public double getGridY() {
        return gridY;
    }


    /**
     * Establece la visibilidad de la cuadrícula. En caso de establecer gridVisible como true, la cuadrícula será
     * pintada en el componente. En caso de ser false, esta no será pintada.
     *
     * @param gridVisible Visibilidad de la cuadrícula.
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
        repaint();
    }


    /**
     * Obtiene la visibilidad de la cuadrícula. En caso de que la cuadricula este visible, devolverá true, en caso
     * contrario devolverá false.
     *
     * @return Visibilidad de la cuadrícula.
     */
    public boolean isGridVisible() {
        return gridVisible;
    }
}
