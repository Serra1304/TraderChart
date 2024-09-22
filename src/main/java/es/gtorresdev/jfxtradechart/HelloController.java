package es.gtorresdev.jfxtradechart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

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
        splitPane.getItems().add(new ChartCanvas());
        splitPane.getItems().add(new ChartCanvas());
    }
}