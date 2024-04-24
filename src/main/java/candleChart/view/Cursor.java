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
    private int positionX;
    private int positionY;
    private boolean cursorVisible;

    /**
     * Construye un nuevo componente con los datos predeterminados del cursor.
     */
    public Cursor() {
        // Valores predeterminados del cursor.
        positionX = 0;
        positionY = 0;
        cursorVisible = true;

        // Se establece el fondo como transparente.
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
            g.drawLine(positionX, 0, positionX, getHeight());
            g.drawLine(0, positionY, getWidth(), positionY);
        }
    }


    /**
     * Método que establece la posición del cursor en el gráfico.
     *
     * @param x Coordenadas del cursor en el eje X.
     * @param y Coordenadas del cursor en el eje Y.
     */
    public void setCursorPosition(int x, int y) {
        positionX = x -1;
        positionY = y -1;
        repaint();
    }


    /**
     * Obtiene la posición del cursor en el eje X.
     *
     * @return Coordenada del cursor en el eje X.
     */
    public int getPositionX() {
        return positionX;
    }


    /**
     * Obtiene la posición del cursor en el eje Y.
     *
     * @return La posición del cursor en el eje Y.
     */
    public int getPositionY() {
        return positionY;
    }


    /**
     * Establece la visibilidad del cursor. En caso de establecer cursorVisible como true, el cursor será pintado en el
     * componente. En caso de ser false, este no será pintado.
     *
     * @param cursorVisible Visibilidad del cursor.
     */
    public void setCursorVisible(boolean cursorVisible) {
        this.cursorVisible = cursorVisible;
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
