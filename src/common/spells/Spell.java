package common.spells;

import org.newdawn.slick.Color;

public abstract class Spell {
	protected int x, y;
    private Quality quality;
    private MagicType type;

    public Spell(int x, int y, Quality quality, MagicType type) {
    	this.x = x;
    	this.y = y;
        this.quality = quality;
        this.type = type;
    }

    public void move(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }

    public int computePower(){
        switch (quality){
            case PERFECT:
                return 10;
            case GOOD:
                return 5;
            case OKAY:
                return 3;
            default:
                return 0;
        }
    }

    public Color getColor(){
        switch (type){
            case EARTH:
                return Color.orange;
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

    public Quality getQuality() {
        return quality;
    }

    public MagicType getType() {
        return type;
    }
}
