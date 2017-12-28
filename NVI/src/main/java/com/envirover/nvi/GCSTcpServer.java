/*
 * Envirover confidential
 * 
 *  [2017] Envirover
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains the property of 
 * Envirover and its suppliers, if any.  The intellectual and technical concepts
 * contained herein are proprietary to Envirover and its suppliers and may be 
 * covered by U.S. and Foreign Patents, patents in process, and are protected
 * by trade secret or copyright law.
 * 
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Envirover.
 */

package com.envirover.nvi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.MAVLink.MAVLinkPacket;
import com.envirover.mavlink.MAVLinkChannel;
import com.envirover.mavlink.MAVLinkSocket;

/**
 * MAVLink TCP server that accepts connections from TCP GCS clients.
 * {@link com.envirover.nvi.GCSClientSession} is created for each client connection. 
 *  
 * @author Pavel Bobov
 *
 */
public class GCSTcpServer {

    private final static Logger logger = Logger.getLogger(GCSTcpServer.class);

    private final Integer port;
    private final MAVLinkChannel mtMessageQueue;
    private final ExecutorService threadPool; 
    private ServerSocket serverSocket;
    private Thread listenerThread;

    /**
     * Creates an instance of GCSTcpServer 
     * 
     * @param port TCP port used for MAVLink ground control stations connections 
     * @param mtMessageQueue Mobile-terminated messages queue
     */
    public GCSTcpServer(Integer port, MAVLinkChannel mtMessageQueue) {
        this.port = port;
        this.mtMessageQueue = mtMessageQueue;
        this.threadPool = Executors.newCachedThreadPool();
    }

    /**
     * Starts GCSTcpServer.
     * 
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        listenerThread = new Thread(new ConnectionListener());
        listenerThread.start();
    }

    /**
     * Stops GCSTcpServer.
     * 
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void stop() throws IOException {
        threadPool.shutdownNow();
        listenerThread.interrupt();
        serverSocket.close();
    }

    /**
     * Accepts socket connections. 
     * 
     * @author pavel
     *
     */
    class ConnectionListener implements Runnable {

        @Override
        public void run() {
            while (serverSocket.isBound()) {
                try {
                    Socket socket = serverSocket.accept();

                    MAVLinkSocket clientSocket = new MAVLinkSocket(socket);
                    GCSClientSession session = new GCSClientSession(clientSocket, mtMessageQueue);
                    session.onOpen();

                    threadPool.execute(new SocketListener(clientSocket, session));

                    logger.info(MessageFormat.format("MAVLink client ''{0}'' connected.", socket.getInetAddress()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        /**
         * Reads MAVLink messages from the socket and passes them to GCSClientSession.onMessage(). 
         * 
         * @author pavel
         *
         */
        class SocketListener implements Runnable {

            private final MAVLinkSocket clientSocket;
            private final GCSClientSession session;

            public SocketListener(MAVLinkSocket clientSocket, GCSClientSession session) {
                this.clientSocket = clientSocket;
                this.session = session;
            }

            @Override
            public void run() {
                while (true) {
                    try {
                        MAVLinkPacket packet = clientSocket.receiveMessage();

                        if (packet != null) {
                            session.onMessage(packet);
                        }

                        Thread.sleep(10);
                    } catch (InterruptedException | IOException e) {
                        try {
                            session.onClose();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        logger.info("MAVLink client disconnected.");

                        return;
                    }
                }
            }
        }
    }

}
