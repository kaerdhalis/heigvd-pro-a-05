package desktop;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import server.Handler;

public class App {
    public static void main(String[] args) throws SlickException {
        AppGameContainer apgc;
        try {
            Handler handler = new Handler(8384);
            handler.startServer();
            apgc = new AppGameContainer(Server.getInstance(), 640, 480, false);
            apgc.setTargetFrameRate(60);
            apgc.start();


        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
