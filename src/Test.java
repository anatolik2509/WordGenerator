import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException{
        PrintWriter wr=new PrintWriter(new File("log.txt"));
        Generator.load("rus.txt");
        for(int i=0;i<100;i++){
            wr.println(Generator.generateWord());
        }
        wr.close();
    }
}
