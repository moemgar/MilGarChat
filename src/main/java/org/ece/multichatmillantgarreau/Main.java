/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.ece.multichatmillantgarreau.client.view.GuiClient;
import org.ece.multichatmillantgarreau.server.serverchannel.ServerNIO;
import org.ece.multichatmillantgarreau.server.servermt.MultiThreadedServer;


/**
 *
 * @author egarreau
 */
public class Main {

    public static int NIO = 0;
    public static int CLIENT = 1;
    public static int MTS = 2;

    public static void main(String[] args) {
        
        int port = 8080;
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        LongOpt[] longopts = new LongOpt[6];
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        longopts[0] = new LongOpt("address", LongOpt.REQUIRED_ARGUMENT, sb, 'a');
        longopts[1] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
        longopts[2] = new LongOpt("nio", LongOpt.NO_ARGUMENT, null, 'n');
        longopts[3] = new LongOpt("port", LongOpt.REQUIRED_ARGUMENT, sb2, 'p');
        longopts[4] = new LongOpt("server", LongOpt.NO_ARGUMENT, null, 's');
        longopts[5] = new LongOpt("client", LongOpt.NO_ARGUMENT, null, 'c');
        Getopt g = new Getopt("MillantGarreau", args, "a:hbnp:cs", longopts);
        int c;
        int server = 2;
        boolean client = false;
        while ((c = g.getopt()) != -1) {
            switch (c) {
                case 'a':
                    address = g.getOptarg();
                    break;
                case 'h':
                    System.out.println("Usage: java -jar target/multichat -0.0.1"
                            + "-SNAPSHOT.jar [OPTION]...\n"
                            + "-a, --address set the IP address\n"
                            + "-h, --help display this help and quit\n"
                            + "-n, --nio use NIOs for the server\n"
                            + "-p, --port=PORT set the port\n"
                            + "-s, --server start the server\n"
                    + "-c, --client start a client ");
                    break;
                case 'n':
                    server = NIO;
                    break;
                case 'p':
                    port = Integer.parseInt(g.getOptarg());
                    break;
                case 's':
                    if (server == MTS) {
                        new MultiThreadedServer(port, address).start();
                    } else if (server == NIO) {
                        new ServerNIO(port, address).start();
                    }

                    break;
                case 'c':
                    new GuiClient(port,address);
                    
                    
                    break;
                default:
                   System.out.println("Usage: java -jar target/multichat -0.0.1"
                            + "-SNAPSHOT.jar [OPTION]...\n"
                            + "-a, --address set the IP address\n"
                            + "-h, --help display this help and quit\n"
                            + "-n, --nio use NIOs for the server\n"
                            + "-p, --port=PORT set the port\n"
                            + "-s, --server start the server\n"
                    + "-c, --client start a client ");
            }

        }
    }

}
