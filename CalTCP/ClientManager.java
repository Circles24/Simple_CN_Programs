import java.io.*;
import java.net.*;

public class ClientManager implements Runnable
{

    Socket skt;

    public ClientManager(Socket skt) throws Exception
    {

        System.out.println("@ClientManager.Const");

        this.skt = skt;

        new Thread(this).start();
    }

    public String processQuery(String q){

        try{

            Integer n = q.length(),pos = -1,cnt=0,num1 = 0,num2 = 0,op = 0;
            char ch;

            for(int i=0;i<n;i++){

                ch = q.charAt(i);

                if(ch < '0' || ch > '9'){

                    if(ch == '+')op = 0;

                    else if(ch == '-')op = 1;
                    
                    else if(ch == '*')op = 2;

                    else if(ch == '/')op = 3;

                    else throw new Exception("Wrong_Format");

                    pos = i;

                    cnt++;
                }   
            }

            if(cnt != 1 || pos == 0 || pos == n-1)throw new Exception("Wrong_Fromat");

            for(int i=0;i<pos;i++)num1 += num1*10+(q.charAt(i)-'0');

            for(int i=pos+1;i<n;i++)num2 += num2*10+(q.charAt(i)-'0');

            if(op == 0)return num1+" + "+num2+" = "+(num1+num2);

            else if(op == 1)return num1+" - "+num2+" = "+(num1-num2);

            else if(op == 2)return num1+" * "+num2+" = "+(num1*num2);

            else {

                if(num2 == 0)return num1+" + "+num2+" = inf";

                else return num1+" / "+num2+" = "+(num1/num2);
            }

        }

        catch(Exception ex){

            return " num1 op num2  <- desired format ";
        }
    }

    public void run(){

        System.out.println("@ClientManager.run");

        try{

            DataInputStream din = new DataInputStream(skt.getInputStream());
            DataOutputStream dout = new DataOutputStream(skt.getOutputStream());

            while(true){

                String q = din.readUTF();

                String res = processQuery(q);

                dout.writeUTF(res);
                
            }

        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@ClientManager.run :: "+ex.getMessage());
        }
    }

}