package greenlink.advancedvanilla;

import greenlink.advancedvanilla.comands.CompassCommand;
import greenlink.advancedvanilla.comands.ProfileCommand;
import greenlink.advancedvanilla.compasSystem.CompasListener;
import greenlink.advancedvanilla.listeners.*;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.tradeSystem.TestListener;
import greenlink.advancedvanilla.tradeSystem.VillagerTradingSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedVanilla extends JavaPlugin {

    public static final String HEMOK98_BUILD_NUMBER = "95";
    public static final String GREENLINK_BUILD_NUMBER = "50";
    public static final String VERSION_NUMBER = "0.1.25";
    private static AdvancedVanilla instance;

    @Override
    public void onEnable() {

        instance = this;

        new InventoryListener(this);
        new VillagerTradingSystem(this);
        new ConnectionListener(this);
        new EnchantmentsFixListener(this);
        new OneShulkerBoxFixes(this);
        new FiredArrowsSystem(this);
        new TestListener(this);
        new ProfileCommand().register(this, "profile" );

        new CompasListener(this);
        new CompassCommand().register(this, "compass");

        ProfessionManager.professionListeners().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        CompasListener.clearCompases();
    }

    public static AdvancedVanilla getInstance() {
        return instance;
    }
}
