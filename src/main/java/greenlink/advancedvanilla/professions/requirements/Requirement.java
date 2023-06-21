package greenlink.advancedvanilla.professions.requirements;

import greenlink.advancedvanilla.RpPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public abstract class Requirement {
    protected int currentProgress;
    protected int requiredAmount;
    protected boolean isFinished = false;

    public Requirement() {
        this.currentProgress = 0;
    }

    public abstract boolean isRequirement(Object arg, RpPlayer rpPlayer);
    @Nullable
    public abstract ArrayList<Component> loreComponents();
    @Nullable
    public abstract ItemStack getDisplayItem();

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    protected void incrementCurrentProgress(RpPlayer rpPlayer) {
        if (currentProgress < requiredAmount) {
            this.currentProgress++;
            if (currentProgress >= requiredAmount) {
                isFinished = true;
                rpPlayer.getProfession().getCurrentLevel().checkLevelUP(rpPlayer);
            }
        }
    }

    public boolean isFinished() {
        return isFinished;
    }
}
