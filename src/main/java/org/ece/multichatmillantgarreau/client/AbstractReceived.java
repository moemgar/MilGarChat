/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.client;

import java.io.BufferedReader;
import java.net.Socket;

/**
 *
 * @author egarreau
 */
public abstract class AbstractReceived {

    private final BufferedReader in;
    private  Socket client;
    
    public AbstractReceived(BufferedReader in, Socket client) {
        this.in = in;
        this.client = client;
    }

    public BufferedReader getIn() {
        return in;
    }

    public Socket getClient() {
        return client;
    }
    
    
}
