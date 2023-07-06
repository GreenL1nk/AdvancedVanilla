package greenlink.advancedvanilla.comands;

import greenlink.advancedvanilla.profileGuis.ProfileGui;
import lib.utils.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProfileCommand extends AbstractCommand {
    @Override
    protected void onPlayerCommand(@NotNull Player player, @NotNull String[] args) {
        ProfileGui.display(player);
    }

    @Override
    protected void onConsoleCommand(@NotNull CommandSender commandSender, @NotNull String[] args) {

    }

    @Override
    protected List<String> onPlayerTab(@NotNull Player player, @NotNull String[] args) {
        return null;
    }

    @Override
    protected List<String> onConsoleTab(@NotNull CommandSender commandSender, @NotNull String[] args) {
        return null;
    }
}
