package Restaurant_Client;


import javafx.application.Platform;
import util.OrderObject2;
import util.SocketWrapper;

import java.io.IOException;

public class RestaurantReadThread implements Runnable{
    private  Thread thr  ;
    private HomePageController controller ;
    private RestaurantClient client ;
    private SocketWrapper socketWrapper ;
    private OrderObject2 orderObject2;

    public RestaurantReadThread (SocketWrapper socketWrapper,HomePageController controller )
    {
        this.controller = controller;

        this.socketWrapper = socketWrapper ;
        this.thr = new Thread (this);
        thr.start ();
    }

    public void run () {
        try {
            while (true) {
                orderObject2 = (OrderObject2) socketWrapper.read();
                System.out.println("Got order ");
                System.out.println(orderObject2.getCustomerName());
                System.out.println(orderObject2.getFood().getName());


                controller.getNotification(orderObject2);


            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }






}
