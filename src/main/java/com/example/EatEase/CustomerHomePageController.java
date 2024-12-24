package com.example.EatEase;

import Admin_Server.Food;
import Admin_Server.Restaurant;
import Admin_Server.Restaurantmanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.OrderObject;
import util.OrderObject2;
import util.SocketWrapper;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomePageController {
    public ListView AddToCartFoodListView;
    private int totalorder ;
    private double totalmoney ;
    public Label FoodInCart;
    public Label TotalCostFoods;
    public Label TotalFoodInCart;
    private CustomerClient client ;
    public int CartFoodCount = 0 ;
    public double Totalcost = 0.0 ;

    private List <Food>  addToCartFoods = new ArrayList<>();
    private ObservableList<Food> AddToCartFoodsob;
    private Restaurantmanager restaurantmanager;
    private Restaurant currentRestaurant ;
    private String CustomerUsername ;

    @FXML
    private ListView<Food> AllFoodsInRestaurantListView;
    private ObservableList<Food> AllFoodsInRestaurantOb ;

    @FXML
    private ListView<Food> AllFoodsListView;
    private ObservableList<Food> AllFoodsob  ;

    @FXML
    private ListView<Restaurant> AllRestaurantListView;
    private ObservableList<Restaurant> AllRestaurantob;

    @FXML
    private Button FoodListButton;

    @FXML
    private ChoiceBox FoodSearchingInaRestaurant;

    @FXML
    private ChoiceBox  FoodSearchingOption;

    @FXML
    private ChoiceBox RestaurantSearchingOption;

    @FXML
    private TextField HighPrice;

    @FXML
    private Button LogoutButton;

    @FXML
    private TextField LowPrice;

    @FXML
    private Button MyCartButton;

    @FXML
    private Button MyInfoButton;

    @FXML
    private Button OrderButton;

    @FXML
    private Button RestaurantButton;




    @FXML
    private Button SearchingButton;

    @FXML
    private TextField SearchingByString;

    @FXML
    void FoodListButtonAction(ActionEvent event) {
        FoodViewVisibility();
        List<Food> f = restaurantmanager.AllFoodList();
        AllFoodsob = FXCollections.observableList(f);
        AllFoodsListView.setItems(AllFoodsob);
        setCustomAllFoodCellFactory();

    }

    @FXML
    void LogoutAction(ActionEvent event) {
        try {
            client.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void MyCartButtonAction(ActionEvent event) {
        System.out.println(CartFoodCount);
        System.out.println(Totalcost);



        AddToCartVisibility();
        AddToCartFoodListView.setVisible(true);
        TotalFoodInCart.setText("Number of Foods in Cart : " + CartFoodCount);
        TotalCostFoods.setText("Total Cost : "+ Totalcost);


        AddToCartFoodsob = FXCollections.observableList(addToCartFoods);
        AddToCartFoodListView.setItems(AddToCartFoodsob);
        setCustomAddToCartCellFactory();


    }

    @FXML
    void MyInfoButtonAction(ActionEvent event) {
        try
        {
            client.showInfoPage();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    @FXML
    void OrderButtonAction(ActionEvent event){
        if (addToCartFoods.size() == 0) return ;

        client.totalmoney += Totalcost ;
        client.totalorder += addToCartFoods.size();
        for (Food f : addToCartFoods)
            f.ShowDetails();

        System.out.println("from customer");

        try {
            SocketWrapper socketWrapper = client.getSocketWrapper();
            for (Food f : addToCartFoods)
            {

                    OrderObject2 orderObject2 = new OrderObject2(f, client.getCustomerUsername());
                    socketWrapper.write(orderObject2);

            }



            System.out.println("givigng opders");

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        addToCartFoods.clear();
        CartFoodCount = 0 ;
        Totalcost = 0.0 ;
        FoodInCart.setText(CartFoodCount + "");
        TotalFoodInCart.setText("Number of Foods in Cart : " + CartFoodCount);
        TotalCostFoods.setText("Total Cost : "+ Totalcost);
        AddToCartFoodsob = FXCollections.observableList(addToCartFoods);
        AddToCartFoodListView.setItems(AddToCartFoodsob);

        client.showAlertForOrder();


    }

    @FXML
    void RestaurantButtonAction(ActionEvent event) {
        RestaurantViewVisibility();
        List<Restaurant> R = restaurantmanager.AllRestaurantList();
        AllRestaurantob = FXCollections.observableList(R);
        AllRestaurantListView.setItems(AllRestaurantob);
        setCustomCellFactory();

    }

    void FoodsInRestaurantButtonAction (Restaurant selectedRestaurant)
    {
        this.currentRestaurant = selectedRestaurant;
        FoodsInRestaurantVisibility();
        List<Food> f = selectedRestaurant.getFoodList();
        AllFoodsInRestaurantOb = FXCollections.observableList(f);
        AllFoodsInRestaurantListView.setItems(AllFoodsInRestaurantOb);
        setCustomAllFoodsInRestaurantCellFactory();


    }



    public void StringTextFieldVisibility ()
    {
        SearchingByString.setVisible(true);
        LowPrice.setVisible(false);
        HighPrice.setVisible(false);

    }
    public void PriceRangeTextFieldVisibility ()
    {
        LowPrice.setVisible(true);
        HighPrice.setVisible(true);
        SearchingByString.setVisible(false);


    }


    public void RestaurantSearch ()
    {
        String searchOption = (String) RestaurantSearchingOption.getValue();
        if (searchOption.equals("By Name"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Restaurant> R = restaurantmanager.SearchByName(typing) ;
            AllRestaurantob = FXCollections.observableList(R);

            AllRestaurantListView.setItems(AllRestaurantob);
        }
        if (searchOption.equals("By Category"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Restaurant> R = restaurantmanager.SearchByCatagory(typing) ;
            AllRestaurantob = FXCollections.observableList(R);
            AllRestaurantListView.setItems(AllRestaurantob);
        }
        if (searchOption.equals("By Price"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Restaurant> R = restaurantmanager.SearchByPrice(typing) ;
            AllRestaurantob = FXCollections.observableList(R);
            AllRestaurantListView.setItems(AllRestaurantob);
        }
        if (searchOption.equals("By Zip Code"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Restaurant> R = restaurantmanager.SearchByZipcode(typing) ;
            AllRestaurantob = FXCollections.observableList(R);
            AllRestaurantListView.setItems(AllRestaurantob);
        }
        if (searchOption.equals("By Score"))
        {
            PriceRangeTextFieldVisibility();
            String typing1 =LowPrice.getText() ;
            String typing2 = HighPrice.getText();
            double  low = Double.parseDouble(typing1) ;
            double high = Double.parseDouble(typing2) ;
            List<Restaurant> f = restaurantmanager.SearchByScore(low,high);
            AllRestaurantob= FXCollections.observableList(f) ;
            AllRestaurantListView.setItems(AllRestaurantob);

        }



    }


    public void FoodSearch ()
    {
        String searchOption = (String) FoodSearchingOption.getValue ();
        if (searchOption.equals("By Name"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Food> f = restaurantmanager.SearchFoodsByName(typing) ;
            AllFoodsob = FXCollections.observableList(f) ;
            AllFoodsListView.setItems(AllFoodsob);

        }
        if (searchOption.equals("By Category"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Food> f = restaurantmanager.SearchFoodsByCategory(typing) ;
            AllFoodsob = FXCollections.observableList(f) ;
            AllFoodsListView.setItems(AllFoodsob);

        }
        if (searchOption.equals("By Price Range"))
        {
            PriceRangeTextFieldVisibility();
            String typing1 =LowPrice.getText() ;
            String typing2 = HighPrice.getText();
            double  low = Double.parseDouble(typing1) ;
            double high = Double.parseDouble(typing2) ;
            List<Food> f = restaurantmanager.SearchFoodsByPriceRange(low,high);
            AllFoodsob  = FXCollections.observableList(f) ;
            AllFoodsListView.setItems(AllFoodsob);

        }



    }
    public void FoodsInaRestaurantSearch ()
    {

       String searchOption = (String) FoodSearchingInaRestaurant.getValue ();
        if (searchOption.equals("By Name"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Food> f = currentRestaurant.SearchingFoodsbyName(typing);
            AllFoodsInRestaurantOb  = FXCollections.observableList(f) ;
            AllFoodsInRestaurantListView.setItems(AllFoodsInRestaurantOb);

        }
        if (searchOption.equals("By Category"))
        {
            StringTextFieldVisibility();
            String typing = SearchingByString.getText() ;
            List<Food> f = currentRestaurant.SearchingFoodsBycategory(typing);
            AllFoodsInRestaurantOb  = FXCollections.observableList(f) ;
            AllFoodsInRestaurantListView.setItems(AllFoodsInRestaurantOb);

        }
        if (searchOption.equals("By Price Range"))
        {
            PriceRangeTextFieldVisibility();
            String typing1 =LowPrice.getText() ;
            String typing2 = HighPrice.getText();
            double  low = Double.parseDouble(typing1) ;
            double high = Double.parseDouble(typing2) ;
            List<Food> f = currentRestaurant.SearchingFoodsByPriceRange(low,high);
            AllFoodsInRestaurantOb  = FXCollections.observableList(f) ;
            AllFoodsInRestaurantListView.setItems(AllFoodsInRestaurantOb);

        }


    }

    public void choiceVisibility (String typing)
    {
        if (typing.equals("By Price Range") || typing.equals("By Score"))
        {
            PriceRangeTextFieldVisibility();

        }
        if (typing.equals("By Name"))
        {
            StringTextFieldVisibility();
        }
        if (typing.equals("By Category"))
        {
            StringTextFieldVisibility();
        }
        if (typing.equals("By Zip Code"))
        {
            StringTextFieldVisibility();
        }
        if (typing.equals("Costliest Foods"))
        {
            List<Food> f = currentRestaurant.highpricefoods();
            AllFoodsInRestaurantOb  = FXCollections.observableList(f) ;
            AllFoodsInRestaurantListView.setItems(AllFoodsInRestaurantOb);
            FoodsInRestaurantVisibility();

        }
    }

    @FXML
    void SearchingActionButton(ActionEvent event) {


        if (AllRestaurantListView.isVisible())
        {
            RestaurantSearch();


        }
       if (AllFoodsListView.isVisible())
        {
            FoodSearch();

        }
       else if (AllFoodsInRestaurantListView.isVisible())
        {
            FoodsInaRestaurantSearch();

        }

    }
    public void RestaurantViewVisibility ()
    {


        RestaurantButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        FoodListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyInfoButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyCartButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");



        AllRestaurantListView.setVisible(true);


        AllFoodsListView.setVisible(false);
        AllFoodsInRestaurantListView.setVisible(false);
        AddToCartFoodListView.setVisible(false);


        OrderButton.setVisible(false);
        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        TotalFoodInCart.setVisible(false);
        TotalCostFoods.setVisible(false);
        SearchingByString.setVisible(true);
        SearchingButton.setVisible(true);



        RestaurantSearchingOption.setVisible(true);
        FoodSearchingOption.setVisible(false);
        FoodSearchingInaRestaurant.setVisible(false);



    }
    public void AddToCartVisibility ()
    {


        MyCartButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        FoodListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyInfoButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        RestaurantButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        AddToCartFoodListView.setVisible(true);

        AllRestaurantListView.setVisible(false);
        AllFoodsListView.setVisible(false);
        AllFoodsInRestaurantListView.setVisible(false);

        OrderButton.setVisible(true);
        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        TotalFoodInCart.setVisible(true);
        TotalCostFoods.setVisible(true);
        SearchingByString.setVisible(false);
        SearchingButton.setVisible(false);







        RestaurantSearchingOption.setVisible(false);
        FoodSearchingOption.setVisible(false);
        FoodSearchingInaRestaurant.setVisible(false);



    }
    public void FoodViewVisibility ()
    {



        FoodListButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        RestaurantButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyInfoButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyCartButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");



        AllFoodsListView.setVisible(true);



        AllFoodsInRestaurantListView.setVisible(false);
        AllRestaurantListView.setVisible(false);
        AddToCartFoodListView.setVisible(false);

        OrderButton.setVisible(false);
        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        TotalFoodInCart.setVisible(false);
        TotalCostFoods.setVisible(false);
        SearchingByString.setVisible(true);
        SearchingButton.setVisible(true);




        RestaurantSearchingOption.setVisible(false);
        FoodSearchingOption.setVisible(true);
        FoodSearchingInaRestaurant.setVisible(false);



    }

    public void FoodsInRestaurantVisibility ()
    {


        RestaurantButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        FoodListButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        LogoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyInfoButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        MyCartButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");





        AllFoodsInRestaurantListView.setVisible(true);



        AllRestaurantListView.setVisible(false);
        AllFoodsListView.setVisible(false);
        AddToCartFoodListView.setVisible(false);
        SearchingByString.setVisible(true);
        SearchingButton.setVisible(true);



        OrderButton.setVisible(false);
        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        TotalFoodInCart.setVisible(false);
        TotalCostFoods.setVisible(false);



        RestaurantSearchingOption.setVisible(false);
        FoodSearchingOption.setVisible(false);
        FoodSearchingInaRestaurant.setVisible(true);

    }



    void setMain (CustomerClient client, Restaurantmanager restaurantmanager)
    {
        this.client = client ;
        this.restaurantmanager = restaurantmanager;


        AllRestaurantListView.setVisible(true);
        AllFoodsListView.setVisible(false);
        AllFoodsInRestaurantListView.setVisible(false);
        AddToCartFoodListView.setVisible(false);

        RestaurantSearchingOption.setStyle("-fx-background-color : black ; -fx-text-fill : white");




        SearchingButton.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
        OrderButton.setStyle("-fx-background-color : black ; -fx-text-fill : white ");
        TotalCostFoods.setStyle("-fx-background-color : black ; -fx-text-fill : white ; -fx-alignment : center ;");
        TotalFoodInCart.setStyle("-fx-background-color : black ; -fx-text-fill : white ; -fx-alignment : center ; ");
        FoodInCart.setStyle("-fx-background-color : black ; -fx-text-fill : white ; -fx-alignment : center ; ");




        LowPrice.setVisible(false);
        HighPrice.setVisible(false);
        OrderButton.setVisible(false);
        TotalCostFoods.setVisible(false);
        TotalFoodInCart.setVisible(false);




        TotalFoodInCart.setText("Number of Foods in Cart : " + CartFoodCount);
        TotalCostFoods.setText("Total Cost : "+ Totalcost);
        FoodInCart.setText(CartFoodCount + "");


        RestaurantViewVisibility();

        List<Restaurant> R = restaurantmanager.AllRestaurantList();
        AllRestaurantob = FXCollections.observableList(R);
        AllRestaurantListView.setItems(AllRestaurantob);
        setCustomCellFactory();





  //// choice box of restaurant
        RestaurantSearchingOption.getItems().addAll(
                "By Name",
                "By Score",
                "By Category","By Price","By Zip Code"

        );
        RestaurantSearchingOption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null ) {
                System.out.println("Previous option " + oldValue + " changed to " + newValue);
               // show ();
                choiceVisibility((String)newValue);
              //  RestaurantSearchingOption.setStyle("-fx-background-color- : black ; -fx-text-fill : while;");

            //    RestaurantSearchingOption.setStyle("-fx-text-fill : black ; ");
               // newValue
               // RestaurantSearchingOption.setStyle("-fx-background-color : black ;" + " -fx-text-fill : while;");
                // function call to handle change or do it here
            }
        });

     ;



        RestaurantSearchingOption.setStyle("-fx-background-color : black ; -fx-text-fill : white;");

        /// choice box of foods in restaurant
        FoodSearchingInaRestaurant.getItems().addAll(
                "By Name",
                "By Category","By Price Range","Costliest Foods"
        );
        FoodSearchingInaRestaurant.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null ) {
                System.out.println("Previous option " + oldValue + " changed to " + newValue);
                //     show ();
                choiceVisibility((String)newValue);
              //  visibilityControl ();
                // function call to handle change or do it here
            }
        });



        // choice box of foods
        FoodSearchingOption.getItems().addAll(
                "By Name",
                "By Category","By Price Range"
        );
        FoodSearchingOption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null ) {
                System.out.println("Previous option " + oldValue + " changed to " + newValue);
                //     show ();
                choiceVisibility((String)newValue);
                //  visibilityControl ();
                // function call to handle change or do it here
            }
        });



    }




    /// for restaurant list viewwww
    class CustomRestaurantCell extends ListCell<Restaurant> {
        private final VBox cellLayout;
        private  final HBox idAndPrice ;
        private final Label nameLabel;
        private final Label idLabel;
        private final Label categoryLabel;
        private final Label priceLabel;
        private final Label zipcodeLabel;

        public CustomRestaurantCell() {
            cellLayout = new VBox();
            nameLabel = new Label();
            idLabel = new Label();
            categoryLabel = new Label();
            priceLabel = new Label();
            zipcodeLabel = new Label();
            idAndPrice = new HBox ();

            nameLabel.setStyle("-fx-font-size: 24px; -fx-text-fill:white ;");
            idLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            categoryLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            priceLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            zipcodeLabel.setStyle("-fx-font-size: 15px ;-fx-text-fill:white ;");

            idAndPrice.getChildren().addAll(idLabel,priceLabel);

            cellLayout.getChildren().addAll(
                    nameLabel,

                    idAndPrice,
                    categoryLabel,
                    zipcodeLabel
            );
            idAndPrice.setSpacing(270);

            cellLayout.setSpacing(5);
            cellLayout.setStyle("-fx-border-color: white ;");
            cellLayout.setStyle("-fx-background-color:black;");
        }

        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);

            if (empty || restaurant == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(" Name: " + restaurant.getName());
                idLabel.setText(" Id: " + restaurant.getId());
                categoryLabel.setText("Categories: " + restaurant.getCategory());
                priceLabel.setText("Price: " + restaurant.getPrice());
                zipcodeLabel.setText("Zipcode: " + restaurant.getZipCode());

                setGraphic(cellLayout);
            }
        }
    }
    public void setCustomCellFactory()  {
        // RestaurantListView.setCellFactory(param -> new CustomRestaurantCell());
        AllRestaurantListView.setCellFactory(param -> {
            CustomRestaurantCell cell = new CustomRestaurantCell();

            // Add click event to the cell
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Restaurant selectedRestaurant = cell.getItem();

                    try {
                        FoodsInRestaurantButtonAction(selectedRestaurant);

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







    /// for  All foods list view
    class CustomAllFoodCell extends ListCell<Food> {
        private final VBox cellLayout;
        private final HBox idAndPrice ;
        private final Label nameLabel;
        private final Label idLabel;
        private final Label categoryLabel;
        private final Label priceLabel;
        private final Button addToCartButton ;

        public CustomAllFoodCell() {
            cellLayout = new VBox();
            nameLabel = new Label();
            idAndPrice = new HBox();
            idLabel = new Label();
            categoryLabel = new Label();
            priceLabel = new Label();
            addToCartButton = new Button("Add to Cart")  ;


            nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill:white ;");
            idLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            categoryLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            priceLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");


            idAndPrice.getChildren().addAll(idLabel,priceLabel);

            categoryLabel.setMinWidth(380);
            categoryLabel.setMaxWidth(500);
            categoryLabel.setWrapText(true);

            cellLayout.getChildren().addAll(
                    nameLabel,
                    idAndPrice,
                    new HBox(categoryLabel,addToCartButton)
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

                addToCartButton.setOnAction(event ->
                {
                    addToCartFoods.add(getItem());
                    CartFoodCount++;
                    Totalcost += getItem().getPrice();
                    TotalFoodInCart.setText("Number of Foods in Cart : " + CartFoodCount);
                    TotalCostFoods.setText("Total Cost : "+ Totalcost);
                    FoodInCart.setText(CartFoodCount + "");
                   // OrderedFood.add(getItem());
                   // cartcount++ ;
                   // OrderCount.setText(cartcount + "");

                });


                setGraphic(cellLayout);
            }
        }
    }


    public void setCustomAllFoodCellFactory()  {



             AllFoodsListView.setCellFactory(param -> {
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




    /// food in restaurant list view

    class CustomAllFoodsInRestaurantCell extends ListCell<Food> {
        private final VBox cellLayout;
        private final HBox idAndPrice ;
        private final Label nameLabel;
        private final Label idLabel;
        private final Label categoryLabel;
        private final Label priceLabel;
        private final Button addToCartButton ;

        public CustomAllFoodsInRestaurantCell() {
            cellLayout = new VBox();
            nameLabel = new Label();
            idAndPrice = new HBox();
            idLabel = new Label();
            categoryLabel = new Label();
            priceLabel = new Label();
            addToCartButton = new Button("Add to Cart")  ;


            nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill:white ;");
            idLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            categoryLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            priceLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");


            idAndPrice.getChildren().addAll(idLabel,priceLabel);

            categoryLabel.setMinWidth(380);
            categoryLabel.setMaxWidth(500);
            categoryLabel.setWrapText(true);

            cellLayout.getChildren().addAll(
                    nameLabel,
                    idAndPrice,
                    new HBox(categoryLabel,addToCartButton)
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

                addToCartButton.setOnAction(event ->
                {
                    addToCartFoods.add(getItem());
                    CartFoodCount++;
                    Totalcost += getItem().getPrice();
                    TotalFoodInCart.setText("Number of Foods in Cart : " + CartFoodCount);
                    TotalCostFoods.setText("Total Cost : "+ Totalcost);
                    FoodInCart.setText(CartFoodCount + "");


                    // OrderedFood.add(getItem());
                    // cartcount++ ;
                    // OrderCount.setText(cartcount + "");

                });


                setGraphic(cellLayout);
            }
        }
    }

    public void setCustomAllFoodsInRestaurantCellFactory()  {



        AllFoodsInRestaurantListView.setCellFactory(param -> {
            CustomAllFoodsInRestaurantCell cell = new CustomAllFoodsInRestaurantCell();


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

    class CustomAddToCartFoodCell extends ListCell<Food> {
        private final VBox cellLayout;
        private final HBox idAndPrice ;
        private final Label nameLabel;
        private final Label idLabel;
        private final Label categoryLabel;
        private final Label priceLabel;

        private final Button RemoveFromCartButton ;

        public CustomAddToCartFoodCell() {
            cellLayout = new VBox();
            nameLabel = new Label();
            idAndPrice = new HBox();
            idLabel = new Label();
            categoryLabel = new Label();
            priceLabel = new Label();
            RemoveFromCartButton = new Button("Remove from cart")  ;


            nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill:white ;");
            idLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            categoryLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");
            priceLabel.setStyle("-fx-font-size: 15px;-fx-text-fill:white ;");


            idAndPrice.getChildren().addAll(idLabel,priceLabel);

            categoryLabel.setMinWidth(320);
            categoryLabel.setMaxWidth(500);
            categoryLabel.setWrapText(true);

            cellLayout.getChildren().addAll(
                    nameLabel,
                    idAndPrice,
                    new HBox(categoryLabel,RemoveFromCartButton)
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

                RemoveFromCartButton.setOnAction(event ->
                {
                    CartFoodCount--;
                    Totalcost -= getItem().getPrice();
                    addToCartFoods.remove(getItem());

                    AddToCartFoodsob = FXCollections.observableList(addToCartFoods);
                    AddToCartFoodListView.setItems(AddToCartFoodsob);
                    TotalFoodInCart.setText("Number of Foods in Cart : " + CartFoodCount);
                    TotalCostFoods.setText("Total Cost : "+ Totalcost);
                    FoodInCart.setText(CartFoodCount + "");

                });


                setGraphic(cellLayout);
            }
        }
    }




    public void setCustomAddToCartCellFactory()  {
        // RestaurantListView.setCellFactory(param -> new CustomRestaurantCell());
        AddToCartFoodListView.setCellFactory(param ->
        {
            CustomAddToCartFoodCell cell = new CustomAddToCartFoodCell();


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
