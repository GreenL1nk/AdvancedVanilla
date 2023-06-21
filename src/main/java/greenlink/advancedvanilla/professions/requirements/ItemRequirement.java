package greenlink.advancedvanilla.professions.requirements;

import greenlink.advancedvanilla.RpPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ItemRequirement extends Requirement {
    private final ItemStack itemStack;

    public ItemRequirement(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.requiredAmount = itemStack.getAmount();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    /***
     * Does not consider stack size
     * @param arg compare item
     * @return true if the required item, ignoring the amount
     */
    @Override
    public boolean isRequirement(Object arg, RpPlayer rpPlayer) {
        if (arg instanceof ItemStack) {
            if (((ItemStack) arg).isSimilar(itemStack)) {
                incrementCurrentProgress(rpPlayer);
                return true;
            }
        }
        if (arg instanceof Material) {
            if (arg.equals(itemStack.getType())) {
                incrementCurrentProgress(rpPlayer);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Component> loreComponents() {
        ArrayList<Component> lores = new ArrayList<>();
        lores.add(Component.text("У вас ", NamedTextColor.GRAY)
                        .append(Component.text(currentProgress, NamedTextColor.YELLOW))
                        .append(Component.text(" из ", NamedTextColor.GRAY))
                        .append(Component.text(itemStack.getAmount(), NamedTextColor.YELLOW))
                        .decoration(TextDecoration.ITALIC, false));
        return lores;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack displayItem = itemStack.clone();
        int leftAmount = itemStack.getAmount() - currentProgress;
        if (leftAmount == 0) displayItem.setAmount(1);
        else displayItem.setAmount(Math.min(leftAmount, displayItem.getMaxStackSize()));
        displayItem.lore(loreComponents());
        return displayItem;
    }
}
