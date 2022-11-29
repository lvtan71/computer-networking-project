package model;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ApplicationModel {
    private InputStream inputStream;
    private OutputStream outputStream;

    public ApplicationModel(InputStream inputStream, OutputStream outputStream){
        initComponents(inputStream,outputStream);
    }

    private void initComponents(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    public void listAppInstalled(){
        try{
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

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));

            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {

                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException iE)
                {
                    iE.printStackTrace();
                }

                line = line.replace('\\', '/');
                line = line.replace("\"", "");
                line = line.replaceAll(",0", "");
                line = line.replaceAll(",-101", "");
                line.trim();
                byte[] buffer = line.getBytes(StandardCharsets.UTF_8);
                outputStream.write(buffer);
                System.out.println(line);
            }
        }
        catch (IOException ioE){
            ioE.printStackTrace();
        }
    }

    public void listAppRunning(){
        try{
            byte [] buffer;

            Process process = Runtime.getRuntime().exec("powershell.exe get-process | where-object {$_.mainwindowhandle -ne 0} | select-object name, Id, mainwindowtitle");

            String line;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

//            bufferedReader.readLine();
//            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null)
            {
                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException iE)
                {
                    iE.printStackTrace();
                }

                buffer = line.getBytes(StandardCharsets.UTF_8);
                outputStream.write(buffer);
                System.out.println(line);
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

    }


    public void stopAppRunning()
    {

        try {
            byte[] buffer = new byte[1024];
            inputStream.read(buffer);

            String IDAppRunning = new String(buffer, StandardCharsets.UTF_8);

            String notifyStopProcess;
            if(isRunning(IDAppRunning)){
                Runtime appRuntime = Runtime.getRuntime();
                appRuntime.exec("taskkill /PID " + IDAppRunning.trim());
                notifyStopProcess = "1";
            }
            else{
                notifyStopProcess = "0";
            }

            buffer = notifyStopProcess.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

        }
        catch (Exception e)
        {
            //
        }
    }


    public static boolean isRunning(String appID){
        try {
            Process process = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + appID.trim() + "\"");

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if (line.contains(" " + appID.trim() + " ")){
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public void startApp(){
        try{
            byte[] buffer = new byte[1024];

            inputStream.read(buffer);

            String apploc = new String(buffer, StandardCharsets.UTF_8);
            apploc = apploc.trim();

            ProcessBuilder pb = new ProcessBuilder(apploc);
            pb.start();

            buffer = "1".getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

            System.out.println(new String(buffer, StandardCharsets.UTF_8));
        }
        catch (IOException ioException){
            try
            {
                outputStream.write("0".getBytes(StandardCharsets.UTF_8));
            }
            catch (IOException ioE)
            {

            }
        }
    }
}
