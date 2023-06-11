package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.miner.Miner;
import greenlink.advancedvanilla.professions.miner.MinerListener;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ProfessionFactory {
    public static Miner createMiner() {
        return new Miner("Шахтёр", new MinerListener());
    }

    public static List<Listener> professionListeners() {
        List<Listener> listeners = new ArrayList<>();
        listeners.add(createMiner().getListener());
        return listeners;
    }
}