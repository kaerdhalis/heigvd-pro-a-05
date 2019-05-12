package common;
import common.spells.ElementalOrb;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import common.spells.AttackSpell;
import common.spells.ShieldSpell;

import java.util.*;

/**
 * Class representing a wizard.
 */
public class Wizard {
	private int x, y;
    private int healthPoint = 100;
    private boolean isDead = false;
    private int id;
    private LinkedList<ShieldSpell> shield = new LinkedList<>();
    private LinkedList<ElementalOrb> orbs = new LinkedList<>();

    /**
     * Constructor by default;
     */
    public Wizard() {
    	this(0, 0);
    }

    /**
     * Constructor, taking the position of a Wizard.
     * @param x the x coordinate of the wizard
     * @param y the y coordinate of the wizard
     */
    public Wizard(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * Setter of the id
     * @param i the new id of the wizard.
     */
    public void setId(int i){
        this.id = i;
    }

    /**
     * Getter of the orbs
     * @return the orbs
     */
    public LinkedList<ElementalOrb> getOrbs() {
        return orbs;
    }

    /**
     * Method used to add an orb. A Wizard cannot have more than 4 orbs.
     * @param orb the new orb we want to add
     * @return a boolean that indicate if the adding was successfull.
     */
    public boolean addOrb(ElementalOrb orb) {
        int count = 0;
        for(int i = 0; i < orbs.size(); i++){
            if(!orbs.get(i).isPrepared()){
                count++;
            }
        }
        if(count < 4){
            orbs.add(orb);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter of the shields
     * @return the shields the wizard possesses.
     */
    public LinkedList<ShieldSpell> getShield(){
        return shield;
    }

    /**
     * Method used to add a shield to the wizard's shields
     * @param spell the shield we want to add
     */
    public void addShield(ShieldSpell spell) {
    	shield.add(spell);
    }

    /**
     * Method used to check wether a spell has hit the wizard or not
     * @param spell the incoming spell
     * @return a boolean telling if the spell has hit or not.
     */
    public boolean checkCollision(AttackSpell spell) {
        if(spell.getTarget() == this) {
            double deltaX = spell.getX() - x;
            double deltaY = spell.getY() - y;
            if(Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= 8){
                spell.setOver();
                return true;
            } else{
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Method used to check whether the wizard has to take damage or not
     * @param spell the spell that hit the wizard
     */
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

    /**
     * Method used to compute the damage, and check whether the spell hit or not
     * @param spell the incoming spell
     */
    private void takeDamage(AttackSpell spell){
        System.out.println("Ouch i took "+ spell.computePower() +" damage pts");
        healthPoint -= spell.computePower();

        // Killing blow
        if (healthPoint <= 0) {
            System.out.println("Me dead");
            isDead = true;
        }
    }

    /**
     * Getter of the x coordinate of the wizard
     * @return the x coordinate
     */
    public int getX() {
    	return x;
    }

    /**
     * Getter of the y coordinate of the wizard.
     * @return the y coordinate
     */
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

    /**
     * Method used to render a wizard
     * @param g the graphics
     * @throws SlickException in case of emergency.
     */
    public void render(Graphics g) throws SlickException {
        g.setColor(new Color(0, 0, 0));
        g.fillOval(x - 16, y - 16, 32, 32);
        if(!shield.isEmpty()) {
            for(int i = 0; i < shield.size(); i++) {
                shield.get(i).render(g);
            }
        }
    }
}
