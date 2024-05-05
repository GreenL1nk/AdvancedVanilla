package greenlink.advancedvanilla.bankingSystem;

import lib.utils.AbstractListener;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BankingSystem extends AbstractListener {
    public BankingSystem(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onVillagerOpen(PlayerInteractAtEntityEvent event){
        //System.out.println(event.getRightClicked().getClass());
        if ( event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Villager.Profession profession = ((Villager) event.getRightClicked()).getProfession();

            if (profession == Villager.Profession.CARTOGRAPHER ) BankerGui.display(event.getPlayer());

        }

    }
}
