package candleChart.data;

import candleChart.model.Candle;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

    private int maxSize;
    private boolean autoSize;

    private List<Candle> candleList;

    /**
     * Constructor predeterminado
     */
    public Buffer() {
        maxSize = 1000;
        autoSize = false;

        candleList = new ArrayList<>();
    }


    /**
     * Método que devuelve el tamaño actual del buffer.
     *
     * @return El tamaño actual del buffer.
     */
    public int size() {
        return candleList.size();
    }


    /**
     * Método que obtiene el tamaño máximo del buffer.
     *
     * @return El tamaño máximo del buffer.
     */
    public int getMaxSize() {
        return maxSize;
    }


    /**
     * Método que establece el tamaño máximo del buffer.
     *
     * @param maxSize Nuevo tamaño máximo del buffer.
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;

        if(!autoSize && size() > maxSize) {
            candleList = candleList.subList(0, maxSize);
        }
    }


    /**
     * Método que obtiene si el buffer ajustara su tamaño en caso de que sea necesario.
     *
     * @return True si el buffer ajustara su tamaño en caso de que sea necesario, o False si el buffer ajustara su
     * tamaño al valor establecido en maxSize.
     */
    public boolean getAutoSize() {
        return autoSize;
    }


    /**
     * Método que establece si el buffer ajustara su tamaño en caso de que sea necesario. Para que el buffer ajuste su
     * tamaño en caso de necesidad, hay que establecer esta propiedad en true. En caso de que se establezca en false, el
     * tamaño del buffer se ajustara al valor establecido en la propiedad maxSize.
     *
     * @param autoSize Nuevo valor de autoSize.
     */
    public void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;

        if(size() > maxSize) {
            candleList = candleList.subList(0, maxSize);
        }
    }


    /**
     * Método que obtiene una lista con todos los elementos que contiene el buffer.
     *
     * @return Lista con los elementos del buffer.
     */
    public List<Candle> getAll() {
        return candleList;
    }


    /**
     * Método que obtiene el elemento del índice indicado.
     *
     * @param index Índice del elemento que se quiere obtener.
     * @return Elemento del índice especificado.
     */
    public Candle get(int index) {
        return candleList.get(index);
    }


    /**
     * Método que añade una lista de velas al buffer. Esta lista sobreescribe cualquier dato existente.
     * Si autoSize está definido como false, solo se almacenaran datos en el buffer asta alcanzar el tamaño definido en
     * maxSize. En el caso de estar definido como true, se almacenara todos los datos.
     *
     * @param candleList Lista de elementos a añadir al buffer.
     */
    public void addAll(ArrayList<Candle> candleList) {
        if(!candleList.isEmpty()) {
            int sizeLimit = Math.min(candleList.size(), maxSize);
            this.candleList.clear();
            this.candleList.addAll(autoSize? candleList: candleList.subList(0, sizeLimit));
        }
    }


    /**
     * Método que añade una lista de velas al principio del buffer. Esta lista no sobreescribe datos del buffer.
     * Si autoSize está definido como false solo se mantendrán en el buffer los datos necesarios asta alcanzar el tamaño
     * definido en maxSize, teniendo prioridad la lista añadida al principio del buffer. En caso de que autoSize este
     * definido como true, se mantendrán todos los datos en el buffer.
     *
     * @param candleList Lista de elementos a añadir al buffer.
     */
    public void addAllFirst(ArrayList<Candle> candleList) {
        if(!candleList.isEmpty()) {
            if(autoSize) {
                this.candleList.addAll(0, candleList);
            }
            else {
                if(candleList.size() >= maxSize) {
                    this.candleList = candleList.subList(0, maxSize);
                }
                else {
                    int remainingSize = maxSize - candleList.size();
                    List<Candle> newList = new ArrayList<>();

                    newList.addAll(candleList);
                    newList.addAll(this.candleList.subList(0, remainingSize));
                    this.candleList = newList;
                }
            }
        }
    }


    /**
     * Método que añade una lista de velas al final de buffer. Esta lista no sobreescribe datos en el buffer.
     * Si autoSize está definido como false solo se agregaran datos asta alcanzar el tamaño establecido en maxSize,
     * descartando todos los demás datos de la lista. En caso de que autoSize este definido como true, se agregaran
     * todos los datos al final del buffer.
     *
     * @param candleList Lista de elementos a añadir al buffer.
     */
    public void addAllLast(ArrayList<Candle> candleList) {
        if(!candleList.isEmpty()) {
            if(autoSize) {
                this.candleList.addAll(candleList);
            }
            else {
                if(this.candleList.size() < maxSize) {
                    int sizeLimit = Math.min(maxSize - this.candleList.size(), candleList.size());
                    this.candleList.addAll(candleList.subList(0, sizeLimit));
                }
            }
        }
    }


    /**
     * Método que añade una vela al principio del buffer. En caso de que el buffer tenga la propiedad autoSize
     * establecida como false, y el tamaño del buffer sea el establecido en maxSize, se irá descartando el último
     * elemento del buffer. En caso de que autoSize sea true, se irá incrementando el tamaño del buffer según sea
     * necesario.
     *
     * @param candle Vela a agregar al buffer.
     */
    public void addFirst(Candle candle) {
        if(candle != null) {
            if(autoSize) {
                candleList.add(0, candle);
            }
            else {
                if(candleList.size() < maxSize) {
                    candleList.add(0, candle);
                }
                else {
                    candleList.remove(maxSize - 1);
                    candleList.add(0, candle);
                }
            }
        }
    }


    /**
     * Método que añade una vela al final del buffer. En caso de que el buffer tenga la propiedad autoSize establecida
     * como false, solo se añadirán los datos siempre que el tamaño del buffer sea inferior al tamaño establecido en
     * la propiedad maxSize. En caso de que sea true, el tamaño del buffer se ajustara según sea necesario.
     *
     * @param candle Vela a agregar al buffer.
     */
    public void addLast(Candle candle) {
        if(candle != null) {
            if(autoSize) {
                candleList.add(candle);
            }
            else {
                if(candleList.size() < maxSize - 1) {
                    candleList.add(candle);
                }
            }
        }
    }

    /**
     * Método que elimina el elemento del buffer indicado en el índice.
     *
     * @param index Elemento a eliminar.
     */
    public void remove(int index) {
        candleList.remove(index);
    }

    /**
     * Método que elimina todos los elementos del buffer.
     */
    public void clear() {
        candleList.clear();
    }
}