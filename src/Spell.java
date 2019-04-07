public abstract class Spell {
    private int power;
    private MagicType type;

    public Spell(int power, MagicType type){
        this.power = power;
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public MagicType getType() {
        return type;
    }
}
