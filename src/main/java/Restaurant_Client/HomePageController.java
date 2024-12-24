package Restaurant_Client;

import Admin_Server.Food;
import Admin_Server.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.OrderObject2;

import java.util.ArrayList;
import java.util.List;

public class HomePageController {

    public Button RestaurantInfo;
    RestaurantClient client ;
    Restaurant restaurant ;



    @FXML
    private Button AddingFoodButton;
    private List<Food> AllFoodList = new ArrayList<>() ;
    public ObservableList<Food>AllFoodob ;

    @FXML
    private ListView<Food> AlllFoodsInRestaurant;

    @FXML
    private Button FoodListButton;

    @FXML
    private ChoiceBox FoodSearchingOption;

    @FXML
    private TextField HighPrice;

    @FXML
    private Button LogoutButton;

    @FXML
    private TextField LowPrice;

    @FXML
    private Button OrderListButton;
    private List<Food> OrderedFoodList = new ArrayList<>() ;
    public ObservableList<Food>OrderedFoodob ;


    @FXML
    private ListView<Food> OrderedFoodsInTheRestaurant;

    @FXML
    private Button SearchingButton;

    @FXML
    private TextField SearchingByString;

    @FXML
    void AddingFoodButtonAction(ActionEvent event) {
        AddingFoodButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        FoodListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        RestaurantInfo.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        OrderListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

    }

    @FXML
    void FoodListButtonAction(ActionEvent event) {
        FoodlistVisibility();
        AllFoodob = FXCollections.observableList(AllFoodList) ;
        AlllFoodsInRestaurant.setItems(AllFoodob);

    }

