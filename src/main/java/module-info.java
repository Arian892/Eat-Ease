module com.example.EatEase {
    requires javafx.controls;
    requires javafx.fxml;

    opens Admin_Server to javafx.base ;
    opens com.example.EatEase to javafx.fxml;
    opens Restaurant_Client to javafx.fxml;
    exports com.example.EatEase;
    exports  Restaurant_Client ;
}