package greenlink.advancedvanilla.compasSystem;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.compasSystem.CompasListener;
import lib.utils.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class CompassCommand extends AbstractCommand {
    @Override
    protected void onPlayerCommand(@NotNull Player player, @NotNull String[] args) {
        if (args.length >= 1 && args[0].equals("stop")) {
            CompasListener.endCompass(player);
            RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
            rpPlayer.setActiveCompass(-1);
        }
    }

    @Override
    protected void onConsoleCommand(@NotNull CommandSender commandSender, @NotNull String[] args) {

    }

    @Override
    protected List<String> onPlayerTab(@NotNull Player player, @NotNull String[] args) {
        return Collections.singletonList("stop");
    }

    @Override
    protected List<String> onConsoleTab(@NotNull CommandSender commandSender, @NotNull String[] args) {
        return null;
    }
}
