package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.miner.Miner;
import greenlink.advancedvanilla.professions.miner.MinerListener;
import greenlink.advancedvanilla.professions.requirements.MineRequirements;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ProfessionManager {
    private static ProfessionManager instance;

    public ProfessionManager() {

    }

    @Nullable
    public ProfessionBase getProfession(Professions profession) {
        switch (profession) {
            case MINER -> {
                return new Miner("Шахтёр",
                        new Level(1,
                                new MineRequirements(new ItemStack(Material.STONE, 50)),
                                new MineRequirements( new ItemStack(Material.GOLD_ORE, 25)),
                                new MineRequirements(new ItemStack(Material.DIAMOND_ORE, 30))
                        ));
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