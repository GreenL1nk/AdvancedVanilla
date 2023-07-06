package greenlink.advancedvanilla;

import greenlink.advancedvanilla.comands.CompassCommand;
import greenlink.advancedvanilla.comands.ProfileCommand;
import greenlink.advancedvanilla.compasSystem.CompasListener;
import greenlink.advancedvanilla.discord.DiscordManager;
import greenlink.advancedvanilla.customrecipes.RecipesFixListener;
import greenlink.advancedvanilla.listeners.*;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.tradeSystem.SidebarInfoSystem;
import greenlink.advancedvanilla.tradeSystem.VillagerTradingSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedVanilla extends JavaPlugin {
    public static final String HEMOK98_BUILD_NUMBER = "157";
    public static final String GREENLINK_BUILD_NUMBER = "141";
    public static String VERSION_NUMBER = "";
    private static AdvancedVanilla instance;
    public final boolean discordEnabled = this.getConfig().getBoolean("discord.enable");

    @Override
    public void onEnable() {

        instance = this;
        this.saveDefaultConfig();
        VERSION_NUMBER = this.getDescription().getVersion();

        new InventoryListener(this);
        new VillagerTradingSystem(this);
        new ConnectionListener(this);
        new EnchantmentsFixListener(this);
        new OneShulkerBoxFixes(this);
        new FiredArrowsSystem(this);
        new SidebarInfoSystem(this);
        new QuitListener(this);
        new ProfileCommand().register(this, "profile" );

        new CompasListener(this);
        new CompassCommand().register(this, "compass");

        new RecipesFixListener(this);

        new AnvilRepairListener(this);

        new SecurityListener(this);

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
