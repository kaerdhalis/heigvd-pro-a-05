package desktop;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import server.MultiThreadServer;

import java.io.File;

public class App {
    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        AppGameContainer apgc;
        try {
            MultiThreadServer server = new MultiThreadServer(8384, 4);
            server.serveClients();
            apgc = new AppGameContainer(Server.getInstance(), 1280, 960, false);
            apgc.setTargetFrameRate(60);
            apgc.start();


        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
