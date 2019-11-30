import java.io.*;
import java.net.*;

public class ChatReader implements Runnable
{

    DataInputStream din;

    public ChatReader(DataInputStream din)throws Exception
    {

        System.out.println("@ChaReader.Const");

        this.din = din;

        new Thread(this).start();
    }

    public void run(){

        System.out.println("@ChaReader.run");

        try{ 
            
            while(true){

                String s;

                s = din.readUTF();

                System.out.println(s);
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@ChatReader.run :: "+ex.getMessage());
        }
    }
}