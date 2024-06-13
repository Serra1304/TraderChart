package candleChart.charts.base;

import candleChart.charts.candle.CandleView;
import candleChart.charts.util.CursorListener;

import java.util.List;

public interface Chart extends CursorListener {
    void addListener(ChartListener listener);
    void removeListener(ChartListener listener);
    void notifyRelativePosition(int relativePosition);
    void notifyXAxisInfo(List<String> xAixisInfoList);
    void notifyYAxisRange(double rangeUp, double rangeDown);
    void setCharInfo(Info info);
    CandleView getGraphicView();
}
