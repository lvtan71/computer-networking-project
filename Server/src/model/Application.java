package model;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Application {
    private InputStream inputStream;
    private OutputStream outputStream;

    public Application(InputStream inputStream, OutputStream outputStream){
        initComponents(inputStream,outputStream);
    }

    private void initComponents(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    public void appInstalled(){
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

            byte [] buffer = new byte[1024];

            BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));

            line = stdout.readLine();
            line = stdout.readLine();

            while ((line = stdout.readLine()) != null) {
                System.out.println();
                System.out.println();

                line = line.replace('\\', '/');
                line = line.replace("\"", "");
                line = line.replaceAll(",0", "");
                line = line.replaceAll(",-101", "");
                buffer = line.getBytes(StandardCharsets.UTF_8);
                outputStream.write(buffer);
            }
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    public void appRunning(){
        try{
            byte [] buffer = new byte[1024];

            Process process = Runtime.getRuntime().exec("powershell.exe get-process | where-object {$_.mainwindowhandle -ne 0} | select-object name, Id, mainwindowtitle");

            String line;

            BufferedReader appReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            line = appReader.readLine();


            while((line = appReader.readLine()) != null){
                System.out.println();
                System.out.println();
                buffer = line.getBytes(StandardCharsets.UTF_8);
                outputStream.write(buffer);
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

    }
    public void stopApp(){
        try {
            // Stop App
            byte [] BufferedReader = new byte[1024];

            inputStream.read(BufferedReader);

            String appID = new String(BufferedReader,StandardCharsets.UTF_8);


            if(isRunning(appID)){
                Runtime appRuntime = Runtime.getRuntime();
                appRuntime.exec("taskkill /F /PID " + appID.trim());
                String notice = "App with ID" + appID.trim() + " has been stopped";
                System.out.println(notice);
                response(outputStream, notice);
            }
            else{
                String notice = "There is no App with ID " + appID + " is running";
                System.out.println(notice);
                response(outputStream, notice);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void response(OutputStream os, String res){
        try {
            byte [] buffer = new byte[1024];

            buffer = res.getBytes(StandardCharsets.UTF_8);

            os.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startApp(){
        try{
            byte[] buffer = new byte[1024];

            inputStream.read(buffer);

            String apploc = new String(buffer, StandardCharsets.UTF_8);
            apploc = apploc.trim();

            ProcessBuilder pb = new ProcessBuilder(apploc);
            pb.start();

        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
