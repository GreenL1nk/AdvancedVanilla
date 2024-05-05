package hemok98.professionsSystem;

import org.bukkit.Material;

public class Requirement {
    private Requirements action;
    private Material displayedItem;
    private String description;
    private int neededAmount;
    private int progress;

    private boolean isComplete;

    public Requirement(Requirements action, Material displayedItem, String description, int neededAmount, int progress) {
        this.action = action;
        this.displayedItem = displayedItem;
        this.description = description;
        this.neededAmount = neededAmount;
        this.progress = progress;
        if (neededAmount == progress) isComplete = true; else isComplete = false;
    }

    public Requirements getAction() {
        return action;
    }

    public Material getDisplayedItem() {
        return displayedItem;
    }

    public String getDescription() {
        return description;
    }

    public int getNeededAmount() {
        return neededAmount;
    }

    public int getProgress() {
        return progress;
    }

    public boolean inc(){
        if (progress >= neededAmount) return false;
        progress++;
        if (progress == neededAmount) {
            isComplete = true;
            return true;
        }
        return false;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
