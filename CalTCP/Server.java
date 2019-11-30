import java.net.*;

public class Server implements Runnable
{

    ServerSocket sSkt;

    public void run(){

        System.out.println("@Server.run");

        try{

            new ClientManager(sSkt.accept());

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Server.run :: "+ex.getMessage());
        }
    }

    public Server(int port)throws Exception
    {

        System.out.println("@Server.Const");

        sSkt = new ServerSocket(port);

        new Thread(this).start();
    }

    public static void main(String args[]){

        System.out.println("@Server.main");

        try{

            new Server(Integer.parseInt(args[0]));

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java Server <portNo>");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Server.main :: "+ex.getMessage());
        }
    }
}