package greenlink.advancedvanilla.changelogNoteSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChangelogNotifyListener extends AbstractListener { public ChangelogNotifyListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        int lastChangelogNotify = ChangelogContainer.getLastChangelogNotify(event.getPlayer().getUniqueId());
        if ( ChangelogContainer.getLastChangeNumber() < 0 || ChangelogContainer.getLastChangeNumber() <= lastChangelogNotify ) return;

        //DatabaseConnector.getInstance().getChangelogNotify( eventPlayer );
        //EventsCorePlugin.getInstance().getLogger().info("== " + eventPlayer.getLastChangelogNotify());

        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(), ()->{

            ChangelogContainer.displayLastChanges(event.getPlayer(), lastChangelogNotify);
            ChangelogContainer.setLastChangelogNotify( event.getPlayer().getUniqueId(), ChangelogContainer.getLastChangeNumber() );
        }, 1);
    }
}
