package candleChart.controller;

public enum CandleSize {
    VERY_SMALL(1, 4, 3),
    SMALL(2, 8, 5),
    LARGE(4, 16, 11),
    VERY_LARGE(5, 32, 19);

    private final int index;
    private final int relativePosition;
    private final int candleWidth;

    CandleSize(int index, int relativePosition, int candleWidth) {
        this.index = index;
        this.relativePosition = relativePosition;
        this.candleWidth = candleWidth;
    }

    public int getIndex() {
        return index;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public int getCandleWidth() {
        return candleWidth;
    }
}
