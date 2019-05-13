package server;


import protocol.Protocol;

public class startServer {
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        MultiThreadServer multi = new MultiThreadServer(Protocol.PRESENCE_DEFAULT_PORT, 4);
        multi.serveClients();
    }
}
