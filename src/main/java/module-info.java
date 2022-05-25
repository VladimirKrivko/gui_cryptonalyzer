module com.example.gui_cryptonalyzer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.gui_cryptonalyzer to javafx.fxml;
    exports com.example.gui_cryptonalyzer;
    exports com.example.logics;
    opens com.example.logics to javafx.fxml;
}