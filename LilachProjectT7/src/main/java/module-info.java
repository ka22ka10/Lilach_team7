module org.openjfx.LilachProjectT7 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.LilachProjectT7 to javafx.fxml;
    exports org.openjfx.LilachProjectT7;
}