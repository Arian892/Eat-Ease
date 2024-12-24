package Admin_Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import util.SocketWrapper;
 



public class AdminServer {
    private static final String INPUT_FILE_NAME1 = "src\\main\\java\\Admin_Server\\restaurant.txt";
    private static final String INPUT_FILE_NAME2 = "src\\main\\java\\Admin_Server\\menu.txt";
    private ServerSocket serverSocket;
    public HashMap<String, SocketWrapper> clientMap;
    private Restaurantmanager restaurantmanager  ; 


    AdminServer (Restaurantmanager restaurantmanager)
    {
        this.restaurantmanager = restaurantmanager ;
        clientMap = new HashMap<>();

        try 
        {
            serverSocket = new ServerSocket (54265);
           
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                serve (clientSocket);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public void serve (Socket clientSocket) throws Exception{
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket) ;
        String username = (String) socketWrapper.read ();
        System.out.println("got the username " + username);

         String[]arr = username.split(",") ; 
         if (arr[0].equals("c"))
         {
            System.out.println("Recieved customer " + arr[1] );
            clientMap.put(username, socketWrapper) ;
            subClientServe (username,socketWrapper );

         }
         else if (arr[0].equals("r"))
         {
            System.out.println("proper loop");
            //System.out.println("Received Restaurant " + arr[1]);
            //clientMap.put(username, socketWrapper) ;

            
            while (true)
            {
               
               if (isValid(username))
              
               {
                  socketWrapper.write (isValid(username)) ;
                 break ;
               }
               else
               {
                  socketWrapper.write (isValid(username)) ;
                  username = (String)socketWrapper.read ();

               } 

              // socketWrapper.write (isValid(username)) ;
            }
            clientMap.put(username, socketWrapper) ;
           subRestaurantServe (username,socketWrapper);


         }
        
        /// new ServerWritethread (clientMap , username,restaurantmanager);
       // new ServerReadthread() ;



    }
    public boolean isValid (String username )
    {
         String[]arr = username.split(",") ;
        List<Restaurant> list = restaurantmanager.AllRestaurantList(); 
        for (Restaurant r : list)
        {
            if (r.getName().equals(arr[1]))
            {
                if (r.getPassword().equals(arr[2]))
                {
                    return true ; 
                }
                return false; 
            }

            
        }
        return false ; 

    }
  



    public void subRestaurantServe (String username,SocketWrapper socketWrapper) throws Exception
    {
        String[]arr = username.split(",") ;
         socketWrapper = clientMap.get (username);
         List<Restaurant> list = restaurantmanager.AllRestaurantList();
         for (Restaurant r : list)
         {
            if (r.getName().equals(arr[1]))
            {
                socketWrapper.write(r);
                break ; 
            }
         }
         
    }

    public void subClientServe (String username, SocketWrapper socketWrapper) throws Exception
    {
        socketWrapper = clientMap.get(username);
        socketWrapper.write (restaurantmanager);
        System.out.println("object sent to the customer client");
        new ServerReadThread(clientMap,socketWrapper,restaurantmanager) ;
    }

    public static void main(String[] args) throws Exception {
        Restaurantmanager restaurantmanager = new Restaurantmanager();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME1));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

            String[] array = line.split(",", -1);

            int rid = Integer.parseInt(array[0]);
            String rname = array[1];
            double rScore = Double.parseDouble(array[2]);
            String rprice = array[3];
            String rZipcode = array[4];

            List<String> categories = new ArrayList<>();

            categories.add(array[5]);

            if (array.length >= 7 && !(array[6].equals("")))
                categories.add(array[6]);
            if (array.length == 8 && !(array[7].equals("")))
                categories.add(array[7]);

            Restaurant restaurant = new Restaurant(rid, rname, rScore, rprice, rZipcode, categories);
            restaurantmanager.addRestaurant(restaurant);

        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(INPUT_FILE_NAME2));
        while (true) {
            String line = br2.readLine();
            if (line == null)
                break;
            // System.out.println(line);
            String[] array = line.split(",", -1);
            // System.out.println(array[3]);

            int fid = Integer.parseInt(array[0]);
            String fcategory = array[1];
            String fname = array[2];
            Double fprice = Double.parseDouble(array[3]);

            Food f = new Food(fid, fcategory, fname, fprice);
            restaurantmanager.addFood(f);

        }
        br2.close();

        new AdminServer(restaurantmanager) ;




        
    }



    
}
