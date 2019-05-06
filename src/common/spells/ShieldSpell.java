package common.spells;

import common.Wizard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ShieldSpell extends Spell {

    private int frameCounter = computePower()*250;

    public ShieldSpell(Quality quality, MagicType type, Wizard caster){
        super(caster.getX(), caster.getY(), quality, type);
    }

    public void render(Graphics g) throws SlickException {
        g.setColor(getColor());
        g.fillOval(x - 16, y - 16, 32, 32);
    }

    public boolean isOver(){
        frameCounter--;
        return frameCounter == 0;
    }

}
