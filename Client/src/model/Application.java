package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Application {
    private Socket sock;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ArrayList<ArrayList<String>> appRunning;
    private ArrayList<ArrayList<String>> appInstalled;

    public Application(Socket sock, InputStream inputStream, OutputStream outputStream){
        initComponents(sock, inputStream, outputStream);
    }

    private void initComponents(Socket sock, InputStream inputStream, OutputStream outputStream) {
        this.sock = sock;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        appRunning = new ArrayList<>();
        appInstalled = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> listAppInstalled() {
        try {
            sock.setSoTimeout(400);
            byte[] buffer = new byte[1024];
            inputStream.read(buffer); // Doc dong dau la cac truong
            String line;
            while(true){
                inputStream.read(buffer);
                line = new String(buffer, StandardCharsets.UTF_8);
                line = line.trim();

                ArrayList<String> row = new ArrayList<String>();
                row.add("  " + line.substring(0,66).trim());
                row.add("  " + line.substring(66,200).trim());

                appInstalled.add(row);
            }
        }catch (IOException ioException){

        }
        return appInstalled;
    }

    public ArrayList<ArrayList<String>> listAppRunning(){
        try {
            sock.setSoTimeout(400);
            byte[] buffer = new byte[1024];
            inputStream.read(buffer); // Doc dong dau la cac truong
            String line;
            while(true){
                inputStream.read(buffer);
                line = new String(buffer, StandardCharsets.UTF_8);
                line = line.trim();

                ArrayList<String> row = new ArrayList<String>();
                row.add("  " + line.substring(0,21).trim());
                row.add("  " + line.substring(21,26).trim());
                row.add("  " + line.substring(26,100).trim());

                appRunning.add(row);
            }
        }catch (IOException ioException){

        }
        return appRunning;
    }

    public void stopApp(String AppID){
        try{
            byte[] buffer = AppID.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);
        }catch (IOException ioException) {

        }
    }

    public void startApp(String AppLocation){
        try{
            byte[] buffer = AppLocation.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);
        }
        catch (IOException ioException){

        }
    }
}
