package Restaurant_Client;

import Admin_Server.Restaurant;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.OrderObject2;
import util.SocketWrapper;

import javafx.application.Application;

public class RestaurantClient extends  Application{
    private Stage stage ;
    private String username ;
    public String getUsername()
    {
        return this.username;
    }

    public OrderObject2 orderObject2;



    private Restaurant restaurant ;
    private SocketWrapper socketWrapper ;
    public int totalOrder = 0  ;
    public SocketWrapper getSocketWrapper ()
    {
        return socketWrapper ;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Stage getStage() {
        return stage;
    }

    public void OrderNotification (OrderObject2 orderObject2)
    {
        this.orderObject2 = orderObject2;
    }
    public void start (Stage primaryStage) throws  Exception
    {
        stage = primaryStage ;
        showLoginPage ();
    }

    public void showLoginPage () throws Exception
    {
        FXMLLoader loader = new FXMLLoader() ;
        loader.setLocation(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load ();


        // loading the controller
        LoginPageController controller = loader.getController();
        controller.setMain(this);



        stage.setTitle("login");
        stage.setScene(new Scene(root,827,521));
        stage.show ();

        connectToServer();

    }

    public void ShowRestaurantInfo ()  throws  Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantInfo.fxml"));
        Parent root = loader.load();

        // Loading the controllerthis. username = username ;
        RestaurantInfoController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("RestaurantInfo");
        stage.setScene(new Scene(root, 827, 521));
        stage.show();


    }

    public void showHomePage (String username , Restaurant restaurant) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        this.restaurant = restaurant ;
        this. username = username ;
        HomePageController controller = loader.getController();
        controller.setMain(this,restaurant);

        stage.setTitle("RestaurantShow");
        stage.setScene(new Scene(root, 827, 521));
        stage.show();


    }

    public void showAlert ()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();

    }
    private void connectToServer () throws  Exception
    {
        String serverAddress = "127.0.0.1" ;
        int serverPort = 54265 ;
        socketWrapper = new SocketWrapper(serverAddress,serverPort) ;


    }






    public static void main(String[] args) {
        launch();

    }
    
}
