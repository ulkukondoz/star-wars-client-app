package com.valstro.startwars;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;

public class StartWarsClient {
    private static final Logger LOG = LogManager.getLogger(StartWarsClient.class);
    private final Socket socket;

    public StartWarsClient(String uri) throws URISyntaxException {
        this.socket = IO.socket(uri);
        socket.on(Socket.EVENT_CONNECT, args -> LOG.info("Socket {}, has connected {}", socket.id(), socket.connected()));
        socket.on(Socket.EVENT_DISCONNECT, args -> LOG.info("Socket {}, has disconnected {}", socket.id(), socket.connected()));
    }

    public Socket startConnection() {
        return socket.connect();
    }

    public void stopConnection() {
        socket.close();
    }
}