import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable
{
    Socket skt;
    Scanner scn;

    public void run(){

        System.out.println("@Client.run");

        try{

            String q;

            DataInputStream din = new DataInputStream(skt.getInputStream());
            DataOutputStream dout  = new DataOutputStream(skt.getOutputStream());

            while(true){

                System.out.println("enter the Query");

                dout.writeUTF(scn.nextLine());
                
                System.out.println("Server :: "+din.readUTF());
                
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("EXception@Client.run :: "+ex.getMessage());
        }
    }

    public Client(InetAddress serverAddr,int port) throws Exception
    {

        System.out.println("@Client.Const");

        skt = new Socket(serverAddr,port);
        scn = new Scanner(System.in);

        new Thread(this).start();
    }

    public static void main(String args[]){

        System.out.println("@Client.main");

        try{

            new Client(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));
        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java Client <ServerIP> <ServerPort>");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Client.Main :: "+ex.getMessage());
        }
    }

}