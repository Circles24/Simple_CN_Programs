import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable
{
 
    public Socket skt;

    Thread reader;
    Thread writer;

    public void read(){

        System.out.println("@Client.read");

        try{

            DataInputStream din = new DataInputStream(skt.getInputStream());

            while(true)System.out.println(din.readUTF());

        }

        catch(Exception ex){

            System.out.println("Exception@Client.read :: "+ex.getMessage());
        }

    }

    public void write(){

        try{

            System.out.println("@Client.read");

            DataOutputStream dout = new DataOutputStream(skt.getOutputStream());
            Scanner scn = new Scanner(System.in);

            while(true)dout.writeUTF(scn.nextLine());

        }

        catch(Exception ex){

            System.out.println("Exception@Client.read :: "+ex.getMessage());
        }


    }

    public void run(){

        if(Thread.currentThread() == reader)read();

        else write();
    }

    public Client(InetAddress serverIP,int serverPortNo) throws Exception
    {

        skt = new Socket(serverIP,serverPortNo); 

        reader = new Thread(this);
        writer  = new Thread(this);

        writer.start();
        reader.start();

    }

    public static void main(String args[]){

        System.out.println("@Client.main");

        try{

            new Client(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java Client <serverIP> <serverPortNo> ");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Client.main :: "+ex.getMessage());
        }
    }
}