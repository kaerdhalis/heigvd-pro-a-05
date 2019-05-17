package server;

public class startServer {
    public static void main(String[] args){
        Handler connect = new Handler(Protocol.PRESENCE_DEFAULT_PORT);
        connect.startConnection();
    }
}
