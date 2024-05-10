package candleChart.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Cursor;

public class ChartArea extends JPanel{

    public ChartArea() {
        setLayout(new OverlayLayout(this));
        setBorder(new LineBorder(Color.GRAY));
        setBackground(Color.BLACK);
        setCursor(java.awt.Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
}
