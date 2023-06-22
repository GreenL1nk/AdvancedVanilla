package greenlink.advancedvanilla.compasSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import lib.utils.AbstractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

public class CompasListener extends AbstractListener {
    private static HashMap<UUID, BossBar> playerCompass = new HashMap<>();
    public CompasListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());

        BossBar bossBar = playerCompass.get(event.getPlayer().getUniqueId());
        if (bossBar == null) return;
        bossBarDisplay(rpPlayer, player , bossBar);
    }

    private static void bossBarDisplay(RpPlayer rpPlayer, Player player, BossBar bossBar){
        Compass compass = rpPlayer.getCompasses()[rpPlayer.getActiveCompass()];

        double x = compass.getDestinationX() - player.getLocation().getX();
        double z = compass.getDestinationZ() - player.getLocation().getZ();

        int degree = (int)( (-1)*Math.signum(x)*Math.toDegrees(Math.acos(z/Math.sqrt(z*z+x*x))));

        StringBuilder bossBarTitle = new StringBuilder();

        int rawYaw = degree - (int)player.getLocation().getYaw();

        int test = rawYaw;
        if (Math.abs(rawYaw)>=180) rawYaw =(-1)* ((int) (Math.signum(rawYaw) * 360 - rawYaw));
        //player.sendActionBar(rawYaw + " " + test );
        rawYaw*=-1;

        int yaw = Math.abs(rawYaw);

        for (int i = 0; i < Math.min(yaw/10, 9); i++) {
            bossBarTitle.append("  ");
        }
        bossBarTitle.append("█");
        for (int i = yaw/10; i < 9; i++) {
            bossBarTitle.append("  ");
        }
        if (rawYaw < 0) {
            bossBarTitle.insert(0, "                    ║");
        } else {
            bossBarTitle.reverse().append("║                    ");
        }

        bossBar.setTitle(bossBarTitle.toString());
        player.sendActionBar( Component.text("До цели: ").color(TextColor.color(972270)).append(Component.text( String.format("%,01.2f", Math.sqrt( x*x + z*z )) ).color(TextColor.color(9290582))).decoration(TextDecoration.ITALIC, false) );

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if ( !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) return;
        if ( event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ) {
            CompassGui.display(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        endCompass(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        if ( rpPlayer.getActiveCompass() != -1 ){
            startCompass(player);
        }
    }

    public static void startCompass(Player player){
        endCompass(player);
        BossBar bossBar = Bukkit.createBossBar("Compass", BarColor.GREEN, BarStyle.SOLID, BarFlag.DARKEN_SKY);
        playerCompass.put(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
        bossBarDisplay(PlayerManager.getInstance().getPlayer(player.getUniqueId()), player, bossBar);
    }

    public static void endCompass(Player player){
        BossBar bossBar = playerCompass.remove(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
        }
    }

    public static void clearCompases(){
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            BossBar bossBar = playerCompass.remove(player.getUniqueId());
            if (bossBar != null) {
                bossBar.removePlayer(player);
            }
        }
    }

}
