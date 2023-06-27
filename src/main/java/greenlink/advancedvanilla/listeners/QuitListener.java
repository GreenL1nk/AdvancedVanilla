package greenlink.advancedvanilla.listeners;

import greenlink.advancedvanilla.PlayerManager;
import lib.utils.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class QuitListener extends AbstractListener {
    public QuitListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerManager.getInstance().saveRpPlayer(event.getPlayer().getUniqueId());
    }
}
