package candleChart.charts.base;

public class Range {
    private double lowerRange;
    private double upperRange;

    public Range() {
        lowerRange = 0;
        upperRange = 1;
    }

    public Range(double lowerRange, double upperRange) {
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    public double getLowerRange() {
        return lowerRange;
    }

    public void setLowerRange(double lowerRange) {
        this.lowerRange = lowerRange;
    }

    public double getUpperRange() {
        return upperRange;
    }

    public void setUpperRange(double upperRange) {
        this.upperRange = upperRange;
    }

    public void setRange(double lowerRange, double upperRange) {
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    public double difRange() {
        return upperRange - lowerRange;
    }
}
