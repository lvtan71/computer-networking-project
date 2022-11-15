
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Process;

public class SystemProcess {
    public static void main(String[] args) {
        try {
            String line;
            // getRuntime: Returns the runtime object associated with the current Java application.
            // exec: Executes the specified string command in a separate process.
            Process process = Runtime.getRuntime().exec (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
            BufferedReader processReader =  new BufferedReader(new InputStreamReader(process.getInputStream()));
            // Read from BufferedReader
            while ((line = processReader.readLine()) != null) { // readline: hàm đọc buffer
                System.out.println(line); // <-- Print all Process here line
                                                // by line
            }
            processReader.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}