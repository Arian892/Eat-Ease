package util;

import Admin_Server.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderObject2 implements Serializable {
    private Food food ;
    private String CustomerName ;

    public OrderObject2 (Food food, String CustomerName) {
        this.food = food;
        this.CustomerName = CustomerName;
    }

    public  Food getFood ()
    {
        return this.food ;
    }
    public  String getCustomerName ()
    {
        return this.CustomerName ;
    }



}
