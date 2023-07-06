package greenlink.advancedvanilla.profileGuis.dailytasks;

import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class DailyTasksGui extends AbstractInventoryHolder {
    private static Component title = Component.text( "       Ежедневные задания" ).color(TextColor.color(2773694));
    public DailyTasksGui(Player requester) {
        super(title, 5, requester);

        for (int i = 0; i < 9; i++) {

        }

    }

    @Override
    protected void init() {

    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
}
