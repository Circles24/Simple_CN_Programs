import java.io.*;
import java.net.*;
import java.util.*;

public class FileClient implements Runnable
{

    DatagramSocket skt;
    Scanner scn;
    InetAddress serverAddr;
    int portNo;

    public void run(){

        System.out.println("@FileClient.run");

        byte[] buff = new byte[1024];

        try{

            while(true){

                System.out.println("Enter the file name");

                String fileName = scn.nextLine();

                System.out.println("# "+fileName);

                DatagramPacket dpkt = new DatagramPacket(fileName.getBytes(),fileName.getBytes().length,serverAddr,portNo);
                
                skt.send(dpkt);

                System.out.println("Message Sent to Server");

                DatagramPacket res = new DatagramPacket(buff,1024);

                skt.receive(res);

                System.out.println("response recieved");

                String ack = new String(res.getData(),0,res.getLength());

                int n = Integer.parseInt(ack);

                if(n == 0){

                    System.out.println("Wrong query");

                }

                else {

                    System.out.println("File Length :: "+n);

                    FileOutputStream fout = new FileOutputStream("output/"+fileName);

                    while( n > 0 ){

                        skt.receive(res);

                        fout.write(res.getData(),0,res.getLength());
                    
                        n -= res.getLength();
                    }

                    fout.close();

                    System.out.println("op Sucessful");
                }

            }
        
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@FileClient.run :: "+ex.getMessage());
        }
    }

    public FileClient(InetAddress serverAddr, int serverPort)throws Exception
    {

        skt = new DatagramSocket();
        this.serverAddr = serverAddr;
        this.portNo = serverPort;
        scn = new Scanner(System.in);

        new Thread(this).start();
    }

    public static void main(String args[]){

        try{

            new FileClient(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@FileServer.main :: "+ex.getMessage());
        }
    }
}