package greenlink.advancedvanilla.compasSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CompasListener extends AbstractListener {
    private HashMap<UUID, BossBar> playerCompass = new HashMap<>();
    public CompasListener(@NotNull JavaPlugin plugin) {
        super(plugin);

        Bukkit.getScheduler().runTaskTimer(AdvancedVanilla.getInstance(), () ->{
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                BossBar bossBar = playerCompass.get(onlinePlayer.getUniqueId());
                if (bossBar != null) {

                    Location point = new Location(onlinePlayer.getWorld(), 100.0, 100, 100.0);
                    double x = point.getX() - onlinePlayer.getLocation().getX();
                    double z = point.getZ() - onlinePlayer.getLocation().getZ();

                    int degree = (int)( (-1)*Math.signum(x)*Math.toDegrees(Math.acos(z/Math.sqrt(z*z+x*x))));

                    StringBuilder mainStr = new StringBuilder();

                    int rawYaw = degree - (int)onlinePlayer.getLocation().getYaw();

                    int test = rawYaw;
                    if (Math.abs(rawYaw)>=180) rawYaw =(-1)* ((int) (Math.signum(rawYaw) * 360 - rawYaw));
                    onlinePlayer.sendActionBar(rawYaw + " " + test );
                    rawYaw*=-1;

                    int yaw = Math.abs(rawYaw);

                    for (int i = 0; i < Math.min(yaw/10, 9); i++) {
                        mainStr.append("  ");
                    }
                    mainStr.append("█");
                    for (int i = yaw/10; i < 9; i++) {
                        mainStr.append("  ");
                    }
                    if (rawYaw < 0) {
                        mainStr.insert(0, "                    ║");
                    } else {
                        mainStr.reverse().append("║                    ");
                    }

                    bossBar.setTitle(mainStr.toString());
                } else {

                    bossBar = Bukkit.createBossBar("Compass", BarColor.GREEN, BarStyle.SOLID, BarFlag.DARKEN_SKY);

                    bossBar.setTitle(String.valueOf(onlinePlayer.getLocation().getYaw()));
                    bossBar.addPlayer(onlinePlayer);
                    playerCompass.put(onlinePlayer.getUniqueId(), bossBar);
                }
            }
        }, -1,1);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        playerCompass.remove( event.getPlayer().getUniqueId());
    }
}
