package greenlink.advancedvanilla.professions;


import org.bukkit.event.Listener;

public abstract class ProfessionBase {
    private final String name;
    private int level;
    private final Listener listener;

    public ProfessionBase(String name, Listener listener) {
        this.name = name;
        this.listener = listener;
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

    public Listener getListener() {
        return listener;
    }
}
