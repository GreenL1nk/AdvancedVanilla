package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.requirements.Requirement;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class ProfessionsGUI extends AbstractInventoryHolder {
    ProfessionBase professionBase;
    RpPlayer rpPlayer;

    public ProfessionsGUI(Player requester, RpPlayer rpPlayer) {
        super(Component.text(""), 6, requester);
        this.rpPlayer = rpPlayer;
        this.professionBase = rpPlayer.getProfession();
        this.init();
    }

    @Override
    protected void init() {

        setRequirements();
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int rawSlot = event.getRawSlot();
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player requester, RpPlayer rpPlayer) {
        ProfessionsGUI professionsGUI = new ProfessionsGUI(requester, rpPlayer);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(), professionsGUI::open, 1);
    }

    private void setRequirements() {
        int levelIndex = 9;
        for (Level level : professionBase.getLevels()) {
            this.inventory.setItem(levelIndex, level.displayItem());
            int requirementIndex = 1;
            for (Requirement requirement : level.requirements()) {
                this.inventory.setItem(requirementIndex + levelIndex, requirement.getDisplayItem());
                requirementIndex++;
            }
            levelIndex += 9;
        }
    }
}
