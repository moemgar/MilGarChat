/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


/**
 *
 * @author egarreau
 */
public class ClientGuiHandler extends SwingWorker {
 private  BufferedReader in;
    private  Socket client;
    private JTextArea ta;

    public ClientGuiHandler(BufferedReader in, Socket client, JTextArea ta) {
        this.in = in;
        this.client = client;
        this.ta = ta;
    }
    @Override
    protected Object doInBackground() throws Exception {
       
        while (true) {

        
            try {
                String message = in.readLine();
                if(message==null){
                    client.close();
                }
                else{
                    ta.append("\n"+message);
                }
               
            } catch (IOException ex) {
                Logger.getLogger(ClientGuiHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

           
        }
    }
    
}
