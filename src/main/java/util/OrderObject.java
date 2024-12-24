package util;

import Admin_Server.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderObject implements Serializable {
    private List<Food> food = new ArrayList<>();
    private String CustomerName ;

    public OrderObject (List <Food> food, String CustomerName) {
        this.food = food;
        this.CustomerName = CustomerName;
    }

    public  List<Food> getFoodList ()
    {
        return this.food ;
    }
    public  String getCustomerName ()
    {
        return this.CustomerName ;
    }



}
