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

/**
 *
 * @author egarreau
 */
public class Received extends AbstractReceived implements Runnable{

 
    

    private String message = null;
    private JTextArea ta;

    public Received(BufferedReader in, Socket client,JTextArea ta) {
        super(in, client);
        this.ta=ta;
    }

   

    @Override
    public void run() {

        while (true) {

        
            try {
                message = this.getIn().readLine();
                if(message==null){
                    this.getClient().close();
                }
                else{
                    ta.append("\n"+message);
                }
               
            } catch (IOException ex) {
                Logger.getLogger(Received.class.getName()).log(Level.SEVERE, null, ex);
            }

           
        }

    }

}
/*
com.mkyong.core.utils.App
*/