package candleChart.controller;

import candleChart.interfaces.ChartObserver;
import candleChart.interfaces.ChartObserverAdapter;
import candleChart.view.ChartView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The cursor controller manages the position and visibility of the cursor on a chart.
 * Implement graph observer interfaces to receive updates.
 */
public class CursorController extends ChartObserverAdapter {
    private final ChartView chartView;
    private List<ChartObserver> observers = new ArrayList<>();

    private int positionCursorX, positionCursorY;
    private boolean currentCursorVisible;
    private boolean globalCursorVisible;

    private int relativePosition;

    /**
     * Create a new CursorController instance.
     *
     * @param chartView The chart view associated with the controller.
     */
    public CursorController(ChartView chartView) {
        this.chartView = chartView;

        positionCursorX = 0;
        positionCursorY = 0;
        currentCursorVisible = false;
        globalCursorVisible = true;
        relativePosition = 1;

        setupMouseListener();
    }

    /**
     * Update of relative position of the candle.
     *
     * @param relativePosition The relative position of the candle.
     */
    @Override
    public void updateCandleRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    /**
     * Gets the relative position of candle.
     *
     * @return The relative position.
     */
    public int getRelativePosition() {
        return relativePosition;
    }

    /**
     * Update observer list.
     *
     * @param observers The observer list.
     */
    @Override
    public void updateObservers(List<ChartObserver> observers) {
        if(observers != null) {
            this.observers = new ArrayList<>(observers);
        }
    }

    /**
     * Gets the observer list.
     *
     * @return The observer list.
     */
    public List<ChartObserver> getObservers() {
        return observers;
    }

    /**
     * Notify update de cursor position.
     */
    private void notifyUpdateCursor() {
        for (ChartObserver observer : observers) {
            observer.updateCursorPosition(positionCursorX, positionCursorY);
        }
    }

    /**
     * Update the cursor position.
     *
     * @param cursorX Cursor position on the X axis.
     * @param cursorY Cursor position on the Y axis.
     */
    private void updateCursor(int cursorX, int cursorY) {
        int cursorPosition = ((cursorX + relativePosition/2 -1) / relativePosition * relativePosition);
        if(cursorPosition < chartView.getWidth()) {
            this.positionCursorX = cursorPosition;
        }
        this.positionCursorY = cursorY;

        chartView.setCursor(this.positionCursorX, this.positionCursorY);
        notifyUpdateCursor();
    }

    /**
     * Notify the cursor visibility.
     */
    private void notifyCursorVisibility() {
        for (ChartObserver observer : observers) {
            observer.updateCursorVisible(currentCursorVisible);
        }
    }

    /**
     * Update the cursor visibility.
     *
     * @param CursorVisibility Cursor visibility.
     */
    private void updateCursorVisibility(boolean CursorVisibility) {
        this.currentCursorVisible = CursorVisibility;
        if(globalCursorVisible) {
            chartView.setCursorVisible(currentCursorVisible);
        }
        notifyCursorVisibility();
    }

    /**
     * Setup mouse listener.
     */
    public void setupMouseListener() {
        chartView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(currentCursorVisible) {
                    updateCursorVisibility(false);
                }
            }
        });

        chartView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                updateCursor(e.getX(), e.getY());
                if(!currentCursorVisible) {
                    updateCursorVisibility(true);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (e.getX() > 0 && e.getY() > 0 && e.getX() < chartView.getWidth() && e.getY() < chartView.getHeight()) {
                    updateCursor(e.getX(), e.getY());
                    if (!currentCursorVisible) {
                        updateCursorVisibility(true);
                    }
                }
            }
        });
    }

    /**
     * Sets global mouse visibility.
     *
     * @param visibility Mouse Visibility.
     */
    public void setGlobalCursosVisible(boolean visibility) {
        globalCursorVisible = visibility;
    }

    /**
     * Gets global mouse visibility.
     *
     * @return mouse visibility.
     */
    public boolean isGlobalCursosVisible() {
        return globalCursorVisible;
    }
}
