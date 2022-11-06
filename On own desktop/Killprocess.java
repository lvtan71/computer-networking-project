import java.io.IOException;
import java.util.Scanner;

public class Killprocess {
    public static void main(String[] args){
        try {
        String processname;

        Scanner input = new Scanner(System.in);
        processname = input.nextLine();
        Runtime process = Runtime.getRuntime();
        process.exec("taskkill /F /IM " + processname + ".exe");
        input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
