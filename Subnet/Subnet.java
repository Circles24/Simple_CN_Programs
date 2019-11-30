import java.net.*;

public class Subnet{

    public static String getFaceValue(long ip){

        String[] arr = new String[4];

        for(int i=0;i<4;i++){

            arr[i] = String.valueOf(ip&(255));

            ip >>= 8;
        }

        StringBuilder s = new StringBuilder();

        for(int i=3;i>=0;i--){

            if(i < 3)s.append('.');
            s.append(arr[i]);
            
        }

        return s.toString();
    }

    public static String getBinaryView(long x,int n){

        StringBuilder s = new StringBuilder();

        for(int i=0;i<n;i++){

            s.append((char)((x%2)+'0'));
            x /= 2;
        }

        s.reverse();

        return s.toString();
    }

    public static void createSubnets(String ipStr,int mask,int noOfSubnets){

        System.out.println("@Subnet.createSubnet");

        String[] ipArr = ipStr.split("\\.");

        int[] ipIntArr = new int[4];

        for(int i=0;i<4;i++)ipIntArr[i] = Integer.parseInt(ipArr[i]);

        long ip = 0;
        
        for(int i=0;i<4;i++){

            ip <<= 8;
            ip |= ipIntArr[i];
        }

        int varBits = (32-mask-(int)Math.ceil(Math.log(noOfSubnets)/Math.log(2)));

        System.out.println("Given IP :: "+getFaceValue(ip));
        System.out.println("Given IP :: "+getBinaryView(ipIntArr[0],8)+" "+getBinaryView(ipIntArr[1],8)+" "+getBinaryView(ipIntArr[2],8)+" "+getBinaryView(ipIntArr[3],8));
        System.out.println("Given IP :: "+getBinaryView(ip,32));

        long subnetSize = 1<<(varBits);
        
        System.out.println("Subnet Size :: " + subnetSize );

        ip = ip & (~((1<<varBits)-1));

        System.out.println("Starting IP :: "+getFaceValue(ip));

        long offset = 0;

        for(int i=0;i<noOfSubnets;i++){

            System.out.println(getFaceValue(ip+offset)+" - "+getFaceValue(ip+offset+subnetSize-1));

            offset += subnetSize;
        }
        
    }

    public static void main(String args[]){

        System.out.println("@Subnet.main");

        try{

            int mask = Integer.parseInt(args[1]);

            if(mask > 31 || mask < 1)throw new Exception("wrong network mask");

            int noOfSubnets = Integer.parseInt(args[2]);

            if(mask +Math.ceil(Math.log(noOfSubnets)/Math.log(2)) > 31)throw new Exception("wrong input");

            InetAddress.getByName(args[0]);

            Subnet.createSubnets(args[0],mask,noOfSubnets);

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("Usage :: java subnet <givenIP> <networkMask>  <noOfSubnets>");
        }

        catch(Exception ex){

            ex.printStackTrace();
            System.out.println("Exception@Subnet.main :: "+ex.getMessage());
        }
    }
}