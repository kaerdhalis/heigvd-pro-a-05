import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WizardTest {

    @Test
    public void wizardMakesUniqueId(){
        Wizard w1 = new Wizard();
        Wizard w2 = new Wizard();
        assertNotEquals(w1.getId(), w2.getId());
    }


}
