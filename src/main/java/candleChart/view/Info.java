package candleChart.view;

import javax.swing.*;
import java.awt.*;

public class Info extends JPanel {
    private static final Color FONT_COLOR = Color.GRAY;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Font FONT = new Font("Arial", Font.PLAIN, 12);

    public Info() {
        setPreferredSize(new Dimension(50,20));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(BACKGROUND_COLOR);
    }

    public void addInfo(String info) {
        JLabel lblInfo = new JLabel();
        lblInfo.setForeground(FONT_COLOR);
        lblInfo.setFont(FONT);
        lblInfo.setText(info);
        add(lblInfo);
    }

    public String getInfo(int n) {
        JLabel lblInfo = (JLabel) this.getComponent(n);
        return lblInfo.getText();
    }

    public void updateInfo(int n, String info) {
        JLabel lblInfo = (JLabel) this.getComponent(n);
        lblInfo.setText(info);
    }

    public void deleteInfo(int n) {
        this.remove(n);
    }

    public void setVisibleInfo(int n, boolean visibility) {
        JLabel lblInfo = (JLabel) this.getComponent(n);
        lblInfo.setVisible(visibility);
    }
}
