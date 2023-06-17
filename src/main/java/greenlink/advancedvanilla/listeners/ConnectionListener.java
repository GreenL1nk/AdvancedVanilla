package greenlink.advancedvanilla.listeners;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.ProfessionSelectGUI;
import greenlink.advancedvanilla.professions.requirements.Requirement;
import lib.utils.AbstractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ConnectionListener extends AbstractListener {

    public ConnectionListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void test(PlayerCommandPreprocessEvent e) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(e.getPlayer().getUniqueId());
        if (e.getMessage().equals("/test") && e.getPlayer().isOp()) {
            ProfessionSelectGUI.display(e.getPlayer());
        }
        if (e.getMessage().equals("/t") && e.getPlayer().isOp()) {
        }
    }

}
