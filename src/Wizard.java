/**
 * Class representing a wizard.
 */
public class Wizard {
    private static int id_generator = 0;
    private int healthPoint = 100;
    private boolean isDead = false;
    private int id;

    /**
     * Constructor, generating a unique id for the wizard.
     */
    public Wizard(){
        this.id = id_generator;
        id_generator++;
    }

    /**
     * Getter of the health points
     * @return the current number of health points
     */
    public int getHealthPoint() {
        return healthPoint;
    }

    /**
     * Setter of the health points
     * @param healthPoint the new value of the health points
     */
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    /**
     * Getter of the boolean is dead
     * @return if the wizard is dead or not
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Setter of the boolean is dead
     * @param dead new value of the boolean is dead.
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Getter of the id
     * @return the id
     */
    public int getId() {
        return id;
    }
}
