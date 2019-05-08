package common.spells;

import common.Wizard;
import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class ElementalOrb {
    protected int x, y;
    private Wizard caster;
    private Quality quality;
    private MagicType type;
    private int index;
    private double radius = 30.0;
    private ArrayList<Pair<Integer, Integer>> trajectory;

    /**
     * Constructor of a magic orb, used to build powerfull spell
     * @param caster the caster of the orb
     * @param quality the quality of the cast
     * @param type the type of the spell
     */
    public ElementalOrb(Wizard caster, Quality quality, MagicType type) {
        this.x = caster.getX();
        this.y = caster.getY();
        this.quality = quality;
        this.type = type;
        this.caster = caster;
        this.trajectory = computeTrajectory();
    }

    /**
     * Getter of the caster
     * @return the caster of the orb
     */
    public Wizard getCaster() {
        return caster;
    }

    /**
     * Method used to compute the trajectory of the orb around the caster
     * @return an array of positions representing the trajectory.
     */
    private ArrayList<Pair<Integer, Integer>> computeTrajectory() {
        ArrayList<Pair<Integer,Integer>> result = new ArrayList<>();
        for(double d  = 0.0; d < Math.PI*2; d = d + 0.05) {
            result.add(new Pair<>((int)(Math.cos(d) * radius) + caster.getX(), (int) (Math.sin(d) * radius) + caster.getY()));
        }
        return result;
    }

    /**
     * Method used to render the orb
     * @param g the graphics
     * @throws SlickException in case of emergency.
     */
    public void render(Graphics g) throws SlickException {
        g.setColor(getColor());
        g.fillOval(x - 8, y - 8, 16, 16);
    }

    /**
     * Method used to move the orb on the trajectory.
     */
    public void move() {
        if(index == trajectory.size()){
            index = 0;
        }
        if (index < trajectory.size()){
            this.x = trajectory.get(index).getKey();
            this.y = trajectory.get(index).getValue();
        }
        index++;
    }

    /**
     * Getter of the x coordinate of the orb.
     * @return the x coordinate of the orb.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter of the y coordinate of the orb
     * @return the y coordinate of the orb.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter of the color depending of the type of the orb
     * @return the color of the orb.
     */
    public Color getColor(){
        return type.getColor();
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
}
