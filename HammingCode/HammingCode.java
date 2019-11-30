import java.util.*;

public class HammingCode{

    public static void display(int arr[]){

        int n = arr.length;

        for(int i=0;i<n;i++)System.out.print(arr[i]+" ");
        System.out.println();
    }

    public static int[] encode(int[] data){

        System.out.println("Given Data");
        display(data);

        int n = data.length, r = 0, k = 0,x;

        while((int)Math.pow(2,r) < r+n+1)r++;

        int[] modData = new int[r+n+1];

        for(int i=1;i<r+n+1;i++){

            if( (i&(i-1)) != 0)modData[i] = data[k++]; 

        }

        k = 1;

        System.out.println("unset parity data");
        display(modData);

        for(int i=0;i<r;i++){

            x = (int)Math.pow(2,i);

            for(int j = 1; j < (n+r+1) ;j++ ){

                if(x != j && ((j>>i)%2 == 1))
                    modData[x] ^= modData[j];
            }
        }

        System.out.println("Mod Data");
        display(modData);
        
        return modData;
    }

    public static void decode(int[] modData){

        int n,x,r = (int)Math.ceil(Math.log(modData.length-1)/Math.log(2));

        n = modData.length;

        for(int i=0;i<r;i++){

            x = (int)Math.pow(2,i);

            for(int j=1;j<n;j++){

                if(j != x && (j>>i)%2 == 1){

                    modData[x] ^= modData[j];
                }
            }

        }

        System.out.println("restored data ");
        display(modData);

        int[] cArr = new int[r];
        int k = 0;

        for(int i=1;i<n;i++){

            if((i&(i-1)) == 0)cArr[k++] = modData[i];
        }

        for(int i=0;i<r/2;i++){
            
            k = cArr[i];
            cArr[i] = cArr[r-i-1];
            cArr[r-i-1] = k;
        }

        int num = 0;

        for(int i=0;i<r;i++)if(cArr[i] == 1)num |= (int)Math.pow(2,i);

        System.out.println("ind of imp "+num);

        System.out.println("restored data");
        
        for(int i=1;i<n;i++){
            
            if((i&(i-1)) == 0)continue;

            System.out.print(modData[i]);
        }

        System.out.println();
    
    }

    public static void main(String args[]){

        try{

            int n = args[0].length();

            if(n != 8)throw new Exception("insufficient data");
            
            int data[] = new int[n];
            
            for(int i = 0; i < n ; i++ )data[i] = args[0].charAt(i)-'0';

            int[] modData = HammingCode.encode(data);
            
            HammingCode.decode(modData);

        }

        catch(ArrayIndexOutOfBoundsException ex){

            System.out.println("USAGE :: java HammingCode <bitset(8)>");
        }

        catch(Exception ex){

            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }

    }
}