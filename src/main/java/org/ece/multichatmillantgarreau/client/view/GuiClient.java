/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ece.multichatmillantgarreau.client.Client;
import org.ece.multichatmillantgarreau.client.ClientGuiHandler;
import org.ece.multichatmillantgarreau.client.Received;


/*
 * Inspired from
 */
public class GuiClient extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    // will first hold "Username:", later on "Enter message"
    private JLabel label;
    // to hold the Username and later on the messages
    private JTextField tf;

    // for the chat room
    private JTextArea ta;

    // the Client object
    private Client client;
    private JButton send;
    // the default port number
    private int defaultPort;
    private String defaultHost;
    private String name;

    // Constructor connection receiving a socket number
    public GuiClient(int port, String host) {

        super("Gui Client");
        defaultPort = port;
        defaultHost = host;
        boolean con = true;

        try {

            client = new Client(port, host);
            client.start();
            name = "ClientMg";
        } catch (UnknownHostException ex) {
            Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            con = false;
            Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con) {
            // The NorthPanel with:
            JPanel northPanel = new JPanel(new GridLayout(2, 1));
            send = new JButton("send");
            send.addActionListener(this);

            tf = new JTextField();
            tf.setBackground(Color.WHITE);
            northPanel.add(tf);
            northPanel.add(send);
            add(northPanel, BorderLayout.NORTH);

            // The CenterPanel which is the chat room
            ta = new JTextArea("", 80, 80);
            ta.setEditable(false);
            JPanel centerPanel = new JPanel(new GridLayout(1, 1));
            centerPanel.add(new JScrollPane(ta));
            ta.setEditable(false);
            add(centerPanel, BorderLayout.CENTER);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(400, 400);
            setVisible(true);
            tf.requestFocus();
            ClientGuiHandler x = new ClientGuiHandler(this.client.getIn(), this.client.getSocket(), ta);
            x.execute();

        }
    }

    // called by the Client to append text in the TextArea 
    void append(String str) {
        ta.append("\n"+str);
        ta.setCaretPosition(ta.getText().length() - 1);
        this.client.getOut().println(str);
        this.client.getOut().flush();
    }

    /*
     * Button or JTextField clicked
     */
    public void actionPerformed(ActionEvent e) {
    
          
       
        if (e.getSource() == this.send) {
            String text=tf.getText();
             Pattern p = Pattern.compile("(^nick/[a-zA-Z0-9]*)|(^nick/ [a-zA-Z0-9]*)");
           
            Matcher m = p.matcher(text);
            System.out.println(m.matches());
            
            if(m.matches()){
               
                name= text.replace("nick/", "");
                name=name.replaceAll(" ", "");
               tf.setText("");
                ta.append("\nYour name has been changed: "+name);
            }
            else{
                tf.setText("");
                append( name + ": " + text);
            }
           
           

            
        }

    }

}
