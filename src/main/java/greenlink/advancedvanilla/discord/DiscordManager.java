package greenlink.advancedvanilla.discord;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.auth.AuthPlayer;
import greenlink.advancedvanilla.discord.listeners.ButtonClickListener;
import greenlink.advancedvanilla.discord.listeners.CommandsListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.util.Arrays;
import java.util.HashMap;

public class DiscordManager {
    private static DiscordManager instance;
    private final JDA jda;
    private HashMap<String, AuthPlayer> map = new HashMap<>();

    public DiscordManager() {
        jda = JDABuilder.createDefault(AdvancedVanilla.getInstance().getConfig().getString("discord.token"))
                .enableIntents(Arrays.stream(GatewayIntent.values()).toList())
                .build();


        jda.updateCommands().addCommands(
                Commands.slash("link", "Привязка аккаунта")
                        .addOptions(new OptionData(OptionType.STRING, "code", "code", true))
        ).queue();
        jda.addEventListener(
                new CommandsListener(),
                new ButtonClickListener()
        );
    }

    public static DiscordManager getInstance() {
        if (instance == null) instance = new DiscordManager();
        return instance;
    }

    public JDA getJda() {
        return jda;
    }

    public HashMap<String, AuthPlayer> getMap() {
        return map;
    }

    public void sendMessageWithButtons(User user, String ip, long currentTime) {
        PrivateChannel privateChannel = user.openPrivateChannel().complete();
        ActionRow actionRow = ActionRow.of(
                Button.success("loginConfirm", "Подтвердить вход"),
                Button.danger("loginDeny", "Заблокировать ip")
        );

        MessageCreateAction messageAction = privateChannel
                .sendMessageComponents(actionRow)
                .addContent("Попытка входа на сервер с IP: " + ip)
                .addContent("\nВход был: <t:" + (currentTime / 1000) + ":R>");

        try {
            messageAction.queue();
        } catch (InsufficientPermissionException e) {
            System.out.println("Бот не может отправить сообщение в ЛС пользователю из-за недостаточных прав.");
        }
    }
}
