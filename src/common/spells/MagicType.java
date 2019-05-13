package common.spells;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum MagicType {
    EARTH,FIRE,LIGHTNING,WATER;

    public Image getSprite() {
        try {
            switch (this) {
                case FIRE:
                    return new Image("img/orb_feu.png");
                case LIGHTNING:
                    return new Image("img/orb_elec.png");
                case WATER:
                    return new Image("img/orb_eau.png");
                case EARTH:
                    return new Image("img/orb_terre.png");
            }
        } catch (
                SlickException e) {
            e.printStackTrace();
        }
        return null;
    }
}
