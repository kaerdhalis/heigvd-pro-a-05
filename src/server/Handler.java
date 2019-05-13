package server;

import desktop.Game;
import desktop.Server;
import protocol.Protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Handler {

    final static Logger LOG = Logger.getLogger(Handler.class.getName());

    private int listenPort;

    public Handler(int port) {
        this.listenPort = port;
    }

    public void startServer(){
        LOG.info("Starting the SERVER on a new thread...");
        new Thread(new HandlerThread()).start();
    }

    private class HandlerThread implements Runnable{
        @Override
        public void run() {
            DatagramChannel channel;
            ByteBuffer receivingBuffer, sendingBuffer;
            Map<InetAddress, Integer> IDAddresses = new HashMap<>();

            try {
                channel = DatagramChannel.open();
                channel.socket().bind(new InetSocketAddress(listenPort));
                channel.configureBlocking(false);

                receivingBuffer = ByteBuffer.allocate(200);
                sendingBuffer = ByteBuffer.allocate(200);
                receivingBuffer.clear();
                sendingBuffer.clear();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            // Le serveur acceuil les clients
            sendingBuffer.put((byte) 1);
            LOG.log(Level.INFO, "Waiting for a new client on port {0}", listenPort);
            try {
                SocketAddress senderAddress;
                while (IDAddresses.size() != 2) {
                    senderAddress = channel.receive(receivingBuffer);
                    if (senderAddress != null) {
                        int action = Byte.toUnsignedInt(receivingBuffer.get(0));

                        if (action == 1) {
                            System.out.println(senderAddress.toString());
                            IDAddresses.putIfAbsent(((InetSocketAddress) senderAddress).getAddress(), IDAddresses.size());
                            channel.send(sendingBuffer, senderAddress);
                        }
                    }

                    receivingBuffer.clear();
                    receivingBuffer.put(new byte[200]);
                    receivingBuffer.clear();
                }
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }


            // Le serveur fetch les requêtes des clients

            System.out.println("Fetching requests");

            SocketAddress senderAddress = null;
            while (true) {
                try {
                    while ((senderAddress = channel.receive(receivingBuffer)) != null) {
                        InetAddress address = ((InetSocketAddress) senderAddress).getAddress();
                        System.out.println("Recieved message from : " + address + " which is wizard N°" + IDAddresses.get(address));

                        for (int i = 0; i < 10; ++i) {
                            System.out.print((char) receivingBuffer.get(i) + " ");
                        }
                        System.out.println();
                        ((Game)Server.getInstance().getState(1)).parse(IDAddresses.get(address), receivingBuffer.array());

                        receivingBuffer.clear();
                        receivingBuffer.put(new byte[200]);
                        receivingBuffer.clear();
                    }
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }
}
