package client;
import protocol.Protocol;
import java.util.Scanner;

public class startClient {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Handler client = new Handler();
        String request = "";
        client.connect("127.0.0.1", Protocol.PRESENCE_DEFAULT_PORT);

        do{
            client.listen();
            //request = scanner.nextLine().toUpperCase();
            request = "FIRE " + "right "+ "good " + client.getIdClient();
            request.toUpperCase();
            client.request(request);
        }while(request.compareTo(Protocol.CMD_EXIT) != 0);
        client.disconnect();
    }
}
