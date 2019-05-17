package client;
import java.util.Scanner;

public class startClient {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Handler client = new Handler();
        String request = "";
        client.connect(args[0], Protocol.PRESENCE_DEFAULT_PORT);

        do{
            request = scanner.nextLine().toUpperCase();
            client.request(request);
        }while(request.compareTo(Protocol.CMD_EXIT) != 0);
        client.disconnect();
    }
}
