package common.spells;

import common.Wizard;

public class ShieldSpell extends Spell {

    public ShieldSpell(int power, MagicType type, Wizard caster){
        super(caster.getX(), caster.getY(), power, type);
    }
}
