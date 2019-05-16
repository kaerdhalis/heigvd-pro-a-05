/*package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler {
    final static Logger LOG = Logger.getLogger(Handler.class.getName());

    Socket clientSocket;
    BufferedReader input;
    PrintWriter output;
    boolean connected = false;
    String in;
    String numClient;

    class NotificationListener implements Runnable {

        public void run() {
            String notification;
            try {
                while ((connected && (notification = input.readLine()) != null)) {
                    LOG.log(Level.INFO, "Server notification for {0}", new Object[]{notification});
                }
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Connection problem in client used by {0}", new Object[]{e.getMessage()});
                connected = false;
            } finally {
                cleanup();
            }
        }
    }

    public void connect(String serverAddress, int serverPort) {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream());
            connected = true;
            this.in = in;
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
            cleanup();
            return;
        }

        new Thread(new NotificationListener()).start();

    }

    public void request(String in){
        output.println(in);
        output.flush();
    }

    public void listen(){
        try{
            if(numClient == null){
                numClient = input.readLine();
                LOG.log(Level.INFO, "num client {0}", numClient);
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public String getIdClient(){
        return numClient.toString();
    }




    public void disconnect() {
        LOG.log(Level.INFO, "Client requested to be disconnected", in);
        connected = false;
        cleanup();
    }

    private void cleanup() {

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (output != null) {
            output.close();
        }

        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


}
*/