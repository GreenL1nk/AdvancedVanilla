package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.requirements.Requirement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Level {
    private final int levelNumber;
    private final ItemStack displayItem;
    private final Requirement[] requirements;

    public Level(int levelNumber, ItemStack displayItem, Requirement... requirements) {
        this.levelNumber = levelNumber;
        this.displayItem = displayItem;
        this.requirements = requirements;
        updateDisplayItem();
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public Requirement[] requirements() {
        return requirements;
    }

    public void updateDisplayItem() {
        ItemMeta itemMeta = displayItem.getItemMeta();
        itemMeta.displayName( Component.text(levelNumber, NamedTextColor.GREEN)
                .append(Component.text(" уровень", NamedTextColor.YELLOW))
                .decoration(TextDecoration.ITALIC, false));
        displayItem.setItemMeta(itemMeta);
        displayItem.setAmount(levelNumber);
    }
}
