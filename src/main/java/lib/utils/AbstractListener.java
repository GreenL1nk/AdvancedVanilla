package lib.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class AbstractListener implements Listener {
    public AbstractListener(@NotNull JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
