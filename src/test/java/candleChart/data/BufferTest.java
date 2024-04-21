package candleChart.data;

import candleChart.model.Candle;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BufferTest {

    @Test
    public void testConstructorAndInitialValue() {
        Buffer buffer = new Buffer();

        assertEquals(1000, buffer.getMaxSize());
        assertFalse(buffer.getAutoSize());
        assertEquals(0, buffer.size());
    }

    @Test
    public void testPropertiesGettersAndSetters() {
        Buffer buffer = new Buffer();

        buffer.setMaxSize(5000);
        buffer.setAutoSize(true);

        assertEquals(5000, buffer.getMaxSize());
        assertTrue(buffer.getAutoSize());
    }

    @Test
    public void testSize() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();

        buffer.addAll(arrayList);

        assertEquals(arrayList.size(), buffer.size());
    }

    @Test
    public void testSetMaxSizeWithAutoSizeFalse() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        int newSize = arrayList.size() - 5;

        buffer.addAll(arrayList);
        buffer.setMaxSize(newSize);

        assertEquals(newSize, buffer.getMaxSize());
        assertEquals(newSize, buffer.size());
    }

    @Test
    public void testSetMaxSizeWithAutoSizeTrue() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        int newSize = arrayList.size() - 5;

        buffer.setAutoSize(true);
        buffer.addAll(arrayList);
        buffer.setMaxSize(newSize);

        assertEquals(newSize, buffer.getMaxSize());
        assertEquals(arrayList.size(), buffer.size());
    }

    @Test
    public void testSetAutoSizeWithSizeGreaterThanMaxsize() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        int newSize = arrayList.size() - 5;

        buffer.setAutoSize(true);
        buffer.addAll(arrayList);
        buffer.setMaxSize(newSize);
        buffer.setAutoSize(false);

        assertEquals(newSize, buffer.size());
    }

    @Test
    public void testSetAutoSizeWithSizeLessThanMaxsize() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        int newSize = arrayList.size() + 5;

        buffer.setAutoSize(true);
        buffer.addAll(arrayList);
        buffer.setMaxSize(newSize);
        buffer.setAutoSize(false);

        assertEquals(arrayList.size(), buffer.size());
    }

    @Test
    public void testGetAll() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();

        buffer.addAll(arrayList);

        assertEquals(buffer.size(), buffer.getAll().size());
    }

    @Test
    public void testGet() {
        Buffer buffer = new Buffer();

        for(int i = 0; i < 5; i++) {
            buffer.addLast(new Candle(LocalDateTime.now(), i, i, i, i));
        }

        assertEquals(2, buffer.get(2).closePrice());
        assertEquals(4, buffer.get(4).closePrice());
    }

    @Test
    public void testAddAllFirstWithAutoSizeFalse() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList1 = candleList();
        ArrayList<Candle> arrayList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            arrayList.add(new Candle(LocalDateTime.now(), i, i, i, i));
        }

        buffer.setMaxSize(arrayList1.size());
        buffer.addAll(arrayList1);
        buffer.addAllFirst(arrayList);

        assertEquals(2, buffer.get(2).closePrice());
        assertEquals(4, buffer.get(4).closePrice());
        assertEquals(arrayList1.size(), buffer.size());
        assertNotEquals(arrayList1.get(arrayList1.size()-1), buffer.get(buffer.size()-1));
    }

    @Test
    public void testAddAllFirstWithAutoSizeTrue() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList1 = candleList();
        ArrayList<Candle> arrayList2 = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            arrayList2.add(new Candle(LocalDateTime.now(), i, i, i, i));
        }

        buffer.setAutoSize(true);
        buffer.setMaxSize(arrayList1.size());
        buffer.addAll(arrayList1);
        buffer.addAllFirst(arrayList2);

        assertEquals(2, buffer.get(2).closePrice());
        assertEquals(4, buffer.get(4).closePrice());
        assertEquals(arrayList1.get(arrayList1.size()-1), buffer.get(buffer.size()-1));
    }

    @Test
    public void testAddAllLastWithAutoSizeFalse() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList1 = candleList();
        ArrayList<Candle> arrayList2 = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            arrayList2.add(new Candle(LocalDateTime.now(), i, i, i, i));
        }

        buffer.setMaxSize(arrayList1.size() + 3);
        buffer.addAll(arrayList1);
        buffer.addAllLast(arrayList2);

        assertEquals(2, buffer.get(buffer.size()-1).closePrice());
        assertEquals(0, buffer.get(buffer.size()-3).closePrice());
        assertEquals(arrayList1.size()+3, buffer.size());
    }

    @Test
    public void testAddAllLastWithAutoSizeTrue() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList1 = candleList();
        ArrayList<Candle> arrayList2 = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            arrayList2.add(new Candle(LocalDateTime.now(), i, i, i, i));
        }

        buffer.setAutoSize(true);
        buffer.setMaxSize(arrayList1.size());
        buffer.addAll(arrayList1);
        buffer.addAllLast(arrayList2);

        assertEquals(4, buffer.get(buffer.size()-1).closePrice());
        assertEquals(0, buffer.get(buffer.size()-5).closePrice());
        assertEquals(arrayList1.size()+5, buffer.size());
    }

    @Test
    public void testAddFirstWithAutoSizeFalse() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        Candle candle = new Candle(LocalDateTime.now(), 5, 5, 5, 5);

        buffer.setMaxSize(arrayList.size());
        buffer.addAll(arrayList);
        buffer.addFirst(candle);

        assertEquals(buffer.get(0), candle);
        assertEquals(arrayList.size(), buffer.size());
    }

    @Test
    public void testAddFirstWithAutoSizeTrue() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        Candle candle = new Candle(LocalDateTime.now(), 5, 5, 5, 5);

        buffer.setAutoSize(true);
        buffer.setMaxSize(arrayList.size());
        buffer.addAll(arrayList);
        buffer.addFirst(candle);

        assertEquals(buffer.get(0), candle);
        assertEquals(arrayList.size() + 1, buffer.size());
    }

    @Test
    public void testAddLastWithAutoSizeFalse() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        Candle candle = new Candle(LocalDateTime.now(), 5, 5, 5, 5);

        buffer.setMaxSize(arrayList.size());
        buffer.addAll(arrayList);
        buffer.addLast(candle);

        assertNotEquals(buffer.get(arrayList.size() - 1), candle);
        assertEquals(arrayList.size(), buffer.size());
    }

    @Test
    public void testAddLastWithAutoSizeTrue() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();
        Candle candle = new Candle(LocalDateTime.now(), 5, 5, 5, 5);

        buffer.setAutoSize(true);
        buffer.setMaxSize(arrayList.size());
        buffer.addAll(arrayList);
        buffer.addLast(candle);

        assertEquals(buffer.get(buffer.size() -1), candle);
        assertEquals(arrayList.size() + 1, buffer.size());
    }

    @Test
    public void testRemove() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            arrayList.add(new Candle(LocalDateTime.now(), i, i, i, i));
        }

        buffer.addAll(arrayList);
        buffer.remove(2);

        assertNotEquals(buffer.get(2), arrayList.get(2));
    }

    @Test
    public void testClear() {
        Buffer buffer = new Buffer();
        ArrayList<Candle> arrayList = candleList();

        buffer.addAll(arrayList);
        buffer.clear();

        assertEquals(0, buffer.size());
    }

    private ArrayList<Candle> candleList() {
        ArrayList<Candle> arrayList = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            arrayList.add(new Candle(LocalDateTime.now(),
                    1.1,
                    1.2,
                    1.0,
                    1.0));
        }
        return arrayList;
    }
}
