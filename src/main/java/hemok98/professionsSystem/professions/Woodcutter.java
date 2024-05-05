package hemok98.professionsSystem.professions;

import hemok98.professionsSystem.Requirement;
import hemok98.professionsSystem.Requirements;
import org.bukkit.Material;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class Woodcutter extends Profession {
    public Woodcutter(boolean isFrozen, Material material, String name){
        this.name = name;
        this.displayedItem = material;
        this.isFrozen = isFrozen;
        this.level = 0;
        requirements = new ArrayList<>();

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.OAK_LOG, "", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.SPRUCE_LOG, "", 120, 0)
        ));

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.COAL_ORE, "", 200, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "", 150, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "", 60, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.GOLD_ORE, "", 90, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.LAPIS_ORE, "", 100, 0)
        ));

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.COAL_ORE, "", 200, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "", 150, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "", 60, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.GOLD_ORE, "", 90, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.LAPIS_ORE, "", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.REDSTONE_ORE, "", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.DIAMOND_ORE, "", 100, 0)
        ));

        requirements.add(List.of(
                new Requirement(Requirements.BREAK_BLOCK, Material.COAL_ORE, "", 100, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.IRON_ORE, "", 120, 0),
                new Requirement(Requirements.BREAK_BLOCK, Material.COPPER_ORE, "", 40, 0)
        ));
    }

    @Override
    public void action(Event event) {

    }
}
