import java.io.*;
import java.net.*;

public class FileServer implements Runnable
{

    ServerSocket  sSkt;

    public void run(){

        System.out.println("@FileServer.run");

        try{

            while(true){

                new FileClientManager(sSkt.accept());
            }
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@FileServer.run :: "+ex.getMessage());
        }
    }

    public FileServer(int port) throws Exception
    {

        System.out.println("@FileServer.FileServer");

        sSkt = new ServerSocket(port);

        new Thread(this).start();
    }

    public static void main(String args[]){

        System.out.println("@FileServer.main");

        try{

            new FileServer(Integer.parseInt(args[0]));
        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java FileServer <portNo>");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@FileServer.main :: "+ex.getMessage());
        }
    }
}