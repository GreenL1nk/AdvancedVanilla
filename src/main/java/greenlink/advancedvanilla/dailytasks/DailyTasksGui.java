package greenlink.advancedvanilla.dailytasks;

import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class DailyTasksGui extends AbstractInventoryHolder {
    public DailyTasksGui(Component title, int lines, Player requester) {
        super(title, lines, requester);
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
