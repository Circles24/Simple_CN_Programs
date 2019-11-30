import java.net.*;

public class Address{

    public InetAddress ip;
    public int portNo;

    public Address(InetAddress ip,int portNo){

        this.ip = ip;
        this.portNo = portNo;
    }
}