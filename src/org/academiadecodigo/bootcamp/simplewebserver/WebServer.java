package org.academiadecodigo.bootcamp.simplewebserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer {
    private Logger logger= Logger.getLogger(WebServer.class.getName());
    public static void main(String[] args) {
    WebServer server=new WebServer();
    server.createServer();
    }

    private void createServer(){
        try {
            ServerSocket server=new ServerSocket(8181);
            connectClient(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void connectClient (ServerSocket server)  {
    while (true) {
        try {
            Socket clientSocket = server.accept();
            communicate(clientSocket);
            if (clientSocket.isConnected()) {
                logger.log(Level.INFO, "Client has connected to server");
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

    private void communicate(Socket clientSocket) {
        try {
            DataOutputStream out=new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader in= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientReq=requestHeader(in);
            logger.log(Level.INFO,clientReq);
            File file=new File("www/index.html");
            File file1= new File("www/logo.png");
            File file2= new File("www/favicon.ico");
            if(clientReq.equals("index.html")){
                headerCreator(file,out);
            }
            else if(clientReq.equals("logo.png")){
                headerCreator(file1,out);

            }
            else if(clientReq.equals("favicon.ico")){
                headerCreator(file2, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException ex){
            ex.getMessage();
        }
    }

    private String requestHeader(BufferedReader in) throws IOException {
            String clientReq=in.readLine();
            String clientRes=clientReq.split(" /| ")[1];

        return clientRes;
    }

    private void headerCreator(File file, DataOutputStream out) throws IOException {
        System.out.println("started writing the "+file.getName());
        FileInputStream fileIn=new FileInputStream(file);
        out.writeBytes("HTTP/1.1 200 Document Follows\r\n");
        out.writeBytes(  "Content-Type: text/html; charset=UTF-8\r\n"+
                "Content-Length:" + file.length()+"\r\n"+"\r\n");
        byte[] buffer= new byte[1024];
        int numbytes;
        while((numbytes=fileIn.read(buffer))!=-1){
            out.write(buffer,0,numbytes);
        }
        fileIn.close();
    }
}
