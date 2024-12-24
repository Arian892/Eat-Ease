package Admin_Server;
import util.OrderObject;
import util.OrderObject2;
import util.SocketWrapper;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.List;

public class ServerReadThread implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    private Restaurantmanager restaurantmanager ;
    public HashMap<String, SocketWrapper> clientMap;

    public ServerReadThread(HashMap<String, SocketWrapper> map, SocketWrapper socketWrapper,Restaurantmanager restaurantmanager)  {
        this.clientMap = map;
        this.socketWrapper = socketWrapper;
        this.restaurantmanager = restaurantmanager ;
        this.thr = new Thread(this);
        thr.start();
    }
    @Override
    public void run  ()
    {
        try {
            while (true) {

                    OrderObject2 o = (OrderObject2) socketWrapper.read();
                     System.out.println(o);
                    System.out.println("gor order from " + o.getCustomerName());
                    o.getFood().ShowDetails();
                            String s = restaurantmanager.getUsernameOfRestaurant(o.getFood().getRestaurant_Id());
                System.out.println(s);
                            SocketWrapper nu = clientMap.get(s);
                            OrderObject2 ort = new OrderObject2(o.getFood(), o.getCustomerName());
                            nu.write(ort);




//                    for (Food f : flist) {
//                        String s = restaurantmanager.getUsernameOfRestaurant(f.getRestaurant_Id());
//                        socketWrapper = clientMap.get(s);
//                        socketWrapper.write(o);
//
//
//                    }

                }




//                System.out.println(o.getCustomerName());
//                System.out.println(o.getFood().getName());
//                System.out.println(o.getFood().getCategory());
//                System.out.println(o.getFood().getRestaurant_Id());

//                if (o instanceof Message) {
//                    Message obj = (Message) o;
//                    String to = obj.getTo();
//                    SocketWrapper nu = clientMap.get(to);
//                    if (nu != null) {
//                        nu.write(obj);
//                    }
//                }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
