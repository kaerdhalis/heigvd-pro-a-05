package desktop;

import java.util.LinkedList;

import common.spells.Quality;
import common.spells.ShieldSpell;
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
	    wizards.add(new Wizard(312, 422));
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
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int a) throws SlickException {
		for(AttackSpell as : attackSpellstoRemove) {
			attackSpells.remove(as);
		}
		attackSpellstoRemove.clear();
		for(ShieldSpell sp : shieldSpellstoRemove) {
			shieldSpells.remove(sp);
		}
		shieldSpellstoRemove.clear();
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
		if (button == 0) {
			Vector v = new Vector(wizards.get(3).getX(), x, wizards.get(3).getY(), y);

			for (Wizard wizard : wizards) {
				if ((!wizard.equals(wizards.get(3))) && wizard.isTarget(wizards.get(3), v
				)) {
					attackSpells.add(new AttackSpell(Quality.PERFECT, MagicType.FIRE, wizards.get(3), wizard));
				}
			}
		}

		if (button == 1) {
			if (wizards.get(1).getShield() == null) {
				shieldSpells.add(new ShieldSpell(Quality.PERFECT, MagicType.FIRE, wizards.get(1)));
			} else {
				shieldSpellstoRemove.add(wizards.get(1).getShield());
				shieldSpells.add(new ShieldSpell(Quality.PERFECT, MagicType.LIGHTNING, wizards.get(1)));
			}
		}
	}

	public void keyPressed(int key, char c){
		if(null != wizards) {
			if (c == 'q') {
				castShield(Quality.PERFECT, MagicType.FIRE, wizards.get(0));
			}
			if (c == 'w') {
				castShield(Quality.PERFECT, MagicType.WATER, wizards.get(1));
			}
			if (c == 'e') {
				castShield(Quality.PERFECT, MagicType.WATER, wizards.get(2));
			}
			if (c == 'r') {
				castShield(Quality.PERFECT, MagicType.LIGHTNING, wizards.get(3));
			}
			if(c == 'a'){
				castAttack(489,245, Quality.PERFECT, MagicType.FIRE, wizards.get(0));
			}
			if(c == 's'){
				castAttack(489,245, Quality.PERFECT, MagicType.EARTH, wizards.get(1));
			}
			if(c == 'd'){
				castAttack(489,245, Quality.PERFECT, MagicType.WATER, wizards.get(2));
			}
			if(c == 'f'){
				castAttack(489,245, Quality.PERFECT, MagicType.LIGHTNING, wizards.get(3));
			}
		}
		if(!(wizards.size()<2)) {
			if (c == 'u') {
				castShield(Quality.PERFECT, MagicType.FIRE, wizards.get(1));
			}
			if (c == 'i') {
				castShield(Quality.PERFECT, MagicType.EARTH, wizards.get(1));
			}
			if (c == 'o') {
				castShield(Quality.PERFECT, MagicType.WATER, wizards.get(1));
			}
			if (c == 'p') {
				castShield(Quality.PERFECT, MagicType.LIGHTNING, wizards.get(1));
			}

			if (c == 'j') {
				castAttack(135, 245, Quality.PERFECT, MagicType.FIRE, wizards.get(1));
			}
			if (c == 'k') {
				castAttack(135, 245, Quality.PERFECT, MagicType.EARTH, wizards.get(1));
			}
			if (c == 'l') {
				castAttack(135, 245, Quality.PERFECT, MagicType.WATER, wizards.get(1));
			}
			if (c == 'é') {
				castAttack(135, 245, Quality.PERFECT, MagicType.LIGHTNING, wizards.get(1));
			}
		}
	}

	private void castShield(Quality quality, MagicType type, Wizard caster){
		if (caster.getShield() == null) {
			shieldSpells.add(new ShieldSpell(quality, type, caster));
		} else {
			shieldSpellstoRemove.add(caster.getShield());
			shieldSpells.add(new ShieldSpell(quality, type, caster));
		}
	}
	
	private void castAttack(int x, int y, Quality qual, MagicType type, Wizard caster){
		Vector v = new Vector(caster.getX(), x, caster.getY(), y);

		for (Wizard target : wizards) {
			if ((!target.equals(caster)) && target.isTarget(caster, v)) {
				attackSpells.add(new AttackSpell(qual, type, caster, target));
			}
		}
	}


}
