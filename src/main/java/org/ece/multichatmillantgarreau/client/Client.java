package org.ece.multichatmillantgarreau.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import org.ece.multichatmillantgarreau.client.view.GuiClient;

public class Client {

    private final int portNumber;
    private final InetAddress host;
    private boolean quit = false;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    
    private static final Logger log = Logger.getLogger(Client.class.getName());

    public boolean isQuit() {
        return quit;
    }

    public Client(int portNumber, String host) throws UnknownHostException {
        this.portNumber = portNumber;
        this.host = InetAddress.getByName(host);
    }

    public void start() throws IOException {

        try {

            socket = new Socket(host, portNumber);
            log.log(Level.INFO, "Creating socket to {0} on port {1}", new Object[]{host, portNumber});
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            

//            Thread t4 = new Thread(new Send(out));
//            t4.start();
//            Thread t3 = new Thread(new Received(in,socket));
//            t3.start();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error :{0}", e);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

}
