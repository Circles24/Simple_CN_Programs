import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable
{

    DatagramSocket skt;
    int serverPortNo;
    InetAddress serverIP;
    Scanner scn;

    Thread reader;
    Thread writer;

    public void write(){

        System.out.println("@Client.write");

        try{

            String mesg;

            while(true){

                mesg = scn.nextLine();
                
                skt.send(new DatagramPacket(mesg.getBytes(),mesg.length(),serverIP,serverPortNo));
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Client.read :: "+ex.getMessage());
        }
    }

    public void read(){

        System.out.println("@Client.read");

        try{

            DatagramPacket dpkt = new DatagramPacket(new byte[1024],1024);

            while(true){

                skt.receive(dpkt);
                System.out.println(new String(dpkt.getData(),0,dpkt.getLength()));

            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Client.read :: "+ex.getMessage());
        }
    }

    public void run(){

        System.out.println("@client.run");

        if(Thread.currentThread() == reader)read();

        else if(Thread.currentThread() == writer)write();

    }

    public Client(InetAddress serverIP,int serverPortNo)throws Exception
    {   
        System.out.println("@Client.Client");

        skt = new DatagramSocket();
        scn = new Scanner(System.in);
        this.serverIP = serverIP;
        this.serverPortNo = serverPortNo;
        
        reader = new Thread(this);
        writer = new Thread(this);

        reader.start();
        writer.start();
    }
    public static void main(String args[]){

        System.out.println("@Server.main");

        try{

            new Client(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("EXception@Server.main :: "+ex.getMessage());
        }
    }
    
}