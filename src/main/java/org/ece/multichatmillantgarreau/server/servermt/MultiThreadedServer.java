/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.server.servermt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.ece.multichatmillantgarreau.server.AbstractMultiChatServer;
import org.ece.multichatmillantgarreau.server.MultiChatServer;


/**
 *
 * @author egarreau
 */
public class MultiThreadedServer extends AbstractMultiChatServer implements MultiChatServer{

    public MultiThreadedServer(int portNumber, String host) {
        super(portNumber, host);
    }

    public MultiThreadedServer(int portNumber, int backlog, String host) {
        super(portNumber, backlog, host);
    }

    @Override
    public void start() {
        boolean loop=true;
          ServerSocket serverSocket = null;
// Set up the Server Socket
        try {
            /*
             backlog is the number of connexion that the server socket 
             can handle and after he put on a queue the other one. */
            serverSocket = new ServerSocket(this.getPortNumber(), 50, this.getHost());

            System.out.println("New Server:");
            System.out.println("- listening on port: " + this.getPortNumber());
            System.out.println("- backlog: " + this.getBacklog());

        } catch (IOException e) {
            System.out.println("Cannot listen on port: " + this.getPortNumber() + ", Exception: " + e);
            System.exit(1);
        }
// Server is now listening for connections or would not get to this point
        while (loop) 
        {
            Socket clientSocket = null;
            try {
                System.out.println("**. Listening for a new connection...");
                clientSocket = serverSocket.accept();
                System.out.println("!! Accepted socket connection from a client: ");
                System.out.println("<- Address: " + clientSocket.getInetAddress().toString());
                System.out.println("<- Port number: " + clientSocket.getPort());
            } catch (IOException e) {
                System.out.println("XX. Connection failed: " + this.getPortNumber() + e);
                loop=false;
                
            }
            new Thread(new ServerConnexionHandler(clientSocket,this.getUser())).start();
            
        }
// Server is no longer listening for client connections - time to shut down.
        try {
            System.out.println("04. -- Closing down the server socket gracefully.");
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("XX. Could not close server socket. " + e.getMessage());
        }
    }
    
}


