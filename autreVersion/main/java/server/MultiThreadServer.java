package server;

import protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MultiThreadServer {

    final static Logger LOG = Logger.getLogger(MultiThreadServer.class.getName());

    int port;
    int maxClients;


    public MultiThreadServer(int port, int maxClients) {
        this.maxClients = maxClients;
        this.port = port;
    }


    public void serveClients() {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }


    private class ReceptionistWorker implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket;
            int nbClient = 1;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            while (nbClient <= maxClients) {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
                    output.println(nbClient);
                    output.flush();
                    LOG.log(Level.INFO, "A new client has arrived. Number of client {0}...", nbClient);

                    new Thread(new ServantWorker(clientSocket)).start();
                    nbClient++;
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        private class ServantWorker implements Runnable {

            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;

            public ServantWorker(Socket clientSocket) {
                try {
                    this.clientSocket = clientSocket;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void run() {
                String line;
                boolean shouldRun = true;


                out.println("Welcome to the Multi-Threaded Server.");
                out.flush();

                try {
                    LOG.info("Reading until client sends EXIT or closes the connection...");
                    while ((shouldRun) && (line = in.readLine()) != null) {
                        String[] command = line.split(" ");
                        String spell = command[0].toUpperCase();
                        String direction = "";
                        String scoreDraw = "";
                        String idClient = "";
                        direction = command[1];
                        scoreDraw = command[2];
                        idClient = command[3];

                        switch(spell){
                            case (Protocol.CMD_FIRE) :
                                out.println("Cast fire spell in direction " + direction + " score " + scoreDraw + " client id " + idClient);
                                out.flush();
                                break;
                            case (Protocol.CMD_WATER) :
                                out.println("Cast water spell in direction " + direction + " score " + scoreDraw + " client id " + idClient);
                                out.flush();
                                break;
                            case (Protocol.CMD_EARTH) :
                                out.println("Cast earth spell in direction " + direction + " score " + scoreDraw + " client id " + idClient);
                                out.flush();
                                break;
                            case (Protocol.CMD_ELECTRICITY) :
                                out.println("Cast electricity spell in direction " + direction + " score " + scoreDraw + " client id " + idClient);
                                out.flush();
                                break;
                            case (Protocol.CMD_EXIT) :
                                out.println("End of the connection, Good Bye!");
                                shouldRun = false;
                                break;
                            default:
                                out.println("Error: This command doesn't exist!");
                                out.println("This is the valid command : FIRE, WATER, EART, ELEC, EXIT");
                                break;
                        }
                        out.println("> " + line.toUpperCase());
                        out.flush();
                    }

                    LOG.info("Cleaning up resources...");
                    clientSocket.close();
                    in.close();
                    out.close();

                } catch (IOException ex) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }
}