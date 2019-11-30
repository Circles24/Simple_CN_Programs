import java.util.*;
import java.io.*;
import java.net.*;

public class Server implements Runnable{

    public ServerSocket ss;
    public Map<Integer,Boolean> mp;

    public Server(int port) throws Exception
    {

        System.out.println("@server.const");

        mp = new HashMap<>();

        ss = new ServerSocket(port);

        new Thread(this).start();
    }

    public void run(){

        System.out.println("@Server.run");
    
        try{

            while(true){

                Socket skt = ss.accept();

                System.out.println("Got a connection @ "+skt);

                new ClientHandler(skt,mp);

            }    
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("EXCEPTION@SERVER.RUN:: "+ex.getMessage());
        }
    }

    public static void main(String args[]){

        System.out.println("@main");

        try{

            new Server(Integer.parseInt(args[0]));
        }

        catch(ArrayIndexOutOfBoundsException  ex){

            System.out.println("USE :: java Server <portNo>");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }


}

class ClientHandler implements Runnable{

    public Map<Integer,Boolean> mp;

    public Socket skt;

    public ClientHandler(Socket skt,Map<Integer,Boolean> mp){

        System.out.println("@ClientHandler.const");

        this.mp = mp;
        this.skt = skt;

        new Thread(this).start();
    }

    public void run(){

        try{

            System.out.println("@ClientHandler.run");

            DataInputStream din = new DataInputStream(skt.getInputStream());
            DataOutputStream dout = new DataOutputStream(skt.getOutputStream());

            System.out.println("Clinet Req :: "+din.readUTF());

            boolean flg = false;

            for(int i=1;i<256;i++){
                if(mp.get(i) == null){

                    dout.writeUTF("192.168.217."+i);
                    mp.put(i,true);

                    flg = true;

                    break;
                }
            }

            if(flg == false){

                dout.writeUTF("Network is too busy");
            }

            dout.close();
            din.close();
            skt.close();
            
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@ClientHandler.run:: "+ex.getMessage());
        }
        
    }


}