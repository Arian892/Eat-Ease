package Restaurant_Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RestaurantInfoController {
    private RestaurantClient client ;

    @FXML
    private Button BackButton;

    @FXML
    private Label Categories;

    @FXML
    private Label Price;

    @FXML
    private Label RestaurantId;

    @FXML
    private Label RestaurantName;

    @FXML
    private Label Score;

    @FXML
    private Label TotalOrderedAccepted;

    @FXML
    private Label Zipcode;

    @FXML
    void BackButtonAction(ActionEvent event) {
        try {
            client.showHomePage(client.getUsername(),client.getRestaurant());
        }
        catch (Exception e)

        {
            System.out.println(e);
        }

    }

    public void setMain (RestaurantClient client )
    {
        this.client = client ;
        BackButton.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
        RestaurantName.setText("Restaurant Name : "+client.getRestaurant().getName());
        RestaurantId.setText("Restaurant Id : "+client.getRestaurant().getId()+"");
        Price.setText("Restaurant Price : "+client.getRestaurant().getPrice());
        Score.setText("Restaurant Score : "+client.getRestaurant().getScore()+"");
        Zipcode.setText("Restaurant Zipcode : "+client.getRestaurant().getZipCode());
      //  Categories.setText(client.getRestaurant().getCategory().get(0) + client.getRestaurant().getCategory().);
        TotalOrderedAccepted.setText("Total order Accepted " + client.totalOrder);


    }


}
