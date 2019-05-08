package desktop;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import common.spells.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import common.Wizard;
import sun.awt.image.ImageWatched;
import util.Vector;

public class Game extends BasicGameState {
	private GameContainer container;
	private TiledMap map;
	private LinkedList<Wizard> wizards, wizardsToRemove;
	private LinkedList<AttackSpell> attackSpells, attackSpellstoRemove;
	private LinkedList<ShieldSpell> shieldSpells, shieldSpellstoRemove;
	private LinkedList<ElementalOrb> elementalOrbs, elementalOrbstoRemove;

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
	    elementalOrbs = new LinkedList<>();
		elementalOrbstoRemove = new LinkedList<>();
	    wizards.add(new Wizard(135, 245));
	    wizards.add(new Wizard(489, 245));
	    //wizards.add(new Wizard(312, 68));
	    //wizards.add(new Wizard(312, 422));
		setId();
	}

	private void setId(){
		int id = 0;
		for(Wizard w : wizards){
			w.setId(id);
			id++;
		}
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
		for(ElementalOrb orb : elementalOrbs){
			orb.render(g);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int a) throws SlickException {
		remove();
		
		for(AttackSpell as : attackSpells) {
			as.move();
			for(Wizard wizard : wizards) {
				if(wizard.checkCollision(as)) {
					wizard.getHit(as);
					attackSpellstoRemove.add(as);
					if(wizard.getOrbs().size() != 0) {
						for (int i = 0; i < wizard.getOrbs().size(); i++) {
							elementalOrbstoRemove.add(wizard.getOrbs().get(i));
						}
						wizard.getOrbs().clear();
					}
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

			for(ElementalOrb orb : wizard.getOrbs()){
				orb.move();
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			Vector v = new Vector(100, 0);
			castAttack(v, wizards.get(0));
		}

		if (button == 1) {
			Random r = new Random();
			int i = r.nextInt(4);
			castOrb(wizards.get(0), Quality.PERFECT, MagicType.values()[i]);
		}
	}

	public void keyPressed(int key, char c){
		if(null != wizards) {
			if (c == 'q') {
				Random r = new Random();
				int i = r.nextInt(4);
				castOrb(wizards.get(1), Quality.PERFECT, MagicType.values()[i]);
			}
		}
		if(null != wizards) {
			if (c == 'w') {
				castShield(wizards.get(1));
			}
		}
	}

	private void castShield(Wizard caster){
		if(caster.getOrbs().size() != 0) {
			for (int i = 0; i < caster.getShield().size(); i++) {
				shieldSpellstoRemove.add(caster.getShield().get(i));
				caster.getShield().get(i).setOver();
				caster.getShield().clear();
			}
		}
		LinkedList<ElementalOrb> orbs = caster.getOrbs();
		for(int i = 0; i < orbs.size(); i++) {
			ElementalOrb orb = orbs.get(i);
			shieldSpells.add(new ShieldSpell(orb));
			elementalOrbstoRemove.add(orb);
		}
		orbs.clear();
	}
	
	private void castAttack(Vector v, Wizard caster){
		for (Wizard target : wizards) {
			LinkedList<ElementalOrb> orbs = caster.getOrbs();
			if ((!target.equals(caster)) && target.isTarget(caster, v) && orbs != null) {
				for(int i = 0; i < orbs.size(); i++) {
					ElementalOrb orb = orbs.get(i);
					attackSpells.add(new AttackSpell(orb, target));
					elementalOrbstoRemove.add(orb);
				}
				orbs.clear();
			}
		}
	}

	private void castOrb(Wizard caster, Quality qual, MagicType type){
		ElementalOrb orb = new ElementalOrb(caster, qual, type);
		if(caster.addOrb(orb)) {
			elementalOrbs.add(orb);
		}

	}

	public void parse(int id, byte[] request){
		String requestString = new String(request);
		String[] requestStringSplit = requestString.split(" ");
		String spellType = requestStringSplit[0];
		MagicType element = MagicType.values()[Integer.parseInt(requestStringSplit[1])];
		Quality quality = Quality.values()[Integer.parseInt(requestStringSplit[2])];
		if(spellType.equals("ATT")){
			castAttack(new Vector(Double.parseDouble(requestStringSplit[3]), Double.parseDouble(requestStringSplit[4])), wizards.get(id));
		} else if(spellType.equals("SHI")) {
			castShield(wizards.get(id));
		}
	}

	private void remove(){
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

		for(ElementalOrb orb : elementalOrbstoRemove) {
			elementalOrbs.remove(orb);
		}
		elementalOrbstoRemove.clear();
	}

}
