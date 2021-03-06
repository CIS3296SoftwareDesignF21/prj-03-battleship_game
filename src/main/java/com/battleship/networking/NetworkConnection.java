package com.battleship.networking;

import com.battleship.utils.IpChecker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private final ConnectionThread connectionThread = new ConnectionThread();
    private final Consumer<Serializable> onReceiveCallback;
    public boolean clientConnected = false;
    public ReentrantLock lock = new ReentrantLock();

    /**
     * Constructor of the class
     *
     * @param onReceiveCallback function called when a message is received
     */
    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    /**
     * Starts the connection
     */
    public void startConnection() {
        connectionThread.start();
    }

    /**
     * Sends a serializable object
     *
     * @param data data to write on the ObjectOutputStream
     * @throws Exception failed to send data
     */
    public void send(Serializable data) throws Exception {
        connectionThread.out.writeObject(data);
    }

    /**
     * Close the connection
     *
     * @throws Exception connection failed
     */
    public void closeConnection() throws Exception {
        connectionThread.socket.close();
        if (isServer())
            connectionThread.server.close();
    }

    /**
     * @return true if the class is a server, true otherwise
     */
    protected abstract boolean isServer();

    /**
     * @return a String containing the IP
     */
    protected abstract String getIP();

    /**
     * @return an int specifying the Port
     */
    protected abstract int getPort();

    /**
     * Private class to handle the connection, extends Thread
     */
    private class ConnectionThread extends Thread {

        private Socket socket;
        private ServerSocket server;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private Serializable data;

        private void setFields() throws IOException {
            Socket socket;
            this.server = isServer() ? new ServerSocket(getPort()) : null;
            if (isServer()) {
                // wait for the client to connect
                System.out.println("Waiting for Client to Connect....");
                socket = server.accept();
                onReceiveCallback.accept(new Boolean(true));
            }
            else {
                socket = new Socket(getIP(), getPort());
            }
            this.socket = socket;
            this.out =  new ObjectOutputStream(socket.getOutputStream());
            this.in =   new ObjectInputStream(socket.getInputStream());
            socket.setTcpNoDelay(true);
        }

        @Override
        public void run() {
            try  {
                setFields();
                while (true) {
                    data = (Serializable)in.readObject();
                    onReceiveCallback.accept(data);
                }
            } catch (Exception e) {
                onReceiveCallback.accept("Connection closed." + e.toString() + " Cause " + e.getCause());
            }
        }
    }
}