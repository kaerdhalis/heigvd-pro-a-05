package common;
import common.spells.ElementalOrb;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import common.spells.AttackSpell;
import common.spells.ShieldSpell;
import sun.awt.image.ImageWatched;
import util.Vector;

import java.util.*;

/**
 * Class representing a wizard.
 */
public class Wizard {
	private int x, y;
    private static int id_generator = 0;
    private int healthPoint = 100;
    private boolean isDead = false;
    private int id;
    private LinkedList<ShieldSpell> shield = new LinkedList<>();
    private LinkedList<ElementalOrb> orbs = new LinkedList<>();

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

    public LinkedList<ElementalOrb> getOrbs() {
        return orbs;
    }

    public void addOrb(ElementalOrb orb) {
        this.orbs.add(orb);
    }

    public LinkedList<ShieldSpell> getShield(){
        return shield;
    }

    public void addShield(ShieldSpell spell) {
    	shield.add(spell);
    }

    public boolean checkCollision(AttackSpell spell) {
        if(spell.getTarget() == this) {
            double deltaX = spell.getX() - x;
            double deltaY = spell.getY() - y;
            return Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= 8;
        } else {
            return false;
        }
    	

    }
    
    public void getHit(AttackSpell spell) {
        boolean shielded = false;
        int indexShield = -1;
    	if(!shield.isEmpty()) {
    	    for(int i = 0; i < shield.size(); i++) {
                if (shield.get(i).getType() == spell.getType()) {
                    shielded = true;
                    indexShield = i;
                }
            }
    	    if(shielded) {
                System.out.println("I shielded " + spell.computePower() + " damage with my " + spell.getType().name() + " shield.");
                shield.get(indexShield).setOver();
                shield.remove(indexShield);
            } else {
    	        takeDamage(spell);
            }
    	} else {
           takeDamage(spell);
    	}
    }

    private void takeDamage(AttackSpell spell){
        System.out.println("Ouch i took "+ spell.computePower() +" damage pts");
        healthPoint -= spell.computePower();

        // Killing blow
        if (healthPoint <= 0) {
            System.out.println("Me dead");
            isDead = true;
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
        if(!shield.isEmpty()) {
            for(int i = 0; i < shield.size(); i++) {
                shield.get(i).render(g);
            }
        }
    }
    
    public boolean isTarget(Wizard wizard, Vector direction) {	
    	Vector v = new Vector(wizard.x, x, wizard.y, y);
    	return v.contained(new Vector(direction, Math.PI / 8), new Vector(direction, -Math.PI / 8));
    }
}
