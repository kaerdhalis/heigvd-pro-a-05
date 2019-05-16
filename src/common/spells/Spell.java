package common.spells;

import common.Wizard;

public abstract class Spell {
    private Quality quality;
    private MagicType type;
    private boolean over;
    private Wizard caster;

    /**
     * Constructor of a spell, taking an orb
     * @param orb the magical orb used to set the values of the spell
     */
    public Spell(ElementalOrb orb) {
        this.quality = orb.getQuality();
        this.type = orb.getType();
        this.caster = orb.getCaster();
    }

    public boolean isOver(){
        return over;
    }

    public void setOver(boolean b){
        over = b;
    }

    public Wizard getCaster(){
        return caster;
    }

    public void setCaster(Wizard wizard){
        caster = wizard;
    }

    /**
     * Method used to compute the power of the spell depending of it's the quality
     * @return the value of the power of the spell
     */
    public int computePower(){
        return quality.computePower();
    }

    /**
     * Getter of the type
     * @return the type of the spell.
     */
    public MagicType getType() {
        return type;
    }
}
