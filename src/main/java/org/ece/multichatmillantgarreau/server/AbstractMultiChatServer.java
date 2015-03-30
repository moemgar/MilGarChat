/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.server;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author egarreau
 */
public abstract class AbstractMultiChatServer {

    private int portNumber;
    private ServerSocket serverSocket;
    private InetAddress host;
    private int backlog = 50;
    private boolean loop = true;
    private HashMap user;

    public int getPortNumber() {
        return portNumber;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public InetAddress getHost() {
        return host;
    }

    public int getBacklog() {
        return backlog;
    }

    public boolean isLoop() {
        return loop;
    }

    public HashMap getUser() {
        return user;
    }

    public AbstractMultiChatServer(int portNumber, String host) {
        try {
            this.portNumber = portNumber;
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(AbstractMultiChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.user = new HashMap<>();
    }

    public AbstractMultiChatServer(int portNumber, int backlog, String host) {
        try {
            this.portNumber = portNumber;
            this.backlog = backlog;
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(AbstractMultiChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.user = new HashMap<>();
    }

}
