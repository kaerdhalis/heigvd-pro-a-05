package common.spells;

import org.newdawn.slick.Color;

public enum MagicType {
    EARTH,FIRE,LIGHTNING,WATER;

    public Color getColor(){
        switch (this) {
            case EARTH:
                return Color.green;
            case WATER:
                return Color.blue;
            case LIGHTNING:
                return Color.yellow;
            case FIRE:
                return Color.red;
            default:
                return Color.black;
        }
    }
}
