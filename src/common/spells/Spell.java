package common.spells;

import org.newdawn.slick.Color;

public abstract class Spell {
	protected int x, y;
    private Quality quality;
    private MagicType type;

    /**
     * Constructor of a spell, taking an orb
     * @param orb the magical orb used to set the values of the spell
     */
    public Spell(ElementalOrb orb) {
        this.x = orb.getX();
        this.y = orb.getY();
        this.quality = orb.getQuality();
        this.type = orb.getType();
    }

    /**
     * Method move used to set the coordinate
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void move(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * Getter of the x coordinate
     * @return the x coordinate of the spell's position
     */
    public int getX() {
    	return x;
    }

    /**
     * Getter of the y coordinate
     * @return the y coordinate of the spell's position
     */
    public int getY() {
    	return y;
    }

    /**
     * Method used to compute the power of the spell depending of it's the quality
     * @return the value of the power of the spell
     */
    public int computePower(){
        return quality.computePower();
    }

    /**
     * Method used to get the color of the spell depending of it's type.
     * @return
     */
    public Color getColor(){
        return type.getColor();
    }

    /**
     * Getter of the type
     * @return the type of the spell.
     */
    public MagicType getType() {
        return type;
    }
}
