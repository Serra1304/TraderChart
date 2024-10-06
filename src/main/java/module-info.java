module es.gtorresdev.jfxtradechart {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.xml;


    opens es.gtorresdev.jfxtradechart to javafx.fxml;
    opens es.gtorresdev.jfxtradechart.componets to javafx.fxml;
    exports es.gtorresdev.jfxtradechart;
    exports es.gtorresdev.jfxtradechart.componets;
    exports es.gtorresdev.jfxtradechart.models;
    exports es.gtorresdev.jfxtradechart.services;
    opens es.gtorresdev.jfxtradechart.services to javafx.fxml;
    exports es.gtorresdev.jfxtradechart.charts;
    opens es.gtorresdev.jfxtradechart.charts to javafx.fxml;
    opens es.gtorresdev.jfxtradechart.models to javafx.fxml;
}