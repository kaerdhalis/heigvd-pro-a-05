package common.spells;
public abstract class Spell {
	protected int x, y;
    private int power;
    private MagicType type;

    public Spell(int x, int y, int power, MagicType type) {
    	this.x = x;
    	this.y = y;
        this.power = power;
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
    
    public int getPower() {
        return power;
    }

    public MagicType getType() {
        return type;
    }
}
