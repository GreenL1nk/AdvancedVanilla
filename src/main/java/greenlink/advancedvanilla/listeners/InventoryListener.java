package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractInventoryHolder;
import lib.utils.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


/**
* Класс отлавливающий нажатия игроком в интерфейсах
*/
public class InventoryListener extends AbstractListener {
    public InventoryListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    /**
    * Ловит нажатия игроком по интерфейсам если открыт интерфейс, производный от {@link AbstractInventoryHolder}</p>
     * при отлове нужных нажатий вызвает click у {@link AbstractInventoryHolder}</p>
     * Блокирует перенос любых предметов из наших интерфейсов
    * */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getView().getTopInventory().getHolder() instanceof AbstractInventoryHolder) {
                if (event.getRawSlot() > event.getView().getTopInventory().getSize() - 1) {
                    if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) event.setCancelled(true); // TODO check the used slots
                    ((AbstractInventoryHolder) event.getView().getTopInventory().getHolder()).clickFromPlayerInventory(event);
                }
            }
            if (event.getClickedInventory().getHolder() instanceof AbstractInventoryHolder) {
                ((AbstractInventoryHolder) event.getClickedInventory().getHolder()).click(event);
            }
            if (event.getInventory().getHolder() instanceof AbstractInventoryHolder && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                ((AbstractInventoryHolder) event.getInventory().getHolder()).shiftClickFromPlayerInventory(event);
            }
        }
    }

    /**
     * Блокирует возможность растягивания с делением предметов по интерфейсам от {@link AbstractInventoryHolder}
    * */
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getRawSlots().stream().anyMatch(slot -> slot <= event.getView().getTopInventory().getSize() - 1)) {
            if (event.getInventory().getHolder() instanceof AbstractInventoryHolder)
                ((AbstractInventoryHolder) event.getInventory().getHolder()).onDrag(event);
        }
    }


    /**
     * Отлавливает закрытия интерфейсов созданных на базе {@link AbstractInventoryHolder}
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof AbstractInventoryHolder) {
            ((AbstractInventoryHolder) event.getInventory().getHolder()).close(event);
        }
    }
}
