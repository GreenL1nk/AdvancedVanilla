package greenlink.advancedvanilla.listeners;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.ProfessionSelectGUI;
import greenlink.advancedvanilla.professions.requirements.Requirement;
import lib.utils.AbstractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ConnectionListener extends AbstractListener {
    private int pluginVersion = 0;

    public ConnectionListener(@NotNull JavaPlugin plugin) {
        super(plugin);
        try { pluginVersion+= Integer.parseInt(AdvancedVanilla.GREENLINK_BUILD_NUMBER);} catch (Exception e) {}
        try { pluginVersion+= Integer.parseInt(AdvancedVanilla.HEMOK98_BUILD_NUMBER);} catch (Exception e) {}

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.sendPlayerListHeader( Component.text("Advanced Vanila ").color(TextColor.color(40091))
                    .append( Component.text( AdvancedVanilla.VERSION_NUMBER + " " ).color(TextColor.color(3193888) ) )
                    .append( Component.text(pluginVersion + " build").color(TextColor.color(5889190) ) ).append( Component.text("\n")  )
            );
        }
        int i = 12121;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        event.getPlayer().sendPlayerListHeader(
                Component.text("Advanced Vanila ").color(TextColor.color(40091))
                        .append( Component.text( AdvancedVanilla.VERSION_NUMBER + " " ).color(TextColor.color(3193888) ) )
                        .append( Component.text(pluginVersion + " build").color(TextColor.color(5889190) )).append( Component.text("\n")  )
        );
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
