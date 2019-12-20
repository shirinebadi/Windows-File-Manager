package midterm;
import java.net.*;
import java.io.*;
import  java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket ss;

    public Server(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream(clientSock.getOutputStream().toString());
        byte[] buffer = new byte[4096];

        int filesize = 15123; // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
//        int read1 = dis.read(buffer);
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);

//            System.out.println("RECV: " + new String(buffer, 0, read));
        }

        fos.close();
        dis.close();
    }

    public static void main(String[] args) throws IOException {
        Server fs = new Server(9691);
        fs.start();
        FileManage fg = new FileManage();
        ServerSocket serverSocket = new ServerSocket(7898);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        File transferFile = new File (fg.currentFile.getPath());
        byte [] bytearray  = new byte [(int)transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(bytearray,0,bytearray.length);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(bytearray,0,bytearray.length);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }

    }
