package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class OneShulkerBoxFixes extends AbstractListener {

    private static final EnumSet<Material> SHULKERS = EnumSet.of(Material.SHULKER_BOX);
    public OneShulkerBoxFixes(@NotNull JavaPlugin plugin) {
        super(plugin);
        for (Material value : Material.values()) {
            if (value.toString().contains("SHULKER_BOX")) {
                SHULKERS.add(value);
            }
        }
    }

    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event){
        if (!event.getEntity().getType().equals(EntityType.PLAYER)) return;

        if ( !SHULKERS.contains(event.getItem().getItemStack().getType())) return;
        boolean containsShulker = false;

        Player player = (Player) event.getEntity();

        for (ItemStack content : player.getInventory().getStorageContents()) {
            if (content != null) {
                if (SHULKERS.contains(content.getType())) {
                    containsShulker = true;
                    break;
                }
            }
        }

        if (containsShulker) {
            event.setCancelled(true);
            // TODO: 12.06.2023 refactor to component
            player.sendActionBar(ChatColor.RED + "Нельзя носить более 1 шалкера");
        }
    }

    @EventHandler
    public void test(InventoryDragEvent event){
        if ( event.getOldCursor().getType().toString().contains("SHULKER_BOX")) {
            event.setCancelled(true);
            // TODO: 12.06.2023 refactor to component
            ((Player) event.getWhoClicked()).sendActionBar(ChatColor.RED + "Нельзя носить более 1 шалкера");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void shulkerFix(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) {
            return;
        }

        boolean shulkerMove = false;

        if ( event.getClickedInventory().getType().equals(InventoryType.PLAYER) && ( event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PLACE_ONE)  || event.getAction().equals(InventoryAction.PLACE_SOME) )  ) {

            if ( event.getCursor() != null && SHULKERS.contains(event.getCursor().getType())) shulkerMove = true;
        }

        if ( !event.getClickedInventory().getType().equals(InventoryType.PLAYER) && (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) ||event.getAction().equals(InventoryAction.HOTBAR_SWAP) ||event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) ) ) {
            if ( event.getCursor() != null && event.getCurrentItem() != null &&  SHULKERS.contains(event.getCurrentItem().getType()) ) shulkerMove = true;
        }

        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER) && event.getCursor() != null && SHULKERS.contains(event.getCursor().getType())) shulkerMove = true;

        if (! shulkerMove) return;

        boolean containsShulker = false;

        for (ItemStack content : event.getWhoClicked().getInventory().getContents()) {
            if (content != null) {

                if ( SHULKERS.contains(content.getType()) ) {
                    containsShulker = true;
                    break;
                }
            }
        }
        if (containsShulker) {
            event.setCancelled(true);
            // TODO: 12.06.2023 refactor to component
            ((Player) event.getWhoClicked()).sendActionBar(ChatColor.RED + "Нельзя носить более 1 шалкера");
        }

    }

    private final EnumSet<InventoryType> DO_NOT_DROP_INVENTORIES = EnumSet.of(InventoryType.CHEST,InventoryType.FURNACE,InventoryType.BLAST_FURNACE,InventoryType.BARREL,InventoryType.DISPENSER,InventoryType.DROPPER,InventoryType.ENDER_CHEST,InventoryType.HOPPER,InventoryType.SHULKER_BOX,InventoryType.SMOKER);
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){

        boolean containsShulker = false;
        for (ItemStack content : event.getPlayer().getInventory().getContents()) {
            if (content != null) {

                if ( SHULKERS.contains(content.getType()) ) {
                    containsShulker = true;
                    break;
                }
            }
        }

        if ( containsShulker && SHULKERS.contains( event.getPlayer().getItemOnCursor().getType()) ) {
            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), event.getPlayer().getItemOnCursor());
            event.getPlayer().setItemOnCursor(null);
        }

        if ( DO_NOT_DROP_INVENTORIES.contains(event.getInventory().getType()) ) return;

        containsShulker = false;

        for (ItemStack content : event.getPlayer().getInventory().getContents()) {
            if (content != null) {

                if ( SHULKERS.contains(content.getType()) ) {
                    containsShulker = true;
                    break;
                }
            }
        }

        for (ItemStack content : event.getInventory().getContents()) {
            if (content != null) {

                if ( SHULKERS.contains(content.getType()) ) {
                    if ( !containsShulker) {
                        containsShulker = true;
                        continue;
                    }

                    event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(),content);
                    event.getInventory().remove(content);
                }
            }
        }

    }
}
