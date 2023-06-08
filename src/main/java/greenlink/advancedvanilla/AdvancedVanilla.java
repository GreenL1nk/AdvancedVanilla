package greenlink.advancedvanilla;

import greenlink.advancedvanilla.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedVanilla extends JavaPlugin {

    private static AdvancedVanilla instance;

    @Override
    public void onEnable() {

        instance = this;

        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static AdvancedVanilla getInstance() {
        return instance;
    }
}
