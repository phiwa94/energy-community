module com.technikum.energycommunityui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com.technikum.energycommunityui to javafx.fxml;
    exports com.technikum.energycommunityui;
}