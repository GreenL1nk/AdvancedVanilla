package greenlink.advancedvanilla.listeners;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.tradeSystem.SidebarInfoSystem;
import lib.utils.AbstractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ConnectionListener extends AbstractListener {
    private int pluginVersion = 0;

    public ConnectionListener(@NotNull JavaPlugin plugin) {
        super(plugin);
        try { pluginVersion+= Integer.parseInt(AdvancedVanilla.GREENLINK_BUILD_NUMBER);} catch (Exception e) {}
        try { pluginVersion+= Integer.parseInt(AdvancedVanilla.HEMOK98_BUILD_NUMBER);} catch (Exception e) {}

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.sendPlayerListHeader( Component.text("Advanced Vanilla ").color(TextColor.color(40091))
                    .append( Component.text( AdvancedVanilla.VERSION_NUMBER + " " ).color(TextColor.color(3193888) ) )
                    .append( Component.text(pluginVersion + " build").color(TextColor.color(5889190) ) ).append( Component.text("\n")  )
            );
        }
        int i = 12121;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (rpPlayer.isDisplaySidebarInfo()) SidebarInfoSystem.addSidebarToPlayer(event.getPlayer());

        event.getPlayer().sendPlayerListHeader(
                Component.text("Advanced Vanilla ").color(TextColor.color(40091))
                        .append( Component.text( AdvancedVanilla.VERSION_NUMBER + " " ).color(TextColor.color(3193888) ) )
                        .append( Component.text(pluginVersion + " build").color(TextColor.color(5889190))).append( Component.text("\n")  )
        );
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

    }
}
