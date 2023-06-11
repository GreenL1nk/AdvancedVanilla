package greenlink.advancedvanilla.professions;


import org.bukkit.event.Listener;

public abstract class ProfessionBase implements Listener {
    protected final String name;
    private int level;

    public ProfessionBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
