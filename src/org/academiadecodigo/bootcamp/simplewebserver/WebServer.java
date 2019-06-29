package org.academiadecodigo.bootcamp.simplewebserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private  void connectClient(ServerSocket server) {
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
            System.out.println(requestHeader(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String requestHeader(BufferedReader in) throws IOException {
            String clientReq=in.readLine();

        return clientReq;
    }
}
