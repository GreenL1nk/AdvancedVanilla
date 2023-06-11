package greenlink.advancedvanilla;

import greenlink.advancedvanilla.listeners.ConnectionListener;
import greenlink.advancedvanilla.listeners.InventoryListener;
import greenlink.advancedvanilla.professions.ProfessionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedVanilla extends JavaPlugin {

    private static AdvancedVanilla instance;

    @Override
    public void onEnable() {

        instance = this;

        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
        ProfessionManager.professionListeners().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));


    }

    @Override
    public void onDisable() {

    }

    public static AdvancedVanilla getInstance() {
        return instance;
    }
}
