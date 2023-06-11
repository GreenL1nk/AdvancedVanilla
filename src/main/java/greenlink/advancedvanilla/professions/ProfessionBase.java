package greenlink.advancedvanilla.professions;


import org.bukkit.event.Listener;

public abstract class ProfessionBase implements Listener {
    protected final String name;
    private int currentLevel;
    private final Level[] levels;

    public ProfessionBase(String name, Level[] levels) {
        this.name = name;
        this.levels = levels;
    }

    public String getName() {
        return name;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level[] getLevels() {
        return levels;
    }
}
