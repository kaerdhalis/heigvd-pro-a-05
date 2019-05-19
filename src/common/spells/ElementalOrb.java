package common.spells;

import common.Wizard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ElementalOrb implements Movable{
    private double x, y;
    private double angle = 0;
    private Wizard caster;
    private Quality quality;
    private MagicType type;
    private double speed = Math.PI / 50;
    private double radius = 30.0;
    private boolean cast = false;
    private boolean prepare = false;
    private Wizard target;
    private Image sprite;

    /**
     * Constructor of a magic orb, used to build powerfull spell
     * @param caster the caster of the orb
     * @param quality the quality of the cast
     * @param type the type of the spell
     */
    public ElementalOrb(Wizard caster, Quality quality, MagicType type){
        this.x = caster.getX();
        this.y = caster.getY() - radius;
        this.quality = quality;
        this.type = type;
        this.caster = caster;
        this.sprite = type.getSprite();
    }

    public void setPrepare(){
        this.prepare = true;
    }

    public boolean isPrepared(){
        return prepare;
    }

    public boolean isCast(){
        return cast;
    }

    /**
     * Getter of the caster
     * @return the caster of the orb
     */
    public Wizard getCaster() {
        return caster;
    }

    /**
     * Method used to render the orb
     * @param g the graphics
     * @throws SlickException in case of emergency.
     */
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x - 8, (int)y - 8);
        /*
        g.setColor(getColor());
        g.fillOval((int)x - 8, (int)y - 8, 16, 16);
        */
    }

    /**
     * Method used to move the orb on the trajectory.
     */
    public void move() {
        this.x = caster.getX() - radius * Math.sin(angle);
        this.y = caster.getY() - radius * Math.cos(angle);
        angle -= speed;
        if(prepare && x-caster.getX() <= 0.01 && y-caster.getY()+radius <= 0.01){
            this.cast = true;
        }
    }

    /**
     * Getter of the x coordinate of the orb.
     * @return the x coordinate of the orb.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter of the y coordinate of the orb
     * @return the y coordinate of the orb.
     */
    public double getY() {
        return y;
    }

    /**
     * Getter of the quality of the orb.
     * @return the quality of the orb
     */
    public Quality getQuality() {
        return quality;
    }

    /**
     * Getter of the type of the orb
     * @return the type of the orb.
     */
    public MagicType getType() {
        return type;
    }

    public void setTarget(Wizard target) {
        this.target = target;
    }

    public Wizard getTarget() {
        return target;
    }
}
