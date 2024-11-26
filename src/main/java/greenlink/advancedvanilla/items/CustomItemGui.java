package greenlink.advancedvanilla.items;

import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class CustomItemGui extends AbstractInventoryHolder {
    public CustomItemGui(Player requester) {
        super(Component.text(" "), 6, requester);
        init();
    }

    @Override
    protected void init() {
        for (CustomItem value : CustomItem.values()) {
            this.inventory.addItem(value.getAbstractItem());
        }
    }

    @Override
    public void click(InventoryClickEvent event) {

    }

    public static void display(Player requester) {
        CustomItemGui menu = new CustomItemGui(requester);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(), menu::open, 1);
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {

    }
}