    @FXML
    void LogoutAction(ActionEvent event) {
        try
        {
            client.showLoginPage();

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void getNotification(OrderObject2 orderObject2) throws  Exception
    {
        client.totalOrder++ ;
        System.out.println("received order" + orderObject2.getCustomerName()+ "   " + orderObject2.getFood().getName());
        OrderedFoodList.add (orderObject2.getFood());
        OrderedFoodsInTheRestaurant.setVisible(true);
        AlllFoodsInRestaurant.setVisible(false);


        OrderedFoodob = FXCollections.observableList(OrderedFoodList);
        OrderedFoodsInTheRestaurant.setItems(OrderedFoodob);
        setCustomOrderFoodCellFactory();
        OrderedFoodsInTheRestaurant.refresh();


    }


    @FXML
    void OrderButtonAction(ActionEvent event) {
        ordersVisibility();
        OrderedFoodsInTheRestaurant.setVisible(true);
        AlllFoodsInRestaurant.setVisible(false);
        OrderedFoodob = FXCollections.observableList(OrderedFoodList);
        OrderedFoodsInTheRestaurant.setItems(OrderedFoodob);
        setCustomOrderFoodCellFactory();
        OrderedFoodsInTheRestaurant.refresh();

    }

    public void SearchingActionButton(ActionEvent actionEvent) {
        String searchOption = (String) FoodSearchingOption.getValue ();
        if (searchOption.equals("By Name"))
        {
            String typing = SearchingByString.getText() ;
            List<Food> f = restaurant.SearchingFoodsbyName(typing);
            AllFoodob  = FXCollections.observableList(f) ;
            AlllFoodsInRestaurant.setItems(AllFoodob);

        }
        if (searchOption.equals("By Category"))
        {
            String typing = SearchingByString.getText() ;
            List<Food> f = restaurant.SearchingFoodsBycategory(typing);
            AllFoodob  = FXCollections.observableList(f) ;
            AlllFoodsInRestaurant.setItems(AllFoodob);

        }
        if (searchOption.equals("By Price Range"))
        {
            String typing1 =LowPrice.getText() ;
            String typing2 = HighPrice.getText();
            double  low = Double.parseDouble(typing1) ;
            double high = Double.parseDouble(typing2) ;
            List<Food> f = restaurant.SearchingFoodsByPriceRange(low,high);
            AllFoodob  = FXCollections.observableList(f) ;
            AlllFoodsInRestaurant.setItems(AllFoodob);

        }



    }
    public void ordersVisibility ()
    {

        OrderListButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        FoodListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        RestaurantInfo.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        AddingFoodButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");




        FoodSearchingOption.setVisible(false);
        AlllFoodsInRestaurant.setVisible(false);
        OrderedFoodsInTheRestaurant.setVisible(true);

        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        SearchingByString.setVisible(false);
        SearchingButton.setVisible(false);

    }


    public void FoodlistVisibility ()
    {

        FoodListButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        OrderListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        RestaurantInfo.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        AddingFoodButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");



        FoodSearchingOption.setVisible(true);
        AlllFoodsInRestaurant.setVisible(true);
        OrderedFoodsInTheRestaurant.setVisible(false);

        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        SearchingByString.setVisible(true);
        SearchingButton.setVisible(true );





    }

    public void RestaurantInfoAction(ActionEvent actionEvent) {
        try
        {
            client.ShowRestaurantInfo();

        }
        catch (Exception e )
        {
            System.out.println(e);

        }
    }

    class CustomOrderedFoodCell extends ListCell<Food> {
        private final VBox cellLayout;
        private final HBox idAndPrice ;
        private final Label nameLabel;
        private final Label idLabel;
        private final Label categoryLabel;
        private final Label priceLabel;
        private final Button AcceptOrderButton ;

        public CustomOrderedFoodCell() {
            cellLayout = new VBox();
            nameLabel = new Label();
            idAndPrice = new HBox();
            idLabel = new Label();
            categoryLabel = new Label();
            priceLabel = new Label();
            AcceptOrderButton = new Button("Accept order")  ;


            nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill:white ;");
            idLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            categoryLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            priceLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");


            idAndPrice.getChildren().addAll(idLabel,priceLabel);

            categoryLabel.setMinWidth(350);
            categoryLabel.setMaxWidth(500);
            categoryLabel.setWrapText(true);

            cellLayout.getChildren().addAll(
                    nameLabel,
                    idAndPrice,
                    new HBox(categoryLabel,AcceptOrderButton)
            );

            cellLayout.setSpacing(5);
            idAndPrice.setSpacing(300);
            //  categoryAndButton.setSpacing(240);
            cellLayout.setStyle("-fx-border-color: white ;");
            cellLayout.setStyle("-fx-background-color : black ; ");
        }

        @Override
        protected void updateItem(Food food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(" Name: " + food.getName());
                idLabel.setText(" Id: " + food.getRestaurant_Id());
                categoryLabel.setText("Categories: " + food.getCategory());
                priceLabel.setText("Price: " + food.getPrice());

                AcceptOrderButton.setOnAction(event ->
                {
                    OrderedFoodList.remove(getItem()) ;
                    OrderedFoodob = FXCollections.observableList(OrderedFoodList);
                    OrderedFoodsInTheRestaurant.setItems(OrderedFoodob);
                   // OrderedFood.add(getItem());
                   // cartcount++ ;
                   // OrderCount.setText(cartcount + "");

                });


                setGraphic(cellLayout);
            }
        }
    }


    class CustomAllFoodCell extends ListCell<Food> {
        private final VBox cellLayout;
        private final HBox idAndPrice ;
        private final Label nameLabel;
        private final Label idLabel;
        private final Label categoryLabel;
        private final Label priceLabel;


        public CustomAllFoodCell() {
            idAndPrice = new HBox();
            cellLayout = new VBox();
            nameLabel = new Label();
            idLabel = new Label();
            categoryLabel = new Label();
            priceLabel = new Label();



            nameLabel.setStyle("-fx-font-size: 24px; -fx-text-fill:white ;");
            idLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            categoryLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            priceLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");

            idAndPrice.getChildren().addAll(idLabel,priceLabel);

            cellLayout.getChildren().addAll(
                    nameLabel,

                    idAndPrice,
                    categoryLabel
            );
            idAndPrice.setSpacing(270);

            cellLayout.setSpacing(5);
            cellLayout.setStyle("-fx-border-color: white ;");
            cellLayout.setStyle("-fx-background-color:black;");
        }

        @Override
        protected void updateItem(Food food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(" Name: " + food.getName());
                idLabel.setText(" Id: " + food.getRestaurant_Id());
                categoryLabel.setText("Categories: " + food.getCategory());
                priceLabel.setText("Price: " + food.getPrice());




                setGraphic(cellLayout);
            }
        }
    }


