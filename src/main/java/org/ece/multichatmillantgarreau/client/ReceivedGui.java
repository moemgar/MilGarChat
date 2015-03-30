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
import javafx.scene.control.TextArea;

/**
 *
 * @author egarreau
 */
public class ReceivedGui extends AbstractReceived implements Runnable{
    private TextArea txt;
    
    private Client myClient;
    public ReceivedGui(BufferedReader in, Socket client, TextArea txt) {
        super(in, client);
        this.txt=txt;
    }

    @Override
    public void run() {
         while (true) {

             System.out.println("ds");
            try {
                
                String message = this.getIn().readLine();
                System.out.println("yes");

                System.out.println(message);
                if(message==null){
                    this.getClient().close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Received.class.getName()).log(Level.SEVERE, null, ex);
            }

           
        }
    }
    
}
