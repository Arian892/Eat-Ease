package Admin_Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {

    private int Id;
    private String Name;
    private double Score;
    private String Price;
    private String ZipCode;
    private String password ;

    private List<String> Categories = new ArrayList<>();
    private List<Food>foodlist = new ArrayList <> ();
    // all getters
    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public double getScore() {
        return Score;
    }

    public String getPrice() {
        return Price;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public List<String> getCategory() {
        return Categories;
    }

    public List<Food> getFoodList ()
    {
        return foodlist ; 
    }
    public String getPassword ()
    {
        return password ; 
    }
    // all getters

    public boolean check_categories(String category) {
        for (int i = 0; i < Categories.size(); i++) {
            String s = Categories.get(i);
            if (s.toLowerCase().contains(category.toLowerCase()))
                return true;
        }
        return false;
    }

    public boolean check_categoriesNotCase(String category) {
        for (int i = 0; i < Categories.size(); i++) {
            String s = Categories.get(i);
            if (s.equalsIgnoreCase(category))
                return true;
        }
        return false;
    }



    // adding food item in a restaurant 
    public boolean addFoodItemOnRestaurant (Food f)
    {
        foodlist.add(f);
        return true ;

    }

    // searching foods in a restaurant by name
  
    public List<Food> SearchingFoodsbyName (String name)
    {
        List<Food> flist = new ArrayList<> ();
        for (Food f : foodlist)
        {
            if (f.getName().toLowerCase().contains(name.toLowerCase()))
            flist.add(f);
            
        }
        return flist; 

    }


    // searching foods in a restaurant by category
     public List<Food> SearchingFoodsBycategory (String category)
    {
        List<Food> flist = new ArrayList<> ();
        for (Food f : foodlist)
        {
            if (f.getCategory().toLowerCase().contains(category.toLowerCase()))
            flist.add(f);
            
        }
        return flist; 

    }

    // searchign foods in a restaurnat by  price range
      public List<Food> SearchingFoodsByPriceRange (double low,double high)
    {
        List<Food> flist = new ArrayList<> ();
        for (Food f : foodlist)
        {
            if (f.getPrice() >= low && f.getPrice()<= high)
            flist.add (f);
            
        }
        return flist; 

    }

      public List<Food> highpricefoods()
    {
        List<Food> flist = new ArrayList<> ();
        double max = 0 ;
        for (Food f : foodlist)
        { 
            if (f.getPrice() >= max)
             max = f.getPrice();     
        }

        for (Food f : foodlist)
        {
            if (f.getPrice() == max)
            flist.add(f);
        }
        return flist; 

    }

    public int TotalFoodCount ()
    {
        return foodlist.size() ;

    }


    //

    // Constructor

    public Restaurant() {

    }

    public Restaurant(int id, String name, double score, String price, String zipCode, List<String> categories) {
        this.Id = id;
        this.Name = name;
        this.Score = score;
        this.Price = price;
        this.ZipCode = zipCode;
        Categories = categories;
        this.password = name + id ; 
    }

    public void ShowDetails() {
        System.out.println();

       // System.out.println("Restaurant Id : " + Id);
        System.out.println("Restaurant Name : " + Name);
        System.out.println("Restaurant Score : " + Score);
        System.out.println("Restaurant Price : " + Price);
        System.out.println("Restaurant Zip code : " + ZipCode);
        System.out.println("Restaurant Categories are : ");
        for (int i = 0; i < Categories.size(); i++) {
            String s = Categories.get(i);
            System.out.println((i + 1) + ". " + s);
        }
        System.out.println();

    }

}
