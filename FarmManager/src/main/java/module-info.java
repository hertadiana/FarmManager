module com.manager.farmmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    opens com.manager.farm to javafx.fxml;
    exports com.manager.farm;
}