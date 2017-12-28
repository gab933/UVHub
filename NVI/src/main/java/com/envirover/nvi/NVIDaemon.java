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

import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;

import com.envirover.mavlink.MAVLinkMessageQueue;
import com.envirover.mavlink.MAVLinkShadow;
import com.envirover.rockblock.RockBlockClient;
import com.envirover.rockblock.RockBlockHttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Daemon interface for NVI application.
 */
@SuppressWarnings("restriction")
public class NVIDaemon implements Daemon {
    private final static String DEFAULT_PARAMS_FILE = "default.params";

    private final static Logger logger = Logger.getLogger(NVIDaemon.class);

    private final Config config = Config.getInstance();
    private MAVLinkTcpServer tcpServer = null;
    private HttpServer httpServer = null;
    //private Thread moMsgPumpThread = null;
    //private Thread mtHandlerThread = null;
    private Thread mtMsgPumpThread = null;
    //private Timer  reportStateTimer = null;
    //private TimerTask reportStateTask = null;
    private Server wsServer;

    @Override
    public void destroy() {
        httpServer.removeContext(config.getHttpContext());
    }

    @Override
    public void init(DaemonContext context) throws DaemonInitException, Exception {
        if (!config.init(context.getArguments()))
            throw new DaemonInitException("Invalid configuration.");

        ClassLoader loader = NVIDaemon.class.getClassLoader();
        InputStream params = loader.getResourceAsStream(DEFAULT_PARAMS_FILE);
        if (params != null) {
            MAVLinkShadow.getInstance().loadParams(params);
            params.close();
        } else {
            logger.warn("File 'default.params' with initial parameters values not found.");
        }

        MAVLinkMessageQueue mtMessageQueue = new MAVLinkMessageQueue(config.getQueueSize());
        tcpServer = new MAVLinkTcpServer(config.getMAVLinkPort(), mtMessageQueue);

        MAVLinkMessageQueue moMessageQueue = new MAVLinkMessageQueue(config.getQueueSize());

        MOMessageHandler moHandler = new MOMessageHandler(moMessageQueue);

        httpServer = HttpServer.create(new InetSocketAddress(config.getRockblockPort()), 0);
        httpServer.createContext(config.getHttpContext(), 
                             new RockBlockHttpHandler(moHandler, config.getRockBlockIMEI()));
        httpServer.setExecutor(null);

        // TODO: Broadcast MO messages to all the connected clients.

        RockBlockClient rockblock = new RockBlockClient(config.getRockBlockIMEI(),
                                                        config.getRockBlockUsername(),
                                                        config.getRockBlockPassword(),
                                                        config.getRockBlockURL());

        MTMessagePump mtMsgPump = new MTMessagePump(mtMessageQueue, rockblock);
        mtMsgPumpThread = new Thread(mtMsgPump, "mt-message-pump");

        WSEndpoint.setMTQueue(mtMessageQueue);
        wsServer = new Server("localhost", config.getWSPort(), "/gcs", WSEndpoint.class);
    }

    @Override
    public void start() throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.printf("Starting RockBLOCK HTTP message handler on http://%s:%d%s...",
                          ip, config.getRockblockPort(), config.getHttpContext());
        System.out.println();

        httpServer.start();
        mtMsgPumpThread.start();
        tcpServer.start();
        wsServer.start();

        Thread.sleep(1000);

        logger.info("NVI Ground Control server started.");
    }

    @Override
    public void stop() throws Exception {

        mtMsgPumpThread.interrupt();
        mtMsgPumpThread.join(1000);

        httpServer.stop(0);

        tcpServer.stop();
        wsServer.stop();

        Thread.sleep(1000);

        logger.info("NVI Ground Control server stopped.");
    }

}