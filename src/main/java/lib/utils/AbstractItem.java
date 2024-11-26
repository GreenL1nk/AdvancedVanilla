package lib.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AbstractItem extends ItemStack {

    public AbstractItem(ItemStack item) {
        super(item);
    }

    public AbstractItem(ItemStack item, int data) {
        super(item);
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setCustomModelData(data);
        this.setItemMeta(itemMeta);
    }
}
