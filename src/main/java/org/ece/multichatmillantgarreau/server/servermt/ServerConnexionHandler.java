package org.ece.multichatmillantgarreau.server.servermt;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnexionHandler implements Runnable {

    private Socket clientSocket = null; // Client socket object
    private InputStream is = null; // Input stream
    private OutputStream os = null; // Output stream
    private BufferedReader br;
    private PrintWriter pw;
    private HashMap myCon;
    private String name;
// The constructor for the connection handler

    public ServerConnexionHandler(Socket clientSocket, HashMap myCon) {
        this.clientSocket = clientSocket;
        this.myCon = myCon;

    }

    @Override
    public void run() {

        try {

            this.os = clientSocket.getOutputStream();
            pw = new PrintWriter(os, true);

            this.br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.myCon.put(this.clientSocket, this.pw);
            pw.println("** Welcome on the server MillantGarreau **");
            while (readCommand()) {
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnexionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Receive and process incoming string commands from client socket

    private synchronized boolean readCommand() {

        try {
            String str = br.readLine();
            if (str == null) {
                this.endConnexion();
            }
            //pw.println("Hello, " + str);
            //Iteration on the HashMap
            Iterator it = myCon.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                if (pair.getKey() != this.clientSocket) {
                    PrintWriter pwTmp = (PrintWriter) pair.getValue();
                    pwTmp.println(str);
                }

            }

        } catch (IOException ex) {
            System.out.println("02. -- Finished communicating with client:" + clientSocket.getInetAddress().toString());
            return false;
        }
        return true;
    }

    // Close the client socket
    public void endConnexion() { //gracefully close the socket connection
        try {
            this.br.close();
            this.is.close();
            this.pw.close();
            this.os.close();
            //Remove the writer if the con is lost
            this.myCon.remove(this.clientSocket);
            this.clientSocket.close();
            System.out.println("02. -- Finished communicating with client:" + clientSocket.getInetAddress().toString());
        } catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }

}
