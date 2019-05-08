package desktop;

import java.util.LinkedList;

import common.spells.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import common.Wizard;
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
				String string = "SHI 3 2";
				parse(0, string.getBytes());
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
	
	private void castAttack(Vector v, Quality qual, MagicType type, Wizard caster){
		for (Wizard target : wizards) {
			if ((!target.equals(caster)) && target.isTarget(caster, v)) {
				attackSpells.add(new AttackSpell(qual, type, caster, target));
			}
		}
	}

	public void parse(int id, byte[] request){
		String requestString = new String(request);
		String[] requestStringSplitted = requestString.split(" ");
		String spellType = requestStringSplitted[0];
		MagicType element = MagicType.values()[Integer.parseInt(requestStringSplitted[1])];
		Quality quality = Quality.values()[Integer.parseInt(requestStringSplitted[2])];
		if(spellType.equals("ATT")){
			castAttack(new Vector(Double.parseDouble(requestStringSplitted[3]), Double.parseDouble(requestStringSplitted[4])), quality, element, wizards.get(id));
		} else if(spellType.equals("SHI")) {
			castShield(quality, element, wizards.get(id));
		}
	}


}
