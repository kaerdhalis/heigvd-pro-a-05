package common.spells;

import javafx.util.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ShieldSpell extends Spell {

    private ElementalOrb orb;
    private int frameCounter = computePower()*25;
    private boolean over = false;
    private static int counter = -1;
    private int radius;

    public ShieldSpell(ElementalOrb orb){
        super(orb);
        counter+=4;
        radius = counter*4;
        this.orb = orb;
        orb.getCaster().addShield(this);
    }

    private Pair<Integer, Integer> getArcBounds(){
        switch (orb.getCaster().getId()){
            case 0:
                return new Pair<>(315, 45);
            case 1:
                return new Pair<>(135, 225);
            default:
                return new Pair<>(0,10);
        }
    }

    public void render(Graphics g) throws SlickException {
        g.setColor(getColor());
        g.setLineWidth(4);
        //g.drawOval(orb.getCaster().getX() - 18 - radius/2 - counter, orb.getCaster().getY() - 18 - radius/2 - counter, 36 + radius + counter*2, 36 + radius + counter*2 );
        g.drawArc(orb.getCaster().getX() - 18 - radius/2 - counter, orb.getCaster().getY() - 18 - radius/2 - counter,   36 + radius + counter*2, 36 + radius + counter*2, getArcBounds().getKey() , getArcBounds().getValue());
        frameCounter--;
        if(!orb.getCaster().getShield().isEmpty() && frameCounter == 0){
            orb.getCaster().getShield().clear();
            over = true;
        }
    }

    public boolean isOver(){
        counter = -1;
        return over;
    }

    public void setOver(){
        over = true;
    }

}
