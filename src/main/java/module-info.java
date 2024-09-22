module es.gtorresdev.jfxtradechart {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens es.gtorresdev.jfxtradechart to javafx.fxml;
    exports es.gtorresdev.jfxtradechart;
}