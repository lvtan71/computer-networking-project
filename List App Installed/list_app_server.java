import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class list_app_server {
    public static void main(String[] args){
        try {


            ServerSocket svSocket = new ServerSocket(9999, 5,InetAddress.getLocalHost());

            System.out.println("Created Socket");

            System.out.println("Connecting");

            Socket clSocket = svSocket.accept();

            System.out.println("Connected");


            // Executing the command

            //String originCommand = "powershell.exe -command \"Get-ItemProperty HKLM:/Software/Microsoft/Windows/CurrentVersion/Uninstall/*,HKCU:/Software/Microsoft/Windows/CurrentVersion/Uninstall/*,HKLM:/Software/Wow6432Node/Microsoft/Windows/CurrentVersion/Uninstall/*,HKCU:/Software/Wow6432Node/Microsoft/Windows/CurrentVersion/Uninstall/* |Select-Object DisplayName, Publisher, DisplayIcon  |Where-Object SystemComponent -ne 1 |Sort-Object Publisher, DisplayName |Format-Table -AutoSize\"";
            String dir1 = "HKLM:/Software/Microsoft/Windows/CurrentVersion/Uninstall/*";
            String dir2 = "HKCU:/Software/Microsoft/Windows/CurrentVersion/Uninstall/*";
            String dir3 = "HKLM:/Software/Wow6432Node/Microsoft/Windows/CurrentVersion/Uninstall/*";
            // Thư mục này máy có máy không
            String dir4 = "HKCU:/Software/Wow6432Node/Microsoft/Windows/CurrentVersion/Uninstall/*";
            // Bỏ câu lệnh sau dấu | để thấy nhiều trường tùy chọn hơn, sau đó thêm sau Selec-object để chọn hiện thị các trường đó
            String custom = "|Select-Object DisplayName, DisplayIcon  |Where-Object SystemComponent -ne 1|Format-Table";
            // Gồm cả những App hệ thống
            String command = "powershell.exe -command \" Get-ItemProperty " + dir1 + "," + dir2 + "," + dir3 + " -ErrorAction SilentlyContinue" + custom + "\"";
            // Gồm những App chính
            // Out-String -Width 4096 dùng để format cỡ chữ in ra, không thì nó sẽ viết tắt bằng "..." làm mất dữ liệu
            String diffCommand = "powershell.exe -command \"$table = foreach ($UKey in 'HKLM:/SOFTWARE/Microsoft/Windows/CurrentVersion/Uninstall/*','HKLM:/SOFTWARE/Wow6432node/Microsoft/Windows/CurrentVersion/Uninstall/*','HKCU:SOFTWARE/Microsoft/Windows/CurrentVersion/Uninstall/*','HKCU:/SOFTWARE/Wow6432node/Microsoft/Windows/CurrentVersion/Uninstall/*'){foreach ($Product in (Get-ItemProperty $UKey -ErrorAction SilentlyContinue)){if($Product.DisplayName -and $Product.SystemComponent -ne 1){new-object psobject -Property @{Name = $Product.DisplayName; Location = $Product.DisplayIcon}}}} $table | Out-String -Width 4096 | Format-Table -Autosize\"";
            
            Process powerShellProcess = Runtime.getRuntime().exec(diffCommand);

            String line;

            OutputStream os = clSocket.getOutputStream();
            byte [] buffer = new byte[1024];

            // In ra file thu
            // Trong file moi app se ghi tren 1 dong, con tren terminal khong du cho nen phai xuong dong
            //FileOutputStream fos = new FileOutputStream("output.txt");
            //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


            System.out.println("Standard Output:");
            BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
            while ((line = stdout.readLine()) != null) {
                System.out.println();
                System.out.println();
                //bw.write(line);
                //bw.newLine();
                line = line.replace('\\', '/');
                line = line.replace("\"", "");
                line = line.replaceAll(",0", "");
                line = line.replaceAll(",-101", "");
                buffer = line.getBytes(StandardCharsets.UTF_8);
                os.write(buffer);
            }
            //bw.close();
            stdout.close();
            clSocket.close();
            svSocket.close();
            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
