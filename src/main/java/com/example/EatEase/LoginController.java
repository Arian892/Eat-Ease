package com.example.EatEase;

import Admin_Server.Restaurant;
import Admin_Server.Restaurantmanager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class LoginController {

     private CustomerClient client ;

     private Restaurantmanager restaurantmanager ;
    @FXML
    public Button loginsubmit;
    @FXML
    public Button loginreset;
    @FXML
    public TextField userText;
    @FXML
    public PasswordField passwordText;

    public void LoginSubmit(ActionEvent actionEvent) {
        String username = userText.getText();
        String password = passwordText.getText() ;
        if (username.equals(password))
        {
            try
            {

               client.getSocketWrapper().write ("c," + username);
              restaurantmanager = (Restaurantmanager) client.getSocketWrapper().read();
                client.showCustomerHomePage("c,"+username,restaurantmanager);
             // show ();


            }
            catch (Exception e )
            {
                System.out.println(e);
            }
        }
        else {
            client.showAlert();
        }
    }

    public void LoginReset(ActionEvent actionEvent) {
        userText.setText(null);
        passwordText.setText(null);

    }
    void setMain (CustomerClient client)
    {
        this.client = client ;

        loginsubmit.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
        loginreset.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
    }

}