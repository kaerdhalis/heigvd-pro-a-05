import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpellTest {
    @Test
    public void attackSpellsCanHaveFourTypes(){
        AttackSpell fireAttack = new AttackSpell(100, MagicType.FIRE);
        AttackSpell earthAttack = new AttackSpell(100, MagicType.EARTH);
        AttackSpell waterAttack = new AttackSpell(100, MagicType.WATER);
        AttackSpell lightningAttack = new AttackSpell(100, MagicType.LIGHTNING);

        assertEquals(MagicType.FIRE,fireAttack.getType());
        assertEquals(MagicType.EARTH, earthAttack.getType());
        assertEquals(MagicType.WATER, waterAttack.getType());
        assertEquals(MagicType.LIGHTNING,lightningAttack.getType());
    }

    @Test
    public void ShieldSpellsCanHaveFourTypes(){
        ShieldSpell fireShield = new ShieldSpell(100,MagicType.FIRE);
        ShieldSpell earthShield = new ShieldSpell(100,MagicType.EARTH);
        ShieldSpell waterShield = new ShieldSpell(100,MagicType.WATER);
        ShieldSpell lightningShield = new ShieldSpell(100,MagicType.LIGHTNING);

        assertEquals(MagicType.FIRE,fireShield.getType());
        assertEquals(MagicType.EARTH, earthShield.getType());
        assertEquals(MagicType.WATER, waterShield.getType());
        assertEquals(MagicType.LIGHTNING,lightningShield.getType());
    }

}
