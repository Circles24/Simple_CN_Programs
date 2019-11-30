import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ChatProcessor implements Runnable
{

    LinkedList<DataOutputStream> streams;
    DataInputStream gDin;

    public void run(){

        try{

            DataInputStream din = gDin;
            String mesg;

            while(true){

                mesg = din.readUTF();

                for(DataOutputStream dout:streams){

                    dout.writeUTF(mesg);
                }
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@ChatProcessor :: "+ex.getMessage());
        }
    }

    public void add(InputStream in,OutputStream out){

        gDin = new DataInputStream(in);
        DataOutputStream dout = new DataOutputStream(out);

        streams.add(dout);

        new Thread(this).start();

    }

    public ChatProcessor(){

        streams = new LinkedList<DataOutputStream>();

    }

}