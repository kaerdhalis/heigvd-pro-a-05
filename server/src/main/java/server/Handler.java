package server;

import server.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Double.NaN;


public class Handler {

    final static Logger LOG = Logger.getLogger(Handler.class.getName());

    int listenPort;

    public Handler(int port) {
        this.listenPort = port;
    }

    public void startConnection(){
        ServerSocket serverSocket;
        Socket clientSocket = null;
        BufferedReader input = null;
        PrintWriter output = null;

        try{
            serverSocket = new ServerSocket(listenPort);
        }catch (IOException ex){
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while(true){
            LOG.log(Level.INFO, "Waiting for a new client on port {0}", listenPort);
            try{
                clientSocket = serverSocket.accept();
                input = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
                output = new PrintWriter(clientSocket.getOutputStream());
                String line;
                boolean shouldRun = true;


                output.println("Connection to the server");
                output.println("This is some command you can use");
                output.println("Spell fire              : FIRE  direction");
                output.println("Spell water             : WATER direction");
                output.println("Spell earth             : EARTH direction");
                output.println("Spell electricity       : ELEC  direction");
                output.println("Quit the application    : EXIT");
                output.flush();

                LOG.info("Reading until client sends EXIT or close the connection...");
                while((shouldRun) && (line = input.readLine()) != null){
                    String[] command = line.split(" ");
                    String operator = command[0].toUpperCase();
                    String direction = "";

                    if(command.length != 2 && operator.compareTo(Protocol.CMD_EXIT) != 0 || operator.compareTo(Protocol.CMD_EXIT) == 0 && command.length != 1){
                        output.println("Error: number of argument is not correct!");
                        output.flush();
                        continue;
                    }

                    direction = command[1];

                    switch(operator){
                        case (Protocol.CMD_FIRE) :
                            output.println("Cast fire spell in direction " + direction);
                            output.flush();
                            break;
                        case (Protocol.CMD_WATER) :
                            output.println("Cast water spell in direction " + direction);
                            output.flush();
                            break;
                        case (Protocol.CMD_EARTH) :
                            output.println("Cast earth spell in direction " + direction);
                            output.flush();
                            break;
                        case (Protocol.CMD_ELECTRICITY) :
                            output.println("Cast electricity spell in direction " + direction);
                            output.flush();
                            break;
                        case (Protocol.CMD_EXIT) :
                            output.println("End of the connection, Good Bye!");
                            shouldRun = false;
                            break;
                        default:
                            output.println("Error: This command doesn't exist!");
                            output.println("This is the valid command : FIRE, WATER, EART, ELEC, EXIT");
                            break;
                    }
                    output.flush();
                }
                LOG.info("Cleaning up resources...");
                clientSocket.close();
                input.close();
                output.close();

            }catch (IOException ex){
                if(input != null){
                    try{
                        input.close();
                    }catch (IOException ex1){
                        LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                    }
                }
                if (output != null){
                    output.close();
                }
                if(clientSocket != null){
                    try{
                        clientSocket.close();
                    }catch (IOException ex1){
                        LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                    }
                }
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
