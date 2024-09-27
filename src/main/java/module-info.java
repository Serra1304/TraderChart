module es.gtorresdev.jfxtradechart {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens es.gtorresdev.jfxtradechart to javafx.fxml;
    opens es.gtorresdev.jfxtradechart.componets to javafx.fxml;
    exports es.gtorresdev.jfxtradechart;
    exports es.gtorresdev.jfxtradechart.componets;
    exports es.gtorresdev.jfxtradechart.models;
}