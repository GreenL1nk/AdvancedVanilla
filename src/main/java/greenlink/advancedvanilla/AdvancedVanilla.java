package greenlink.advancedvanilla;

import greenlink.advancedvanilla.comands.CompassCommand;
import greenlink.advancedvanilla.comands.ProfileCommand;
import greenlink.advancedvanilla.compasSystem.CompasListener;
import greenlink.advancedvanilla.discord.DiscordManager;
import greenlink.advancedvanilla.listeners.*;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.tradeSystem.TestListener;
import greenlink.advancedvanilla.tradeSystem.VillagerTradingSystem;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class AdvancedVanilla extends JavaPlugin {
    public static final String HEMOK98_BUILD_NUMBER = "119";
    public static final String GREENLINK_BUILD_NUMBER = "141";
    public static String VERSION_NUMBER = "";
    private static AdvancedVanilla instance;
    public final boolean discordEnabled = this.getConfig().getBoolean("discord.enable");

    @Override
    public void onEnable() {

        instance = this;
        this.saveDefaultConfig();
        VERSION_NUMBER = this.getDescription().getVersion();

        //TODO Сделать чтобы листенеры регались с помощью AbstractListener
        new InventoryListener(this);
        new VillagerTradingSystem(this);
        new ConnectionListener(this);
        new EnchantmentsFixListener(this);
        new OneShulkerBoxFixes(this);
        new FiredArrowsSystem(this);
        new TestListener(this);
        new QuitListener(this);
        new ProfileCommand().register(this, "profile" );

        new CompasListener(this);
        new CompassCommand().register(this, "compass");

        ProfessionManager.professionListeners().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        CompasListener.clearCompases();

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            PlayerManager.getInstance().saveRpPlayer(onlinePlayer.getUniqueId());
        }
        DatabaseConnector.getInstance().closeConnection();

        DiscordManager.getInstance().getJda().shutdownNow();
    }

    public static AdvancedVanilla getInstance() {
        return instance;
    }
}
