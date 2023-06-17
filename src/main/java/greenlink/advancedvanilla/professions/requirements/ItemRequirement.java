package greenlink.advancedvanilla.professions.requirements;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ItemRequirement extends Requirement {
    private final ItemStack itemStack;
    private int currentProgress;

    public ItemRequirement(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.currentProgress = 0;
    }

    public ItemStack getItemStack() {
        return itemStack;
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
        displayItem.setAmount(Math.min(leftAmount, displayItem.getMaxStackSize()));
        displayItem.lore(loreComponents());
        return displayItem;
    }

    /***
     * Does not consider stack size
     * @param checkItem
     * @return true if the required item, ignoring the amount
     */
    public boolean isRequirement(ItemStack checkItem) {
        if (checkItem.isSimilar(itemStack)) {
            incrementCurrentProgress();
            return true;
        }
        return false;
    }

    public boolean isRequirement(Material material) {
        if (material.equals(itemStack.getType())) {
            incrementCurrentProgress();
            return true;
        }
        return false;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    private void incrementCurrentProgress() {
        if (currentProgress < itemStack.getAmount()) {
            this.currentProgress++;
        }
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }
}
