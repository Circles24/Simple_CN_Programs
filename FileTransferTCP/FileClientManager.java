import java.io.*;
import java.net.*;

public class FileClientManager implements Runnable
{

    Socket skt;
    DataInputStream din;
    DataOutputStream dout;

    public void run(){

        System.out.println("@FileClientManager.run");

        try{

            String s;
            FileInputStream fin;
            int n;
            byte[] buff = new byte[8196];

            while(true){

                s = din.readUTF();

                System.out.println("Client Req :: "+s);

                fin = new FileInputStream(s);

                if(fin == null)dout.writeInt(0);

                else{

                    dout.writeInt(fin.available());

                    System.out.println("File Size :: "+fin.available());

                    n = 0;

                    while(n != -1){

                        n = fin.read(buff);
                        if(n != -1)dout.write(buff,0,n);
                    }

                    fin.close();
                }

            }
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    public FileClientManager(Socket skt) throws Exception
    {

        System.out.println("@FileClientManager.FileClientManager");

        this.skt = skt;
        din = new DataInputStream(skt.getInputStream());
        dout = new DataOutputStream(skt.getOutputStream());

        new Thread(this).start();
    }

}