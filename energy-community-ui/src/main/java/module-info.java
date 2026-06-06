module com.technikum.energycommunityui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens com.technikum.energycommunityui to javafx.fxml;
    exports com.technikum.energycommunityui;
}