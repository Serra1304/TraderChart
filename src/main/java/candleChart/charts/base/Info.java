package candleChart.charts.base;

import javax.swing.*;
import java.awt.*;


/**
 * Panel de información destinado a mostrar detalles sobre el gráfico.
 */
public class Info extends JPanel {
    private static final Color FONT_COLOR = Color.GRAY;
    private static final Font FONT = new Font("Arial", Font.PLAIN, 12);

    private final JLabel lblInfo;

    /**
     * Constructor predeterminado del panel de información
     */
    public Info() {
        lblInfo = new JLabel();
        lblInfo.setForeground(FONT_COLOR);
        lblInfo.setFont(FONT);
        add(lblInfo);

        setPreferredSize(new Dimension(50,20));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);}


    /**
     * Establece la información al panel.
     *
     * @param info la nueva información del panel.
     */
    public void setInfo(String info) {
        lblInfo.setText(info);
    }


    /**
     * Obtiene la información del panel.
     *
     * @return la información del panel.
     */
    public String getInfo() {
        return lblInfo.getText();
    }
}

