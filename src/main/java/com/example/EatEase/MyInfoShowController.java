package com.example.EatEase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class MyInfoShowController {
    private CustomerClient client ;
    private int totalorder ;
    private double  totalmoney ;

    @FXML
    private Button BackButton;

    @FXML
    private Label TotalMoneySpentShow;

    @FXML
    private Label TotalOrderCountShow;

    @FXML
    private Label UserNameShow;

    @FXML
    void BackButtonAction(ActionEvent event)  {
        try {
            client.showCustomerHomePage(client.getCustomerUsername(), client.getRestaurantmanager());
        }
        catch (Exception e)

        {
            System.out.println(e);
        }

    }
    void setMain (CustomerClient client , int totalorder , double totalmoney)
    {
        this.client = client ;
        this.totalmoney = totalmoney;
        this.totalorder = totalorder ;

        BackButton.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
        TotalOrderCountShow.setStyle("-fx-background-color : black ; -fx-text-fill : white; -fx-alignment : center ;");
        TotalMoneySpentShow.setStyle("-fx-background-color : black ; -fx-text-fill : white ; -fx-alignment : center ;");
         UserNameShow.setStyle("-fx-background-color : black ; -fx-text-fill : white ; -fx-alignment : center ;");
      //  UserNameShow.setTextAlignment(TextAlignment.CENTER);
       // UserNameShow.setStyle("-fx-alignment : center ;");


        TotalOrderCountShow .setText("Total Ordered Items : " + totalorder );
        TotalMoneySpentShow.setText("Total Money Spent " + totalmoney + "$");
       String []arr = client.getCustomerUsername().split(",") ;
       UserNameShow.setText("Username  : " + arr[1]);

    }

}
