module com.technikum.energycommunityui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.technikum.energycommunityui to javafx.fxml;
    exports com.technikum.energycommunityui;
}