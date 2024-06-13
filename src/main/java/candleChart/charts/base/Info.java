package candleChart.charts.base;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * Panel de información destinado a mostrar detalles sobre el gráfico. Proporciona métodos para agregar, actualizar,
 * eliminar y establecer la visibilidad de la información mostrada en el panel.
 */
public class Info extends JPanel {
    private static final Color FONT_COLOR = Color.GRAY;
    private static final Font FONT = new Font("Arial", Font.PLAIN, 12);


    /**
     * Constructor. Inicializa el panel de información con un tamaño predeterminado y un diseño de caja horizontal.
     */
    public Info() {
        setPreferredSize(new Dimension(50,20));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
    }


    /**
     * Agrega un nuevo elemento de información al panel.
     *
     * @param info La información a agregar.
     * @throws IllegalArgumentException Si se pasa un valor nulo como parámetro.
     */
    public void addInfo(String info) {
        if(info == null) {
            throw new IllegalArgumentException("El parámetro 'info' proporcionado es nulo");
        }

        JLabel lblInfo = new JLabel();
        lblInfo.setForeground(FONT_COLOR);
        lblInfo.setFont(FONT);
        lblInfo.setText(info);
        lblInfo.setBorder(new EmptyBorder(0, 0, 0, 10));
        add(lblInfo);
    }


    /**
     * Obtiene la información del índice especificado del panel.
     *
     * @param n El índice de la información deseada.
     * @return La información del índice especificado.
     * @throws IndexOutOfBoundsException Si se pasa un valor que no contiene ningún componente de información.
     */
    public String getInfo(int n) {
        if(this.getComponentCount() <= n || n < 0) {
            throw new IndexOutOfBoundsException("Valor fuera de rango");
        }

        JLabel lblInfo = (JLabel) getComponent(n);
        return lblInfo.getText();
    }


    /**
     * Actualiza la información del índice especificado del panel.
     *
     * @param n El índice de la información a actualizar.
     * @param info La nueva información a establecer.
     * @throws IndexOutOfBoundsException Si se pasa un valor que no contiene ningún componente de información.
     * @throws IllegalArgumentException Si se pasa un valor nulo como parámetro.
     */
    public void updateInfo(int n, String info) {
        if(this.getComponentCount() <= n || n < 0) {
            throw new IndexOutOfBoundsException("Valor fuera de rango");
        }

        if(info == null) {
            throw new IllegalArgumentException("El parámetro 'info' proporcionado es nulo");
        }

        JLabel lblInfo = (JLabel) this.getComponent(n);
        lblInfo.setText(info);
    }


    /**
     * Elimina la información del índice especificado del panel.
     *
     * @param n El índice de la información a eliminar.
     * @throws IndexOutOfBoundsException Si se pasa un valor que no contiene ningún componente de información.
     */
    public void deleteInfo(int n) {
        if(this.getComponentCount() <= n || n < 0) {
            throw new IndexOutOfBoundsException("Valor fuera de rango");
        }

        this.remove(n);
    }


    /**
     * Establece la visibilidad de la información del índice especificado.
     *
     * @param n El índice de la información cuya visibilidad se va a cambiar.
     * @param visibility true para hacer visible la información, false para ocultarla.
     * @throws IndexOutOfBoundsException Si se pasa un valor que no contiene ningún componente de información.
     */
    public void setVisibilityInfo(int n, boolean visibility) {
        if(this.getComponentCount() <= n || n < 0) {
            throw new IndexOutOfBoundsException("Valor fuera de rango");
        }

        JLabel lblInfo = (JLabel) this.getComponent(n);
        lblInfo.setVisible(visibility);
    }


    /**
     * Obtiene la visibilidad de la información del índice especificado.
     *
     * @param n El índice de la información que se quiere obtener la visibilidad.
     * @return true si la información está visible o false de lo contrario.
     * @throws IndexOutOfBoundsException Si se pasa un valor que no contiene ningún componente de información.
     */
    public boolean isVisibleInfo(int n) {
        if(this.getComponentCount() <= n || n < 0) {
            throw new IndexOutOfBoundsException("Valor fuera de rango");
        }

        JLabel lblInfo = (JLabel) this.getComponent(n);
        return lblInfo.isVisible();
    }


    /**
     * Elimina todos los componentes de información del panel.
     */
    public void cleanInfo() {
        for(int i = getComponentCount() -1; i >= 0; i--) {
            this.remove(i);
        }
    }
}

