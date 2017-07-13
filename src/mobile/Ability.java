package mobile;

/**
 * Created by bobvv on 7/10/17.
 */
public enum Ability {
    fight("fight", 16),
    walk("walk", 11);

    Ability(String ability, int frame) {
        this.ability = ability;
        this.frame = frame;
    }
    private String ability;

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    private int frame;

    public String getAbility() {
        return ability;
    }
}
