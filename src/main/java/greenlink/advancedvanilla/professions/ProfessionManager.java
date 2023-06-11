package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.miner.Miner;
import greenlink.advancedvanilla.professions.miner.MinerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ProfessionManager {
    private static ProfessionManager instance;

    public ProfessionManager() {

    }

    @Nullable
    public ProfessionBase getProfession(Professions profession) {
        switch (profession) {
            case MINER -> {
                return new Miner("Шахтёр");
            }
            default -> {
                return null;
            }
        }
    }

    public static List<Listener> professionListeners() {
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new MinerListener());
        return listeners;
    }

    public static ProfessionManager getInstance(){
        if (instance == null) instance = new ProfessionManager();
        return instance;
    }
}