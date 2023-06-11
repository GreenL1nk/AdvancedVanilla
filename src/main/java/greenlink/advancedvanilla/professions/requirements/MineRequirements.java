package greenlink.advancedvanilla.professions.requirements;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class MineRequirements extends Requirements {
    private final ItemStack itemStack;
    private int currentProgress;

    public MineRequirements(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.currentProgress = 0;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    Component description() {
        return Component.text("123");
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void incrementCurrentProgress() {
        this.currentProgress++;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }
}
