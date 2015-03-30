/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ece.multichatmillantgarreau.server.serverchannel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ece.multichatmillantgarreau.server.AbstractMultiChatServer;
import org.ece.multichatmillantgarreau.server.MultiChatServer;


/**
 *
 * @author egarreau
 */
public class ServerNIO extends AbstractMultiChatServer implements MultiChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final Logger log = Logger.getLogger(ServerNIO.class.getName());

    

    public ServerNIO(int portNumber, String host) {
        super(portNumber, host);
    }

    public ServerNIO(int portNumber) throws UnknownHostException {
        super(portNumber, InetAddress.getLocalHost().getHostName());
    }

    @Override
    public void start() {
        this.initServerSocket();
        boolean loop = true;
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        while (loop) {
            try {
                //wait for a connexion or a message to read
                this.selector.select();
                Set<SelectionKey> acceptKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = acceptKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    //accept connexion
                    if (key.isAcceptable()) {
                        this.acceptConnexion(key);

                    }
                    //read data
                    ByteBuffer bbuf = ByteBuffer.allocate(8192);
                    if (key.isReadable()) {
                        int test = ((SocketChannel) key.channel()).read(bbuf);
                        //Check if the channel is still on.
                        if (test == -1) {
                            //Remove user disconnected
                            this.getUser().remove(key.channel());
                            log.log(Level.INFO, "close connexion with SocketChannel on port :{0}", ((SocketChannel)key.channel()).socket().getPort());
                            key.channel().close();
                        } else {
                            Charset charset = Charset.defaultCharset();
                            // switch the buffer from writing mode to reading mode
                            bbuf.flip();
                            CharBuffer cbuf = charset.decode(bbuf);
                            StringBuilder sb= new StringBuilder(cbuf.toString());
                            sb.deleteCharAt(sb.length()-1);
                           
                            System.out.println(sb.toString());
                            
                            this.broadcast(sb.toString(),(SocketChannel)key.channel());
                            
                            bbuf.compact();

                        }

                    }

                    keyIterator.remove();
                }
            } catch (IOException ex) {
                
                log.log(Level.SEVERE, null, ex);
            }

        }
        try {
            selector.close();
            this.serverSocketChannel.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerNIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void broadcast(String text,SocketChannel from) throws IOException{
        log.log(Level.INFO, "broadcast message from {0} :{1}", new Object[]{from, text});
        Iterator it=this.getUser().entrySet().iterator();
        System.out.println("unno1");
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry)it.next();
                SocketChannel s=(SocketChannel) pair.getKey();
                System.out.println("unno2");
                if(s!=from){
                    ByteBuffer bb=ByteBuffer.allocate(8192);
                    bb.put(text.getBytes());
                    bb.flip();
                    s.write(bb);
                }                
                
            }
    }
    private void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
	long nbytes = 0;
	long toWrite = writeBuffer.remaining();

	// loop on the channel.write() call since it will not necessarily
	// write all bytes in one shot
	try {
	    while (nbytes != toWrite) {
		nbytes += channel.write(writeBuffer);
	    }
	}
	catch (ClosedChannelException cce) {
	}
	catch (Exception e) {
	} 
	
	// get ready for another write if needed
	writeBuffer.rewind();
    }

    
    private void acceptConnexion(SelectionKey key) {
        try {
            
            ServerSocketChannel ch = (ServerSocketChannel) key.channel();
            SocketChannel newClient = ch.accept();
            newClient.configureBlocking(false);
            newClient.register(this.selector, SelectionKey.OP_READ);
            
            //put in map
            this.getUser().put(newClient, null);
            log.log(Level.INFO, "New connexion established with SocketChannel on port :{0}", newClient.socket().getPort());
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Connexion failed {0}", ex);
        }

    }

    private void initServerSocket() {
        try {
            //open an non-blocking server socket channel
            //non-blocking: you can receive message in your buffer while your are typing
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            //bind on designated port and host
            serverSocketChannel.socket().bind(new InetSocketAddress(this.getHost(), this.getPortNumber()));
            this.selector = Selector.open();
            log.info("**ServerMillantGarreauNIO is running**");
            log.log(Level.INFO, "- listening on port: {0}", this.getPortNumber());

        } catch (IOException ex) {
            log.log(Level.SEVERE, "error initializing server : {0}", ex);
        }
    }

}
