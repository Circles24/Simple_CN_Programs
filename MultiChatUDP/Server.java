import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable
{

    DatagramSocket skt;
    HashMap<Address,Boolean> mp;
    LinkedList<Address> clientList;

    public void run(){

        System.out.println("@Server.run");

        try{

            byte[] buff = new byte[1024];

            DatagramPacket pkt = new DatagramPacket(buff,1024);

            Address addr;

            while(true){

                skt.receive(pkt);

                addr = new Address(pkt.getAddress(),pkt.getPort());

                if(mp.containsKey(addr) == false){

                    mp.put(addr,true);
                    clientList.add(addr);
                }

                for(Address sendAddr:clientList){

                    if(sendAddr != addr)skt.send(new DatagramPacket(pkt.getData(),pkt.getLength(),sendAddr.ip,sendAddr.portNo));
                }
                
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("EXception@Server.run :: "+ex.getMessage());
        }
    }


    public Server(int portNo)throws Exception
    {

        System.out.println("@Server.Server");

        skt = new DatagramSocket(portNo);
        mp = new HashMap<Address,Boolean>();
        clientList = new LinkedList<Address>();

        new Thread(this).start();

    }

    public static void main(String args[]){

        System.out.println("@Server.main");

        try{

            new Server(Integer.parseInt(args[0]));

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java Server <portNo> ");
        }

        catch(Exception ex){

            System.out.println("Exception@Server.main :: "+ex.getMessage());

        }
    }
}