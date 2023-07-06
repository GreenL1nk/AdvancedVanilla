package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SecurityListener extends AbstractListener {
    private HashMap<String, Boolean> allowedCommands = new HashMap<>();
    public SecurityListener(@NotNull JavaPlugin plugin) {
        super(plugin);

        allowedCommands.put("book", true);
        allowedCommands.put("compass", true);
        allowedCommands.put("tell", true);
        allowedCommands.put("info", true);
        allowedCommands.put("w", true);
        allowedCommands.put("changelog", true);
        allowedCommands.put("profile", true);
    }

    @EventHandler
    public void fixNotAllowedCommandsUse(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split("\\s+")[0];
        //Bukkit.broadcastMessage(String.valueOf(command));
        if (event.getPlayer().isOp()) return;
        if (allowedCommands.get(command.substring(1) ) == null) event.setCancelled(true);
    }

    @EventHandler
    public void PlayerCommandSend(PlayerCommandSendEvent event){
        if ( event.getPlayer().isOp() ) return;
        event.getCommands().removeIf(s -> allowedCommands.get(s) == null);
    }
}
