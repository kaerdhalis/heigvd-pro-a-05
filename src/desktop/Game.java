package desktop;

import java.util.LinkedList;

import common.spells.Quality;
import common.spells.ShieldSpell;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import common.Wizard;
import common.spells.AttackSpell;
import common.spells.MagicType;
import util.Vector;

public class Game extends BasicGameState {
	private GameContainer container;
	private TiledMap map;
	private LinkedList<Wizard> wizards, wizardsToRemove;
	private LinkedList<AttackSpell> attackSpells, attackSpellstoRemove;
	private LinkedList<ShieldSpell> shieldSpells, shieldSpellstoRemove;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		container = gc;
	    map = new TiledMap("img/tiles/map1.tmx");
	    wizards = new LinkedList<>();
	    wizardsToRemove = new LinkedList<>();
	    attackSpellstoRemove = new LinkedList<>();
	    shieldSpellstoRemove = new LinkedList<>();
	    shieldSpells = new LinkedList<>();
	    attackSpells = new LinkedList<>();
	    wizards.add(new Wizard(135, 245));
	    wizards.add(new Wizard(489, 245));
	    wizards.add(new Wizard(312, 68));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(0, 0);
		for(Wizard wizard : wizards) {
			wizard.render(g);
		}
		for(AttackSpell as : attackSpells) {
			as.render(g);
		}

		for(ShieldSpell sp : shieldSpells) {
			sp.render(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int a) throws SlickException {
		for(AttackSpell as : attackSpellstoRemove) {
			attackSpells.remove(as);
		}
		for(ShieldSpell sp : shieldSpellstoRemove) {
			shieldSpells.remove(sp);
		}
		attackSpellstoRemove.clear();
		for(Wizard wizard : wizardsToRemove) {
			wizards.remove(wizard);
		}
		wizardsToRemove.clear();
		
		for(AttackSpell as : attackSpells) {
			as.move();
			for(Wizard wizard : wizards) {
				if(wizard.checkCollision(as)) {
					wizard.getHit(as);
					attackSpellstoRemove.add(as);
				}
			}
			if(as.isOver())
				attackSpellstoRemove.add(as);
		}
		
		for(ShieldSpell sp : shieldSpells){
			if(sp.isOver()){
				shieldSpellstoRemove.add(sp);
			}
		}
		
		for(Wizard wizard : wizards) {
			if(wizard.isDead())
				wizardsToRemove.add(wizard);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	 public void mouseClicked(int button, int x, int y, int clickCount) {
		 if(button == 0) {
				Vector v = new Vector(wizards.get(0).getX(), x, wizards.get(0).getY(), y);

			for(Wizard wizard : wizards) {
				if((!wizard.equals(wizards.get(0))) && wizard.isTarget(wizards.get(0), v
					)) {
					attackSpells.add(new AttackSpell(Quality.OKAY, MagicType.FIRE, wizards.get(0), wizard));
				}
			}
		 }
		 
		 if(button == 1) {
		 	shieldSpells.add(new ShieldSpell(Quality.PERFECT, MagicType.LIGHTNING, wizards.get(0)));
		 }
	 }

}
