import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test2 {
    public static void main(String[] args)throws IOException {
        Scanner sc=new Scanner(new File("rus_city.txt"));
        int n=0;
        while (sc.hasNext()){

            Generator.readWord(sc.next());
            n++;
            if(n%1000==0) System.out.println(n);

        }
        Generator.save("rus.txt");
        sc.close();
    }
}
