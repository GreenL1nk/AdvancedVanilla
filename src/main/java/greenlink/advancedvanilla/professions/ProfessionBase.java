package greenlink.advancedvanilla.professions;


import org.bukkit.event.Listener;

public abstract class ProfessionBase implements Listener {
    private final String name;

    public ProfessionBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
