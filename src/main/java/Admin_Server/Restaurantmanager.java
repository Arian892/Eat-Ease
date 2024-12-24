package Admin_Server;

import java.io.Serializable;
import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

public class Restaurantmanager implements Serializable { 
    private List<Restaurant> RestaurantList;
    private List<Food> FoodList;
    private List<String> CategoryList;
    

    Restaurantmanager() {
    
        RestaurantList = new ArrayList<>();
        FoodList = new ArrayList<>();
        CategoryList = new ArrayList<>();
    }

    // getters

    public List<Restaurant> AllRestaurantList() {
        return RestaurantList;
    }

    public List<Food> AllFoodList() {
        return FoodList;
    }

    // getters

    public boolean addRestaurant(Restaurant restaurant) {
        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.getName().equalsIgnoreCase(restaurant.getName()))
                return false; // false if restaurant already added
        }
        RestaurantList.add(restaurant);
        List<String> C = restaurant.getCategory();
        for (int i = 0; i < C.size(); i++) {
            String cat = C.get(i);
            AddCategory(cat);
        }

        return true; // true if added successfully
    }

    public boolean AddCategory(String category) {
        for (int i = 0; i < CategoryList.size(); i++) {
            String s = CategoryList.get(i);
            if (s.equalsIgnoreCase(category)) {
                return false; // category already added

            }

        }
        CategoryList.add(category);
        return true; /// true if added successfully

    }

    public boolean addFood(Food food) {
        for (int i = 0; i < FoodList.size(); i++) {
            Food f = FoodList.get(i);
            if (f.getName().equalsIgnoreCase(food.getName()) && f.getRestaurant_Id() == food.getRestaurant_Id()
                    && f.getCategory().equals(food.getCategory()))
                return false; // false if food item not addded ;

        }

        for (Restaurant r : RestaurantList)
        {
            if (r.getId() == food.getRestaurant_Id())
            {
                FoodList.add(food);
                r.addFoodItemOnRestaurant(food);
                return true ;

            }
        }

        
        return false; // true if added successfully

    }

    // checking for restaurant existence

    public boolean IsRestaurantExist(String rname) {
        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.getName().equalsIgnoreCase(rname))
                return true;

        }
        return false;
    }

    // search by name
    public List<Restaurant> SearchByName(String name) {
        List<Restaurant> L = new ArrayList<>();
        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.getName().toLowerCase().contains(name.toLowerCase()))

            {
                L.add(r);
                // break ;
            }
        }
        return L;
    }

    // search by score

    public List<Restaurant> SearchByScore(double low, double high) {
        
        List<Restaurant> L = new ArrayList<>();
        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.getScore() >= low && r.getScore() <= high) {
                L.add(r);
                

            }
        }

        return L;
    }

    // search by category
    public List<Restaurant> SearchByCatagory(String catagory) {
        List<Restaurant> L = new ArrayList<>();

        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.check_categories(catagory)) {
                L.add(r);
                
            }

        }
        return L;

    }

    public  List<Restaurant> SearchByCatagorynot(String catagory) {
        List<Restaurant> L = new ArrayList<>();

        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.check_categoriesNotCase(catagory)) {
                L.add(r);
                
            }

        }
        return L;

    }

    // search by price
    public List<Restaurant> SearchByPrice(String price) {
        List<Restaurant> L = new ArrayList<>();

        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.getPrice().equals(price)) {
                L.add(r);
            }

        }

        return L;

    }

    // search by zipcode

    public List<Restaurant> SearchByZipcode(String zipcode) {
        
        List<Restaurant> L = new ArrayList<>();

        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant r = RestaurantList.get(i);
            if (r.getZipCode().equals(zipcode)) {
                L.add(r);
                
            }

        }

        return L;

    }

    // category wise Restaurant show

    public void CategoryWiseRestaurant() {
        System.out.println();
        for (int i = 0; i < CategoryList.size(); i++) {
            System.out.print(CategoryList.get(i) + " : ");
            List<Restaurant> L = SearchByCatagorynot(CategoryList.get(i));
            for (int j = 0; j < L.size(); j++) {
                System.out.print(L.get(j).getName());
                if (j != L.size() - 1)
                    System.out.print(",");

            }
            System.out.println();
        }
        System.out.println();

    }

    // seacrching show

    public boolean ShowDetailsofRestaurants(List<Restaurant> L) {
        if (L.isEmpty())
            return false;
        for (int i = 0; i < L.size(); i++) {
            Restaurant r = L.get(i);
            r.ShowDetails();

        }
        return true;
    }

    // functions for foood //


    // food searching by name
    public  List<Food> SearchFoodsByName(String name) {
        List<Food> L = new ArrayList<>();
        for (int i = 0; i < FoodList.size(); i++) {
            Food f = FoodList.get(i);
            if (f.getName().toLowerCase().contains(name.toLowerCase())) {
                L.add(f);
            }
        }
        return L;
    }

    // food searching by name and restaurant name
    public List<Food> SearchFoodsByNameInRestaurant(String fname, String rname) {
        List<Restaurant> R = findingRestaurantbyname(rname);
        List<Food> L = new ArrayList<>();

        if (R.isEmpty())
            return L;
        Restaurant restaurant = R.get(0);
        L = restaurant.SearchingFoodsbyName(fname) ;

        // for (int i = 0; i < FoodList.size(); i++) {
        //     Food f = FoodList.get(i);
        //     if (f.getRestaurant_Id() == restaurant.getId() && f.getName().toLowerCase().contains(fname.toLowerCase())) {
        //         L.add(f);
        //     }
        // }
        return L;

    }

    // food searching by category
    public  List<Food> SearchFoodsByCategory(String fcategory) {
        List<Food> L = new ArrayList<>();
        for (int i = 0; i < FoodList.size(); i++) {
            Food f = FoodList.get(i);
            if (f.getCategory().toLowerCase().contains(fcategory.toLowerCase())) {
                L.add(f);
            }
        }
        return L;

    }

    // food searching by category in a restaurant
    public List<Food> SearchFoodsByCategoryInRestaurant(String fCategory, String rname) {
        List<Restaurant> R = findingRestaurantbyname(rname);
        List<Food> L = new ArrayList<>();
        if (R.isEmpty())
            return L;
        Restaurant restaurant = R.get(0);
        L = restaurant.SearchingFoodsBycategory(fCategory);

        // for (int i = 0; i < FoodList.size(); i++) {
        //     Food f = FoodList.get(i);
        //     if (f.getRestaurant_Id() == restaurant.getId()
        //             && f.getCategory().toLowerCase().contains(fCategory.toLowerCase())) {
        //         L.add(f);
        //     }
        // }
        return L;

    }

    // food searching by price range
    public List<Food> SearchFoodsByPriceRange(double low, double high) {
        List<Food> L = new ArrayList<>();
        for (int i = 0; i < FoodList.size(); i++) {
            Food f = FoodList.get(i);
            if (f.getPrice() >= low && f.getPrice() <= high) {
                L.add(f);
            }
        }
        return L;

    }

    // food searching by price range in a restaurant
    public List<Food> SearchFoodsByPriceRangeInRestaurant(double low, double high, String rname) {
        List<Food> L = new ArrayList<>();
        List<Restaurant> Rlist = findingRestaurantbyname(rname);
        if (Rlist.isEmpty())
            return L;
        Restaurant R = Rlist.get(0);
        L = R.SearchingFoodsByPriceRange(low, high);
        // for (int i = 0; i < list.size(); i++) {
        //     Food f = list.get(i);
        //     if (f.getRestaurant_Id() == R.getId())
        //         L.add(f);
        // }
        return L;

    }

    // expensive Foods
    public List<Food> CostliestFoodItems(String rname) {
       List <Restaurant> rlist =  findingRestaurantbyname(rname) ; 
        //
        Restaurant R = rlist.get(0); 
        List<Food> L = R.highpricefoods();
        return L ;


        //List<Food> flist = FoodsInRestaurant(R);
       // return maxpriceFoods(flist);
    }

    // food count in each restaurant
    public  void TotalRestaurantsAndFoodItems() {
        for (int i = 0; i < RestaurantList.size(); i++) {
            Restaurant R = RestaurantList.get(i);
            System.out.println(R.getName() + ": " + R.TotalFoodCount());
        }
        System.out.println();

    }

    // seaching for foods in a restaurant
    public List<Food> FoodsInRestaurant(Restaurant r) {
        List<Food> L = new ArrayList<>();
        for (int i = 0; i < FoodList.size(); i++) {
            Food f = FoodList.get(i);
            if (f.getRestaurant_Id() == r.getId())
                L.add(f);
        }
        return L;
    }

    // maxprice foodlist
    public List<Food> maxpriceFoods(List<Food> flist) {
        List<Food> L = new ArrayList<>();
        double max = 0;
        for (int i = 0; i < flist.size(); i++) {
            Food f = flist.get(i);
            if (f.getPrice() >= max)
                max = f.getPrice();
        }

        for (int i = 0; i < flist.size(); i++) {
            Food f = flist.get(i);
            if (f.getPrice() == max) {
                L.add(f);
            }

        }
        return L;
    }

    // food count
    public int FoodCount(Restaurant r) {
        int count = 0;
        for (int i = 0; i < FoodList.size(); i++) {
            Food f = FoodList.get(i);
            if (f.getRestaurant_Id() == r.getId()) {
                count++;
            }
        }
        return count;

    }

    // show food details
    public boolean ShowDetailsofFoods(List<Food> L) {
        if (L.isEmpty())
            return false;
        for (int i = 0; i < L.size(); i++) {
            Food r = L.get(i);
            r.ShowDetails();

        }
        return true;
    }


    

    // special needed function

    public String getUsernameOfRestaurant (int id)
    {
        String username="";
        for (Restaurant r : RestaurantList)
        {
            if (r.getId() == id)
            {
                username = "r,"+r.getName()+","+ r.getPassword();
                break ;
            }
        }
        return username;

    }

    public int getIdOfRestaurantName(String  name )
    {
        int id =0  ; 
        for (int i = 0 ; i < RestaurantList.size (); i++)
        {
            Restaurant R = RestaurantList.get (i);
            if (R.getName().equalsIgnoreCase(name))
            {
                id = R.getId() ; 
                break ; 
            }
        }
        return id ; 
    }
    public boolean IsIdExist (int id )
    {
        for (int i = 0 ; i< RestaurantList.size() ; i++)
        {
            Restaurant R = RestaurantList.get(i);
            if (R.getId() == id)
            {
                return true ; 
            }
        }
        return false ; 

    }


    public boolean IsCategoryExist (String category) {
        for (int i = 0; i < CategoryList.size(); i++) {
            String s = CategoryList.get(i);
            if (s.equalsIgnoreCase(category))
                return true;
        }
        return false;
    }

     public List <Restaurant> findingRestaurantbyname (String name)
    {
        List <Restaurant> L = new  ArrayList<>() ;
        for (int i =0 ;i< RestaurantList.size() ; i++)
        {
            Restaurant r = RestaurantList.get(i);
            if (r.getName().equalsIgnoreCase(name))

            {
                L.add (r); 
                break ;
            }
             

        }
        return L ; 
    }

}
