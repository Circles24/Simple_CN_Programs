import java.net.*;
import java.io.*;

public class Node implements Runnable{

    public ServerSocket sSkt;
    public Socket skt;
    int choice;

    public Node(String args[])throws Exception
    {
        System.out.println("@Node.Const");

        choice = Integer.parseInt(args[0]);

        if(choice == 0){

            sSkt = new ServerSocket(Integer.parseInt(args[1]));
        }

        else if(choice == 1){

            skt = new Socket(InetAddress.getByName(args[1]),Integer.parseInt(args[2]));

        }

        else throw new Exception("wrong choice");

        new Thread(this).start();
    }

    public void run(){

        System.out.println("@rNode.run");

        try{

            if(choice == 0){

                skt = sSkt.accept();
            }

            new ChatHandler(skt);

        }


        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Node.run :: "+ex.getMessage());
        }

            
    }
    public static void main(String args[]){

        System.out.println("@main");

        try{

            new Node(args);
        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java Node 0 <port> // accept conn");
            System.out.println("Usage :: java Node 1 <ip> <port> // make conn");
            
        } 

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println(ex.getMessage());

        }
    }

}