package lib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemChanger {
    public static ItemStack changeName(ItemStack item, String str){
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.displayName(Component.text(str).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(itemMeta);
        }

        return item;
    }
}
