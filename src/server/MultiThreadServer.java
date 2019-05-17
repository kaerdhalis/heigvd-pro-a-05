package server;

import desktop.Game;
import desktop.LogIn;
import desktop.Server;

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
            int nbClient = 0;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            while (nbClient < 4) {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOG.log(Level.INFO, "A potential client has arrived.");

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line;
                    if((line = in.readLine()) != null) {
                        if (line.equals("READY")) {
                            LOG.log(Level.INFO, "A new client has arrived. Number of client {0} with ip " + clientSocket.getInetAddress().getHostAddress(), nbClient);

                            new Thread(new ServantWorker(clientSocket, nbClient)).start();
                            nbClient++;
                            LogIn.setNbPlayers(nbClient);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        private class ServantWorker implements Runnable {
            int id;
            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;

            public ServantWorker(Socket clientSocket, int id) {
                this.id = id;
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

                try {
                    LOG.info("Reading until client sends EXIT or closes the connection...");
                    while (shouldRun) {
                        if((line = in.readLine()) != null) {

                            ((Game) Server.getInstance().getState(1)).parse(id, line.getBytes());
                        }
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