package greenlink.advancedvanilla.discord.listeners;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class ButtonClickListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch (event.getComponentId()) {
            case ("loginConfirm") -> {
                String content = event.getMessage().getContentDisplay();
                String ip = content.substring(content.indexOf("IP: ") + 4, content.indexOf("\n")).strip();
                Bukkit.getLogger().log(Level.INFO, ip);
                RpPlayer rpPlayer = PlayerManager.getInstance().getRpPlayerFromDiscordID(event.getInteraction().getUser().getIdLong());
                if (rpPlayer != null) {
                    rpPlayer.getAuthPlayer().setAddress(ip);
                    event.reply("Теперь можно зайти на сервер").queue();
                }
                else {
                    event.reply("Ошибка").queue();
                }
            }
            case ("loginDeny") -> {

            }
        }
    }

}
