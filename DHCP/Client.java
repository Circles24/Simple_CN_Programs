import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable{

    public Socket skt;

    public Client(InetAddress addr,int portNo) throws Exception
    {

        skt = new Socket(addr,portNo);

        new Thread(this).start();
    }

    public void run(){

        try{

            System.out.println("@Client.run");

            DataOutputStream dout = new DataOutputStream(skt.getOutputStream());
            DataInputStream din = new DataInputStream(skt.getInputStream());
        
            dout.writeInt(1);

            System.out.println(din.readUTF());

            dout.close();
            din.close();
            skt.close();
                    
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("EXCEPTION@CLIENT.RUN :: "+ex.getMessage());
        }
    }

    public static void main(String args[]){

        System.out.println("@Client.main");

        try{

            new Client(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));
        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("useage :: java Client <server ip> <server port>");

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("EXCEPTION@CLIENT.MAIN :: "+ex.getMessage());
        }
    }


}