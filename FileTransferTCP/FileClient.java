import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FileClient implements Runnable
{

    Socket skt;
    DataInputStream din;
    DataOutputStream dout;
    FileOutputStream fout;
    Scanner scn;

    public void run(){

        System.out.println("@FileClient.run");

        try{

            String fName,s;
            int n,m;
            byte[] buff = new byte[8196];

            while(true){

                System.out.println("Enter the File name");

                fName = scn.nextLine();

                dout.writeUTF(fName);

                fName = "output/"+fName;

                fout = new FileOutputStream(fName);

                n = din.readInt();

                System.out.println("Server :: "+n);

                if(n == 0){

                    System.out.println("Server :: Wrong Input");
                    continue;
                }

                while(n > 0){

                    m = din.read(buff);

                    fout.write(buff,0,m);

                    n -= m;
                }

                fout.close();
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Execption@FileClient.run :: "+ex.getMessage());
        }

    }

    public FileClient(InetAddress ServerIP,int ServerPort) throws Exception
    {

        System.out.println("@FileClient.FileClient");

        scn = new Scanner(System.in);
        skt = new Socket(ServerIP,ServerPort);
        dout = new DataOutputStream(skt.getOutputStream());
        din = new DataInputStream(skt.getInputStream());

        new Thread(this).start();
    }

    public static void main(String args[]){

        System.out.println("@FileClient.main");

        try{

            new FileClient(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java FileClient <ServerIP> <ServerPort>");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@FileClient.main :: "+ex.getMessage());
        }
    }
    
}