import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WizardTest {
    @BeforeAll
    public static void firstWizardHasId0(){
        Wizard w = new Wizard();
        assertEquals(0,w.getId());
    }

    @Test
    public void wizardMakesUniqueId(){
        Wizard w1 = new Wizard();
        Wizard w2 = new Wizard();
        assertNotEquals(w1.getId(), w2.getId());
    }

    @Test
    public void wizardIsCreatedAlive(){
        Wizard w = new Wizard();
        assertFalse(w.isDead());
    }

}
