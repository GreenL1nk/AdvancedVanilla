package greenlink.advancedvanilla.professions;


import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.requirements.Requirement;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

public abstract class ProfessionBase implements Listener {
    private final String name;
    private int currentLevel;
    private final Level[] levels;
    private RpPlayer rpPlayer;

    public ProfessionBase(String name, Level ... levels) {
        this.name = name;
        this.levels = levels;
        this.rpPlayer = null;
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

    public Requirement[] getCurrentRequirements() {
        if (levels.length < currentLevel) return new Requirement[0];
        return levels[currentLevel].requirements();
    }

    @Nullable
    public RpPlayer getRpPlayer() {
        return rpPlayer;
    }

    public void setRpPlayer(RpPlayer rpPlayer) {
        this.rpPlayer = rpPlayer;
    }
}
