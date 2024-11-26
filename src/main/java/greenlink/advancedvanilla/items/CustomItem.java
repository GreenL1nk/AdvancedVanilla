package greenlink.advancedvanilla.items;

import greenlink.advancedvanilla.items.coins.CopperCoin;
import lib.utils.AbstractItem;
import lib.utils.ItemChanger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum CustomItem {

    COPPER_COIN(new CopperCoin(ItemChanger.changeName(Material.POPPED_CHORUS_FRUIT, "Медная монета", "#B87733"), 1)),
    SILVER_COIN(new CopperCoin(ItemChanger.changeName(Material.POPPED_CHORUS_FRUIT, "Серебряная монета", "#C0C0C0"), 2)),
    GOLD_COIN(new CopperCoin(ItemChanger.changeName(Material.POPPED_CHORUS_FRUIT, "Золотая монета", "#FFD700"), 3)),

    ;

    private final AbstractItem abstractItem;

    CustomItem(AbstractItem abstractItem) {
        this.abstractItem = abstractItem;
    }

    private void create() {

    }

    public static void init() {
        for (CustomItem item : CustomItem.values()) {
            item.create();
        }
    }

    public AbstractItem getAbstractItem() {
        return abstractItem;
    }

    @Nullable
    public static CustomItem getItemByStack(ItemStack itemStack) {
        return Arrays.stream(CustomItem.values())
                .filter(item -> item.getAbstractItem().getItemMeta().equals(itemStack.getItemMeta()))
                .findFirst().orElse(null);
    }
}
