package Restaurant_Client;

import Admin_Server.Restaurant;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    public TextField userText;
    public PasswordField passwordText;
    public Restaurant restaurant ;
    public  String  Username;

    public Button loginsubmit;
    public Button loginreset;
    RestaurantClient client;








    void setMain (RestaurantClient client)
    {
        this.client = client ;
        loginsubmit.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
        loginreset.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
    }

    public void LoginSubmit(ActionEvent actionEvent) {
        String username = userText.getText() ;
        String password = passwordText.getText();
        try
        {
            client.getSocketWrapper().write("r,"+username+","+password);
            this.Username = "r,"+ username+ "," + password ;
            boolean flag = (boolean)client.getSocketWrapper().read();
            if (flag)
            {
                try {
                    this.restaurant = (Restaurant) client.getSocketWrapper().read();
                    System.out.println("got the list");
                    client.showHomePage(this.Username,this.restaurant);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }


            }
            else {
                client.showAlert();
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public void LoginReset(ActionEvent actionEvent) {
        userText.setText(null);
        passwordText.setText(null);

    }
}
