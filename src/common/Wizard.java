package common;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import common.spells.AttackSpell;
import common.spells.ShieldSpell;
import util.Vector;

/**
 * Class representing a wizard.
 */
public class Wizard {
	private int x, y;
    private static int id_generator = 0;
    private int healthPoint = 100;
    private boolean isDead = false;
    private int id;
    private ShieldSpell shield = null;

    /**
     * Constructor, generating a unique id for the wizard.
     */
    public Wizard() {
    	this(0, 0);
    }
    
    public Wizard(int x, int y) {
    	this.x = x;
    	this.y = y;
        this.id = id_generator;
        id_generator++;
    }

    public void castSpell(ShieldSpell spell) {
    	shield = spell;
    }

    public AttackSpell castSpell(AttackSpell spell, Wizard target) {
    	return spell;
    }

    public boolean checkCollision(AttackSpell spell) {
    	double deltaX = spell.getX() - x;
    	double deltaY = spell.getY() - y;
    	
    	return Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= 8;
    }
    
    public void getHit(AttackSpell spell) {
    	if(shield != null) {
    		shield = null;
    	} else {
    		System.out.println("Ouch i took "+ spell.computePower() +" damage pts");
    		healthPoint -= spell.computePower();
    		
    		// Killing blow
    		if (healthPoint <= 0) {
    			System.out.println("Me dead");
    			isDead = true;
    		}
    	}
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    /**
     * Getter of the health points
     * @return the current number of health points
     */
    public int getHealthPoint() {
        return healthPoint;
    }

    /**
     * Setter of the health points
     * @param healthPoint the new value of the health points
     */
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    /**
     * Getter of the boolean is dead
     * @return if the wizard is dead or not
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Setter of the boolean is dead
     * @param dead new value of the boolean is dead.
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Getter of the id
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    public void render(Graphics g) throws SlickException {
        g.setColor(new Color(0, 0, 0));
        g.fillOval(x - 16, y - 16, 32, 32);
    }
    
    public boolean isTarget(Wizard wizard, Vector direction) {	
    	Vector v = new Vector(wizard.x, x, wizard.y, y);
    	
    	return v.contained(new Vector(direction, Math.PI / 6), new Vector(direction, -Math.PI / 6));
    }
}
