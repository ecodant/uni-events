module com.uniquindio.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.uniquindio.finalproject to javafx.fxml;
    exports com.uniquindio.finalproject;
}
