import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatWriter implements Runnable
{

    DataOutputStream dout;
    Scanner scn;

    public ChatWriter(DataOutputStream dout)throws Exception
    {

        System.out.println("@ChaWriter.Const");

        this.dout = dout;
        scn = new Scanner(System.in);

        new Thread(this).start();
    }

    public void run(){

        System.out.println("@ChaWriter.run");

        try{ 
            
            while(true){

                String s;

                s = scn.next();

                dout.writeUTF(s);
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@ChatReader.run :: "+ex.getMessage());
        }

    }
}