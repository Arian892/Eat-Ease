package Admin_Server;

import java.io.Serializable;

public class Food implements Serializable {

    private int Restaurant_Id;
    private String Category;
    private String name;
    private double Price;
    private int ordercount  = 0 ;

    // all getters


    public void increaseOrder ()
    {
        ordercount++ ;
    }

    public int getOrdercount() {
        return ordercount;
    }

    public int getRestaurant_Id() {
        return Restaurant_Id;
    }

    public String getCategory() {
        return Category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return Price;
    }
    // all getters

    

    // constructor
    public Food(int restaurantId, String category, String name, double price) {
        this.Restaurant_Id = restaurantId;
        this.Category = category;
        this.name = name;
        this.Price = price;
    }

    // show function for Food 
    public void ShowDetails() {
        System.out.println();
        System.out.println("Food Name : " + name);
     //   System.out.println("Restaurant Id : " + Restaurant_Id);
        System.out.println("Food Price : " + Price);
        System.out.println("Food Category : " + Category);
        System.out.println();

    }

}
