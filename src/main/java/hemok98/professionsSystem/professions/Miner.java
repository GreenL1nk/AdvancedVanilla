package hemok98.professionsSystem.professions;

import greenlink.advancedvanilla.listeners.BlockListener;
import hemok98.professionsSystem.Requirement;
import hemok98.professionsSystem.Requirements;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class Miner extends Profession {

    public Miner(boolean isFrozen, Material material, String name){
        this.name = name;
        this.displayedItem = material;
        this.isFrozen = isFrozen;
        this.level = 0;
        requirements = new ArrayList<>();

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.COAL_ORE, "Добыть угольной руды", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "Добыть железной руды", 120, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "Добыть медной руды", 40, 0)
        ));

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.COAL_ORE, "Добыть угольной руды", 200, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "Добыть железной руды", 150, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "Добыть медной руды", 60, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.GOLD_ORE, "Добыть золотой руды", 90, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.LAPIS_ORE, "Добыть лазуритовой руды", 100, 0)
        ));

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "Добыть железной руды", 150, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "Добыть медной руды", 60, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.GOLD_ORE, "Добыть золотой руды", 90, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.LAPIS_ORE, "Добыть лазуритовой руды", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.REDSTONE_ORE, "Добыть редстоуновой руды", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.DIAMOND_ORE, "Добыть алмазной руды", 100, 0)
        ));

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "Добыть железной руды", 150, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "Добыть медной руды", 60, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.GOLD_ORE, "Добыть золотой руды", 90, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.LAPIS_ORE, "Добыть лазуритовой руды", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.REDSTONE_ORE, "Добыть редстоуновой руды", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.DIAMOND_ORE, "Добыть алмазной руды", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.ANCIENT_DEBRIS, "Добыть древние обломки", 100, 0)
        ));


    }


    @Override
    public void action(Event event) {

        if (event instanceof BlockBreakEvent breakEvent) {
            boolean success = true;
            Material material = breakEvent.getBlock().getType();
            switch (level) {
                case 0 -> {
                }
                case 1 -> {
                }
                case 2 -> {
                }
                case 3 -> {
                }
                case 4 -> {
                }
            }

            if (BlockListener.isPlacedBlock(breakEvent.getBlock())) return;
            if (!isFrozen && success && (level < 4) ) {
                for (Requirement requirement : requirements.get(level)) {
                    if (requirement.checkEquals(material)) {
                        if (requirement.inc(breakEvent.getPlayer())) {
                            this.requirementInfo( requirement, breakEvent.getPlayer()  );

                            boolean allComplete = true;
                            for (Requirement req : requirements.get(level)){
                                if (!req.isComplete()) {
                                    allComplete = false;
                                    break;
                                }
                            }

                            if ( allComplete ) {
                                level++;
                                //notify

                            }

                        }

                        break;
                    }
                }
            }
        }

    }
}
