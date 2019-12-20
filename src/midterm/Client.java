package midterm;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket server = new Socket("127.0.0.1", 9691)) {
            System.out.println("Connected to server.");
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());
            FileManage fg = new FileManage();
            System.out.println(fg.finalCurrentFile.getName());
            FileInputStream fis = new FileInputStream(fg.currentFile.getPath());
            byte[] buffer = new byte[4096];
            int read;
            while ((read=fis.read(buffer)) > 0) {
                dos.write(buffer,0,read);
//                System.out.println(new String(buffer, 0, read));
            }
            fis.close();
            dos.close();
        }

        catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }
}