package candleChart.data;

import candleChart.model.Candle;

import java.util.ArrayList;
import java.util.List;

public class DataBuffer {
    private final DataSource dataSource;
    private final List<Candle> candleList;
    private int sizeBuffer;

    public DataBuffer(DataSource dataSource) {
        this.dataSource = dataSource;
        sizeBuffer = 1000;
        candleList = new ArrayList<>();
    }

    public void readData() {
        while(true) {
            List<Candle> tempList = dataSource.getNextData();
            int listSize = candleList.size();

            if (tempList.isEmpty()) {
                break;

            } else if (tempList.size() <= sizeBuffer - listSize) {
                candleList.addAll(tempList);

            } else {
                candleList.addAll(tempList.subList(0, sizeBuffer - listSize));
                break;
            }
        }
        //System.out.println(candleList.size());
    }

    public List<Candle> read(){
        return candleList;
    }

    public void setSizeBuffer(int sizeBuffer) {
        this.sizeBuffer = sizeBuffer;
    }

    public Candle getcandleIndex(int index) {
        return candleList.get(index);
    }

    public int getSizeBuffer() {
        return sizeBuffer;
    }
}
