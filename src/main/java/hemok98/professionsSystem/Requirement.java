package hemok98.professionsSystem;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Requirement {
    private Requirements action;
    private Material material;
    private String description;
    private int neededAmount;
    private int progress;

    private boolean isComplete;

    public Requirement(Requirements action, Material material, String description, int neededAmount, int progress) {
        this.action = action;
        this.material = material;
        this.description = description;
        this.neededAmount = neededAmount;
        this.progress = progress;
        if (neededAmount == progress) isComplete = true; else isComplete = false;
    }

    public Requirements getAction() {
        return action;
    }

    public Material getMaterial() {
        return material;
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

    public boolean inc(Player player) {
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
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

    public boolean checkEquals(Material checkMaterial) {
        return this.material.equals(checkMaterial) || this.material.equals(Material.getMaterial("deepslate_" + checkMaterial.getKey().getKey()));
    }
}
