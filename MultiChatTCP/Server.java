import java.io.*;
import java.net.*;

public class Server implements Runnable
{

    ServerSocket sSkt;
    ChatProcessor cp;

    public void run(){

        System.out.println("@Server.run");

        try{

            Socket skt;

            while(true){

                skt = sSkt.accept();
                cp.add(skt.getInputStream(),skt.getOutputStream());            
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Server.run :: "+ex.getMessage());

        }
        
    }

    public Server(int portNo)throws Exception
    {

        sSkt = new ServerSocket(portNo);
        cp = new ChatProcessor();

        new Thread(this).start();
    }

    public static void main(String args[]){

        System.out.println("@Server.main");

        try{

            new Server(Integer.parseInt(args[0]));

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usagae :: java Server <portNo> ");

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Server.main :: "+ex.getMessage());
        }
    }
}