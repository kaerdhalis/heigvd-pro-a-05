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

    public ElementalOrb(Wizard caster, Quality quality, MagicType type) {
        this.x = caster.getX();
        this.y = caster.getY();
        this.quality = quality;
        this.type = type;
        this.caster = caster;
        this.trajectory = computeTrajectory();
    }

    public Wizard getCaster() {
        return caster;
    }

    private ArrayList<Pair<Integer, Integer>> computeTrajectory() {
        ArrayList<Pair<Integer,Integer>> result = new ArrayList<>();
        for(double d  = 0.0; d < Math.PI*2; d = d + 0.05) {
            result.add(new Pair<>((int)(Math.cos(d) * radius) + caster.getX(), (int) (Math.sin(d) * radius) + caster.getY()));
        }
        return result;
    }

    public void render(Graphics g) throws SlickException {
        g.setColor(getColor());
        g.fillOval(x - 8, y - 8, 16, 16);
    }

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

    public Quality getQuality() {
        return quality;
    }

    public MagicType getType() {
        return type;
    }
}
