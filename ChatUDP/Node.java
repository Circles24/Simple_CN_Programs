import java.io.*;
import java.net.*;
import java.util.*;

class Address{

    InetAddress ip;
    public int portNo;

    public Address(InetAddress ip,int portNo){

        this.ip = ip;
        this.portNo = portNo;
    }

    public String toString(){

        return (ip.toString() + "  "+ portNo);
    }
}

public class Node implements Runnable
{

    LinkedList<DatagramPacket> chatLog;
    HashMap<Address,String> contactDict;
    HashMap<String,Address> invContactDict;
    Scanner scn;

    DatagramSocket sSkt;
    DatagramSocket rSkt;

    Thread reader;

    public int menu(){

        System.out.println("______________MENU______________");
        System.out.println("View My Addr          ::       0");
        System.out.println("Add contact           ::       1");
        System.out.println("Remove contact        ::       2");
        System.out.println("Read Messages         ::       3");
        System.out.println("Send Message          ::       4");
        System.out.println("View Contacts         ::       5");
        System.out.println("Exit                  ::       6");

        return scn.nextInt();
    }

    public void add()throws Exception
    {

        System.out.println("Enter ip, port, Name of the Contact to be inserted");

        InetAddress ip;
        int portNo;
        String name;

        ip = InetAddress.getByName(scn.next());
        portNo = scn.nextInt();
        name = scn.next();

        contactDict.put(new Address(ip,portNo),name);
        invContactDict.put(name,new Address(ip,portNo));

    }

    public void remove()throws Exception
    {

        System.out.println("Enter ip, port, Name of the Contact to be removed");

        InetAddress ip;
        int portNo;
        String name;

        ip = InetAddress.getByName(scn.next());
        portNo = scn.nextInt();
        name = scn.next();

        contactDict.remove(new Address(ip,portNo));
        invContactDict.remove(name);

    }

    public void readMessages()throws Exception
    {

        String mesg;

        for(DatagramPacket dpkt:chatLog){

            System.out.println("addr :: "+dpkt.getAddress()+dpkt.getPort());

            System.out.println(contactDict.get(new Address(dpkt.getAddress(),dpkt.getPort())));
            
            mesg = new String(dpkt.getData(),0,dpkt.getLength());

            System.out.println(mesg);

            System.out.println("____________________________________");
            
        }
        
    }

    public void send()throws Exception
    {

        System.out.println("enter the contact name");
        String name = scn.next();
        Address addr = invContactDict.get(name);

        if(addr == null)throw new Exception("wrong contact name");

        System.out.println("Enter the message");

        scn.nextLine();
        String mesg = scn.nextLine();

        System.out.println("mesg :: "+mesg);

        sSkt.send(new DatagramPacket(mesg.getBytes(),mesg.length(),addr.ip,addr.portNo));
        
    }

    public void viewContacts()throws Exception
    {

        for(Map.Entry<Address,String> elem:contactDict.entrySet()){

            System.out.println(elem.getValue()+" :: "+elem.getKey());
        }

    }

    public void read(){

        System.out.println("@Node.read");
        
        try{

            while(true){

                byte[] buff = new byte[1024];

                DatagramPacket dpkt = new DatagramPacket(buff,1024);

                rSkt.receive(dpkt);

                System.out.println(new String(dpkt.getData(),0,dpkt.getLength()));

                chatLog.add(dpkt);

            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Node.read :: "+ex.getMessage());
        }
    }

    void viewMyAddr(){

        try{

            System.out.println("ip :: "+rSkt.getLocalAddress()+" port :: "+rSkt.getLocalPort());

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Node.viewMyAddr :: "+ex.getMessage());
        }
    }

    public void run(){

        if(Thread.currentThread() == reader)read();

        else {

            int choice;

            while(true){

                try{

                    choice = menu();

                    if(choice == 0)viewMyAddr();

                    else if(choice == 1)add();

                    else if(choice == 2)remove();

                    else if(choice == 3)readMessages();

                    else if(choice == 4)send();

                    else if(choice == 5)viewContacts();

                    else if(choice == 6)break;

                    else System.out.println("wrong choice");

                }

                catch(Exception ex){

                    ex.printStackTrace();
                    System.out.println("Exception@Node.run :: "+ex.getMessage());
                }
            }
        }
    }


    public Node(InetAddress ip,int portNo)throws Exception
    {

        System.out.println("@Node.Node");

        rSkt = new DatagramSocket(portNo);
        sSkt = new DatagramSocket();
        contactDict = new HashMap<Address,String>();
        invContactDict = new HashMap<String,Address>();
        chatLog = new LinkedList<DatagramPacket>();
        scn = new Scanner(System.in);
        
        reader = new Thread(this);
        reader.start();
        Thread.sleep(100);
        new Thread(this).start();
    }

    public static void main(String args[]){

        System.out.println("@Node.main");

        try{

            new Node(InetAddress.getByName("localhost"),Integer.parseInt(args[1]));

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: <ip> <portNo> ");
        }

        catch(Exception ex){

            System.out.println("Exception@Node.main :: "+ex.getMessage());
        }
    }
}