package greenlink.advancedvanilla.tradeSystem;

import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class VillagerTradeGui extends AbstractInventoryHolder {
    public VillagerTradeGui(Component title, Player requester) {
        super(title, 6, requester);
    }

    @Override
    protected void init() {

    }

    @Override
    public void click(InventoryClickEvent event) {

    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {

    }
}
