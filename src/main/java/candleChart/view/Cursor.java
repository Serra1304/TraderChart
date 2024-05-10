package candleChart.view;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa un componente que dibuja las líneas horizontal y vertical de un cursor en una interfaz de
 * usuario gráfica. Esta clase permite la renderización de las líneas de un cursor con la
 * visibilidad personalizada.
 */
public class Cursor extends JPanel {

    // Propiedades del cursor
    private int locationX;
    private int locationY;
    private boolean cursorVisible;

    /**
     * Construye un nuevo componente con los datos predeterminados del cursor.
     */
    public Cursor() {
        // Valores predeterminados del cursor.
        locationX = 0;
        locationY = 0;
        cursorVisible = false;

        // Configuración de la vista Cursor.
        setOpaque(false);
    }


    /**
     * Pinta el componente de cursor en el contexto gráfico.
     *
     * @param g El contexto grafico en el que pintar.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Pinta el cursor si cursorVisible está establecido como true.
        if (cursorVisible) {
            g.setColor(Color.GRAY);
            g.drawLine(locationX, 0, locationX, getHeight());
            g.drawLine(0, locationY, getWidth(), locationY);
        }
    }


    /**
     * Método que establece la posición del cursor en el gráfico.
     *
     * @param x Coordenadas del cursor en el eje X.
     * @param y Coordenadas del cursor en el eje Y.
     */
    public void setCursorLocation(int x, int y) {
        locationX = x;
        locationY = y;
        repaint();
    }


    /**
     * Obtiene la posición del cursor en el eje X.
     *
     * @return Coordenada del cursor en el eje X.
     */
    public int getLocationX() {
        return locationX;
    }


    /**
     * Obtiene la posición del cursor en el eje Y.
     *
     * @return La posición del cursor en el eje Y.
     */
    public int getLocationY() {
        return locationY;
    }


    /**
     * Establece la visibilidad del cursor. En caso de establecer cursorVisible como true, el cursor será pintado en el
     * componente. En caso de ser false, este no será pintado.
     *
     * @param cursorVisible Visibilidad del cursor.
     */
    public void setCursorVisible(boolean cursorVisible) {
        this.cursorVisible = cursorVisible;
        repaint();
    }


    /**
     * Obtiene la visibilidad del cursor. En caso de que el cursor sea visible, devolverá true, en caso contrario
     * devolverá false.
     *
     * @return Visibilidad del cursor.
     */
    public boolean isCursorVisible() {
        return cursorVisible;
    }
}
