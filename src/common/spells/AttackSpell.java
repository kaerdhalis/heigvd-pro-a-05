package common.spells;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import common.Wizard;
import javafx.util.Pair;

public class AttackSpell extends Spell {
	private Wizard target;
	private int index;
	private int speed = 2;
	private ArrayList<Pair<Integer, Integer>> trajectory;

    public AttackSpell(ElementalOrb orb, Wizard target){
        super(orb);
        this.target = target;
        this.trajectory = computeTrajectory(orb.getX(), orb.getY(), target);
    }
    
    public void move() {
    	if(index < trajectory.size())
    		super.move(trajectory.get(index).getKey(), trajectory.get(index).getValue());
    	index+=speed;
    }
    
    public boolean isOver() {
    	return index >= trajectory.size();
    }
    
    public void render(Graphics g) throws SlickException {
        g.setColor(getColor());
        g.fillOval(x - 8, y - 8, 16, 16);
    }

    public Wizard getTarget(){
        return target;
    }
    
    /**
     * Computes a line using Bresenham's line algorithm
     * 
     */
    private ArrayList<Pair<Integer, Integer>> computeTrajectory(int x , int y , Wizard target) {
    	ArrayList<Pair<Integer, Integer>> temp = new ArrayList<>(); 
     // Bresenham's algorithm

    	int x1 = x,
    		x2 = target.getX(),
    		y1 = y,
    		y2 = target.getY();
    	
        if ((x1 == x2) && (y1 == y2)) {
            temp.add(new Pair<>(x1, y1));
            
        } 
        else {              
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int rozdil = dx - dy;

            int posun_x, posun_y;

            if (x1 < x2) posun_x = 1; else posun_x = -1;
            if (y1 < y2) posun_y = 1; else posun_y = -1;

            while ((x1 != x2) || (y1 != y2)) {  

                int p = 2 * rozdil;

                if (p > -dy) {
                    rozdil = rozdil - dy;
                    x1 = x1 + posun_x;
                }
                if (p < dx) {
                    rozdil = rozdil + dx;
                    y1 = y1 + posun_y;
                }
                temp.add(new Pair<Integer, Integer>(x1, y1)); 
            }
        }
        
    	return(temp);
    }
}
