package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractInventoryHolder;
import lib.utils.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class InventoryListener extends AbstractListener {
    public InventoryListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getHolder() instanceof AbstractInventoryHolder) {
                ((AbstractInventoryHolder) event.getClickedInventory().getHolder()).click(event);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if (event.getInventory().getHolder() instanceof AbstractInventoryHolder) ((AbstractInventoryHolder) event.getInventory().getHolder()).onDrag(event);
    }


    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if (event.getInventory().getHolder() instanceof AbstractInventoryHolder) {
            ((AbstractInventoryHolder) event.getInventory().getHolder()).close(event);
        }
    }
}
