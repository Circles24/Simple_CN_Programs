import java.net.*;
import java.io.*;

public class ChatHandler implements Runnable
{

    Socket skt;
    DataInputStream din;
    DataOutputStream dout;

    public ChatHandler(Socket skt)throws Exception
    {

        System.out.println("@ChatHandler.Const");

        System.out.println("@ChatHandler.Const");

        this.skt = skt;
        din = new DataInputStream(skt.getInputStream());
        dout = new DataOutputStream(skt.getOutputStream());

        new Thread(this).start();

    }

    public void run(){

        System.out.println("@ChatHandler.run");

        try{

            new ChatReader(din);
            new ChatWriter(dout);

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@ChatHandler.run :: "+ex.getMessage());
        }

    }

}