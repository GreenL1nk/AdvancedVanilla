package greenlink.advancedvanilla.changelogNoteSystem;


import lib.utils.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangelogCommand extends AbstractCommand {
    @Override
    protected void onPlayerCommand(@NotNull Player player, @NotNull String[] args) {
        if ( args.length == 0 ){
            ChangelogContainer.displayChangesList( player );
        }

        if (args.length == 1) {

            int number = -1;

            try {
                number = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                number = -1;
            }

            if ( number < 0) {
                return;
            }

            ChangelogContainer.displayChanges(player, number);
        }
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
