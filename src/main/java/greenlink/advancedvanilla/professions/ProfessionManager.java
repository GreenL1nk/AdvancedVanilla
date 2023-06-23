package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.fisherman.Fisherman;
import greenlink.advancedvanilla.professions.miner.Miner;
import greenlink.advancedvanilla.professions.miner.MinerListener;
import greenlink.advancedvanilla.professions.requirements.ItemRequirement;
import greenlink.advancedvanilla.professions.woodcutter.Woodcutter;
import lib.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfessionManager {
    private static ProfessionManager instance;

    public ProfessionManager() {

    }

    @Nullable
    public ProfessionBase getProfession(Professions profession) {
        switch (profession) {
            case WOODCUTTER -> {
                return new Woodcutter("Лесоруб");
            }
            case MINER -> {
                return new Miner("Шахтёр",
                        new Level(1,
                                new ItemStack(Material.IRON_PICKAXE),
                                new ItemRequirement(new ItemStack(Material.STONE, 1920)),
                                new ItemRequirement(new ItemStack(Material.IRON_ORE, 256)),
                                new ItemRequirement(new ItemStack(Material.COAL_ORE, 256))
                        ),
                        new Level(2,
                                new ItemStack(Material.DIAMOND_PICKAXE),
                                new ItemRequirement(new ItemStack(Material.STONE, 2560)),
                                new ItemRequirement(new ItemStack(Material.REDSTONE_ORE, 256)),
                                new ItemRequirement(new ItemStack(Material.GOLD_ORE, 256))
                        ),
                        new Level(3,
                                new ItemStack(Material.QUARTZ),
                                new ItemRequirement(new ItemStack(Material.NETHERRACK, 960)),
                                new ItemRequirement(new ItemStack(Material.NETHER_QUARTZ_ORE, 3200))
                        ),
                        new Level(4,
                                new ItemStack(Material.NETHERITE_PICKAXE),
                                new ItemRequirement(new ItemStack(Material.ANCIENT_DEBRIS, 448)),
                                new ItemRequirement(new ItemStack(Material.NETHER_QUARTZ_ORE, 448)),
                                new ItemRequirement(new ItemStack(Material.IRON_ORE, 448)),
                                new ItemRequirement(new ItemStack(Material.GOLD_ORE, 448)),
                                new ItemRequirement(new ItemStack(Material.COAL_ORE, 256)),
                                new ItemRequirement(new ItemStack(Material.REDSTONE_ORE, 448)),
                                new ItemRequirement(new ItemStack(Material.LAPIS_ORE, 256)),
                                new ItemRequirement(new ItemStack(Material.COPPER_ORE, 256))
                        ));
            }
            case FISHERMAN -> {
                return new Fisherman("Рыбак",
                        new Level(1,
                                new ItemStack(Material.FISHING_ROD),
                                new ItemRequirement(new ItemStack(Material.PUFFERFISH, 20)),
                                new ItemRequirement(new ItemStack(Material.SALMON, 10))
                        ),
                        new Level(2,
                                new ItemStack(Material.FISHING_ROD),
                                new ItemRequirement(new ItemStack(Material.PUFFERFISH, 20)),
                                new ItemRequirement(new ItemStack(Material.SALMON, 10))
                        ));
            }
            default -> {
                return null;
            }
        }
    }

    public static List<Listener> professionListeners() {
        List<Listener> listeners = new ArrayList<>();
        Arrays.stream(Professions.values()).forEach(professions -> listeners.add(professions.getProfessionListener()));
        return listeners;
    }

    public static ProfessionManager getInstance() {
        if (instance == null) instance = new ProfessionManager();
        return instance;
    }
}