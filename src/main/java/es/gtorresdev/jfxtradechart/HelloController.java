package es.gtorresdev.jfxtradechart;

import es.gtorresdev.jfxtradechart.charts.candle.Candle;
import es.gtorresdev.jfxtradechart.charts.candle.CandleChart;
import es.gtorresdev.jfxtradechart.componets.SymbolDimension;
import es.gtorresdev.jfxtradechart.services.ChartManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import java.time.LocalDateTime;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private SplitPane splitPane;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() {
        ChartManager chartManager = new ChartManager();
        CandleChart candleChart = new CandleChart(chartManager);
        candleChart.addToBuffer(new Candle(LocalDateTime.now(), 5, 8, 3, 4, 20));
        candleChart.addToBuffer(new Candle(LocalDateTime.now(), 4, 6, 2, 5, 20));
        candleChart.addToBuffer(new Candle(LocalDateTime.now(), 5, 10, 5, 8, 20));
        candleChart.addToBuffer(new Candle(LocalDateTime.now(), 8, 9, 5, 6, 20));

        //ChartManager chartManager = new ChartManager();
        chartManager.addChart(candleChart);
        chartManager.setSizeElement(SymbolDimension.SMALL);

        splitPane.getItems().add(chartManager);
        splitPane.getItems().add(new ChartManager());

        candleChart.addToBuffer(new Candle(LocalDateTime.now(), 6, 20, 3, 15, 20));
        candleChart.addToBuffer(new Candle(LocalDateTime.now(), 13, 17, 9, 10, 20));
    }
}