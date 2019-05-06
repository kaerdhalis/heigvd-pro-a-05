package common.spells;

import common.Wizard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ShieldSpell extends Spell {

    private Wizard caster;
    private int frameCounter = computePower()*250;
    private boolean over = false;

    public ShieldSpell(Quality quality, MagicType type, Wizard caster){
        super(caster.getX(), caster.getY(), quality, type);
        caster.setShield(this);
        this.caster = caster;
    }

    public void render(Graphics g) throws SlickException {
        g.setColor(getColor());
        g.fillOval(x - 16, y - 16, 32, 32);
        frameCounter--;
        if(caster.getShield() != null && frameCounter == 0){
            caster.setShield(null);
            over = true;
        }
    }

    public boolean isOver(){
        return over;
    }

    public void setOver(){
        over = true;
    }

}
