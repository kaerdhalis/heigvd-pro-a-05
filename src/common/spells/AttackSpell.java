package common.spells;

import org.newdawn.slick.Image;
import util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import common.Wizard;

public class AttackSpell extends Spell implements Movable{
    private Vector direction;
	private Wizard target;
	private double speed = 3;
	private double x, y;
	private Image sprite;
	private boolean bounce = false;
    /**
     * Constructor taking an orb as parameter, and a target
     * @param orb the magic orb used to build the spell
     * @param target the target of the spell
     */
    public AttackSpell(ElementalOrb orb, Wizard target){
        super(orb);
        this.target = target;
        this.x = orb.getX();
        this.y = orb.getY();
        direction = new Vector(orb.getX(), target.getX(), orb.getY(), target.getY());
        direction.normalize();
        sprite = getType().getSprite();
    }


    /**
     * Method used to compute the next movement of the spell
     */
    public void move() {
        if(bounce){
            bounce();
            bounce = false;
        }
        direction.normalize();
        this.x += (direction.getX() * speed);
        this.y += (direction.getY() * speed);
    }

    /**
     * Method used to render the attack spell
     * @param g graphics
     * @throws SlickException in case of emergency.
     */
    public void render(Graphics g) throws SlickException {
        g.drawImage(sprite, (int)x - 8, (int)y - 8);
    }

    public void setBounce(boolean b){
        bounce = b;
    }

    public boolean isBounce() {
        return bounce;
    }

    public void bounce(){
        direction = new Vector(this.target.getX(), this.getCaster().getX(), this.target.getY(), this.getCaster().getY());
        Wizard temp = this.target;
        this.target = this.getCaster();
        setCaster(temp);
    }

    /**
     * Getter of the target
     * @return the wizard targeted by the spell.
     */
    public Wizard getTarget(){
        return target;
    }

    /**
     * Getter of the x coordinate
     * @return the x coordinate of the spell's position
     */
    public double getX() {
        return x;
    }

    /**
     * Getter of the y coordinate
     * @return the y coordinate of the spell's position
     */
    public double getY() {
        return y;
    }

}
