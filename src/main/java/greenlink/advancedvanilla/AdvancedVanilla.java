package greenlink.advancedvanilla;

import greenlink.advancedvanilla.listeners.*;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.tradeSystem.TestListener;
import greenlink.advancedvanilla.tradeSystem.VillagerTradingSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedVanilla extends JavaPlugin {

    public static final String HEMOK98_BUILD_NUMBER = "50";
    public static final String GREENLINK_BUILD_NUMBER = "50";
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

        ProfessionManager.professionListeners().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {

    }

    public static AdvancedVanilla getInstance() {
        return instance;
    }
}
