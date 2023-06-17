package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.fisherman.Fisherman;
import greenlink.advancedvanilla.professions.miner.Miner;
import lib.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Professions {
    MINER(new Miner(""), Utils.getItem(Component.text("Шахтёр").color(NamedTextColor.YELLOW), Material.STONE_PICKAXE)),
    FISHERMAN(new Fisherman(""), Utils.getItem(Component.text("Рыбак").color(NamedTextColor.YELLOW), Material.FISHING_ROD));

    private final ProfessionBase professionBase;
    private final ItemStack displayItem;

    Professions(ProfessionBase professionBase, ItemStack displayItem) {
        this.professionBase = professionBase;
        this.displayItem = displayItem;
    }

    public ProfessionBase getProfessionBase() {
        return professionBase;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }
}
