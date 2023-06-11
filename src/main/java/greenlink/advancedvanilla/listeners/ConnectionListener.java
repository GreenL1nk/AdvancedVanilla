package greenlink.advancedvanilla.listeners;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.professions.Professions;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void test(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equals("/test") && e.getPlayer().isOp()) {
            boolean b = PlayerManager.getInstance().getPlayer(e.getPlayer().getUniqueId()).setProfession(Professions.MINER);
            Bukkit.broadcastMessage(String.valueOf(b));
        }
        if (e.getMessage().equals("/prof") && e.getPlayer().isOp()) {
            Bukkit.broadcastMessage(String.valueOf(PlayerManager.getInstance().getPlayer(e.getPlayer().getUniqueId()).getProfession().getName()));
        }
    }

}
