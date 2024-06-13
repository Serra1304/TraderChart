package candleChart.charts.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un componente que dibuja las líneas horizontal y vertical de un cursor en una interfaz de
 * usuario gráfica. Esta clase permite la renderización de las líneas de un cursor con la
 * visibilidad personalizada.
 */
public class Cursor extends JPanel {

    private int locationX;
    private int locationY;
    private boolean cursorVisible;

    private final List<CursorListener> listenerList;

    public Cursor() {
        locationX = 0;
        locationY = 0;
        cursorVisible = false;

        listenerList = new ArrayList<>();

        setOpaque(false);
        setupMouse();
    }

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

    public void addCursorListener(CursorListener listener) {
        listenerList.add(listener);
    }

    public void removedCursorListener(CursorListener listener) {
        listenerList.remove(listener);
    }

    private void notifyCursorListener() {
        for(CursorListener listener : listenerList) {
            listener.cursorMoved(locationX, locationY);
        }
    }

    private void notifyVisibilityChange() {
        for(CursorListener listener: listenerList) {
            listener.cursorVisibility(cursorVisible);
        }
    }

    private void setupMouse() {
        this.addMouseListener(new MouseAdapter() {

            /**
             * Cuando el ratón sale del gráfico, los elementos del cursor, precio y fecha a la que apunta el cursor e
             * información de la vela, son ocultados.
             *
             * @param e Evento del ratón.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
                cursorVisible = false;
                notifyVisibilityChange();

                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                locationX = e.getX();
                locationY = e.getY();
                notifyCursorListener();

                if(!cursorVisible) {
                    setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                    cursorVisible = true;
                    notifyVisibilityChange();
                }
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (e.getX() > 0 && e.getY() > 0 && e.getX() < getWidth() && e.getY() < getHeight()) {

                    locationX = e.getX();
                    locationY = e.getY();
                    notifyCursorListener();

                    if(!cursorVisible) {
                        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                        cursorVisible = true;
                        notifyVisibilityChange();
                    }
                    repaint();
                }
            }
        });
    }
}
