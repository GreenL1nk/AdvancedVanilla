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

//    @EventHandler
//    public void onLogin(PlayerLoginEvent event) {
//        if (AdvancedVanilla.getInstance().discordEnabled) {
//            RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
//            AuthPlayer authPlayer = rpPlayer.getAuthPlayer();
//            String hostAddress = event.getRealAddress().getHostAddress();
//            long currentTime = System.currentTimeMillis();
//            if (!authPlayer.isLinked()) {
//
//                Component command = Component.text("/link " + authPlayer.getCode(hostAddress), TextColor.color(3193888));
//
//                Component message = Component.text("Необходимо привязать аккаунт в ", TextColor.color(40091))
//                                .append(Component.text("discord.gg/96UX24vcwX\n", TextColor.color(3193888)))
//                                .append(Component.text("команда ", TextColor.color(40091)))
//                                .append(command)
//                                .append(Component.text("\nкод действует 15 минут", TextColor.color(40091)));
//
//                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
//            }
//            else {
//
//                if (!authPlayer.getAddress().equals(hostAddress)) {
//                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Подтвердите вход в личном сообщении от бота", TextColor.color(40091)));
//
//                    DiscordManager instance = DiscordManager.getInstance();
//                    User userById = instance.getJda().getUserById(authPlayer.getDiscordID());
//                    if (userById == null) return;
//                    instance.sendMessageWithButtons(
//                            userById, hostAddress, currentTime);
//                }
//            }
//
//        }
//    }
}
