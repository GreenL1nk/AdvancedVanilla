package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.miner.Miner;
import greenlink.advancedvanilla.professions.miner.MinerListener;
import greenlink.advancedvanilla.professions.woodcutter.Woodcutter;
import greenlink.advancedvanilla.professions.woodcutter.WoodcutterListener;
import lib.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Professions {
    MINER(new Miner(""), Utils.getItem(Component.text("Шахтёр").color(NamedTextColor.YELLOW), Material.STONE_PICKAXE), new MinerListener()),
    WOODCUTTER(new Woodcutter(""), Utils.getItem(Component.text("Лесоруб").color(NamedTextColor.YELLOW), Material.STONE_AXE), new WoodcutterListener());

    private final ProfessionBase professionBase;
    private final ItemStack displayItem;
    private final ProfessionBase professionListener;

    Professions(ProfessionBase professionBase, ItemStack displayItem, ProfessionBase professionListener) {
        this.professionBase = professionBase;
        this.displayItem = displayItem;
        this.professionListener = professionListener;
    }

    public ProfessionBase getProfessionBase() {
        return professionBase;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public ProfessionBase getProfessionListener() {
        return professionListener;
    }
}
