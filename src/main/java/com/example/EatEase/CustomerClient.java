package com.example.EatEase;

import Admin_Server.Food;
import Admin_Server.Restaurant;
import Admin_Server.Restaurantmanager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.SocketWrapper;

import java.util.List ;
import java.util.Scanner ;


import java.io.IOException;

public class CustomerClient extends Application {


    private Stage stage ;
    public int totalorder = 0 ;
    public double totalmoney = 0;
    private String CustomerUsername ;


    private Restaurantmanager restaurantmanager ;
    private Restaurant SelectedRestaurant ;
    private SocketWrapper socketWrapper ;

    public Restaurantmanager getRestaurantmanager() {
        return restaurantmanager;
    }

    public SocketWrapper getSocketWrapper ()
    {
        return socketWrapper ;
    }
    public String getCustomerUsername ()
    {
        return CustomerUsername ;
    }




    public Stage getStage ()
    {
        return stage ;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage ;

        showLoginPage ();

    }

    private void connectToServer () throws Exception
    {
        String serverAddress = "127.0.0.1" ;
        int serverPort = 54265 ;
       socketWrapper = new SocketWrapper(serverAddress,serverPort) ;

    }

    public void showInfoPage () throws  Exception
    {



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyInfoShow.fxml"));
        Parent root = loader.load();


        // Loading the controller
        MyInfoShowController controller = loader.getController();
        controller.setMain (this,totalorder,totalmoney);



        stage.setTitle("My Info");
        stage.setScene(new Scene (root,827,521) );
        stage.show ();

    }



    public void showLoginPage () throws Exception
    {
        FXMLLoader loader = new FXMLLoader() ;
        loader.setLocation(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load ();


        // loading the controller
        LoginController controller = loader.getController();
        controller.setMain(this);



        stage.setTitle("login");
        stage.setScene(new Scene(root,827,521));
        stage.show ();

        connectToServer();

    }

    public void showAlertForOrder()
    {
//        Alert alert = new Alert(Alert.AlertType.E);
//        alert.setTitle("Incorrect Credentials");
//        alert.setHeaderText("Incorrect Credentials");
//        alert.setContentText("The username and password you provided is not correct.");
//        alert.showAndWait();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText("Order Placed Successfully");


        alert.setContentText("Thank you for placing your order. Your order has been confirmed.");

        alert.showAndWait();


    }

    public void showAlert ()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();

    }

    public static void main(String[] args) {
        launch();
    }




    public void showCustomerHomePage (String username, Restaurantmanager restaurantmanager) throws Exception

    {
        this.CustomerUsername = username ;
        this.restaurantmanager = restaurantmanager;


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerHomePage.fxml"));
        Parent root = loader.load();


        // Loading the controller
        CustomerHomePageController controller = loader.getController();
        controller.setMain (this,restaurantmanager);

        System.out.println("new edit to check which was edited");
        stage.setTitle("Customer Home page");
        stage.setScene(new Scene (root,827,521) );
        stage.show ();


    }


}