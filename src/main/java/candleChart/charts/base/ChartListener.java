package candleChart.charts.base;

import java.util.List;

public interface ChartListener {
    void OnRelativePosition(int relativePosition);
    void OnChartInfo(String info);
    void OnXAxisInfo(List<String> stringInfoList);
    void OnPriceRange(double rangeUp, double rangeDown);
}
