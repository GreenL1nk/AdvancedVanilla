package greenlink.advancedvanilla.discord.listeners;

import greenlink.advancedvanilla.auth.AuthPlayer;
import greenlink.advancedvanilla.discord.DiscordManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.HashMap;

public class CommandsListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "link" -> {
                String code = event.getOption("code", OptionMapping::getAsString);
                HashMap<String, AuthPlayer> map = DiscordManager.getInstance().getMap();
                if (map.containsKey(code)) {
                    map.get(code).link(event.getUser().getIdLong());
                    map.remove(code);
                    event.reply("Аккаунт успешно привязан, вход на сервер разрешён").queue();
                }
                else {
                    event.reply("Неправильный код").queue();
                }
            }
        }

    }
}