  public void visibilityControl ()
  {
      String text = (String) FoodSearchingOption.getValue() ;
      if (text.equals("Costliest Foods"))
      {
          List<Food> f = restaurant.highpricefoods();
          AllFoodob  = FXCollections.observableList(f) ;
          AlllFoodsInRestaurant.setItems(AllFoodob);
          SearchingButton.setVisible(false);
          SearchingByString.setVisible(false);
          LowPrice.setVisible(false);
          HighPrice.setVisible(false);

      }
      if (text.equals("By Price Range"))
      {
          SearchingButton.setVisible(true);
          SearchingByString.setVisible(false);
          LowPrice.setVisible(true);
          HighPrice.setVisible(true);

      }
      if (text.equals("By Name"))
      {
          SearchingButton.setVisible(true);
          SearchingByString.setVisible(true);
          LowPrice.setVisible(false);
          HighPrice.setVisible(false);

      }
      if (text.equals("By Category"))
      {
          SearchingButton.setVisible(true);
          SearchingByString.setVisible(true);
          LowPrice.setVisible(false);
          HighPrice.setVisible(false);

      }
  }

    void setMain (RestaurantClient client , Restaurant restaurant)
    {
        this.client = client ;
        this.restaurant = restaurant ;
       new RestaurantReadThread(client.getSocketWrapper(),this);
        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
      //  FoodListButton.setVisible(false);
       // AlllFoodsInRestaurant.setVisible(false);
        OrderedFoodsInTheRestaurant.setVisible(false);
        SearchingButton.setStyle("-fx-background-color : black ; -fx-text-fill : white ");

        FoodListButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        FoodSearchingOption.getItems().addAll(
                "By Name",
                "By Category","By Price Range","Costliest Foods"
        );
        FoodSearchingOption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null ) {
                System.out.println("Previous option " + oldValue + " changed to " + newValue);
           //     show ();
                visibilityControl ();
                // function call to handle change or do it here
            }
        });
        AllFoodList = restaurant.getFoodList();
        AllFoodob = FXCollections.observableList(AllFoodList) ;
        AlllFoodsInRestaurant.setItems(AllFoodob);
        setCustomAllFoodCellFactory ();











      //  AddingFoodButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    }

    public void setCustomAllFoodCellFactory ()
    {
        AlllFoodsInRestaurant.setCellFactory(param -> {
      CustomAllFoodCell cell = new CustomAllFoodCell();
            // Add click event to the cell
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Food selectedFood = cell.getItem() ;

                    try {
                        // OrderObject orderObject = new OrderObject(selectedFood,client.getCustomerUsername());//                        client.getSocketWrapper().write (orderObject);
                        // client.showFoodsInRestaurantPage(selectedRestaurant);
                    }
                    catch ( Exception e)
                    {
                        System.out.println(e);
                    }



                    // Handle the selected restaurant, e.g., display its details
                    //  showRestaurantDetails(selectedRestaurant);
                }
            });

            return cell;
        });

    }

    public void setCustomOrderFoodCellFactory ()
    {
        OrderedFoodsInTheRestaurant.setCellFactory(param -> {
            CustomOrderedFoodCell cell = new CustomOrderedFoodCell();
            // Add click event to the cell
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Food selectedFood = cell.getItem() ;

                    try {
                        // OrderObject orderObject = new OrderObject(selectedFood,client.getCustomerUsername());//                        client.getSocketWrapper().write (orderObject);
                        // client.showFoodsInRestaurantPage(selectedRestaurant);
                    }
                    catch ( Exception e)
                    {
                        System.out.println(e);
                    }



                    // Handle the selected restaurant, e.g., display its details
                    //  showRestaurantDetails(selectedRestaurant);
                }
            });

            return cell;
        });

    }



}
