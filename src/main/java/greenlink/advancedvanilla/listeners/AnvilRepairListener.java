package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class AnvilRepairListener extends AbstractListener {
    public AnvilRepairListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    private static final EnumSet<Material> ANVIL = EnumSet.of(Material.CHIPPED_ANVIL,Material.DAMAGED_ANVIL);
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        if ( event.getHand() != EquipmentSlot.HAND ) return;
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        if ( ANVIL.contains(clickedBlock.getType()) ) {

            ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
            if (!itemInMainHand.getType().equals(Material.IRON_INGOT) || !(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
                return;

            Directional data = (Directional) clickedBlock.getBlockData();
            BlockFace oldFacing = data.getFacing();
            if (clickedBlock.getType().equals(Material.CHIPPED_ANVIL)) clickedBlock.setType(Material.ANVIL);
            if (clickedBlock.getType().equals(Material.DAMAGED_ANVIL)) clickedBlock.setType(Material.CHIPPED_ANVIL);

            Directional blockData = (Directional) clickedBlock.getBlockData();
            blockData.setFacing(oldFacing);

            clickedBlock.setBlockData(blockData);

            int ironLeft = itemInMainHand.getAmount() - 1;
            if (ironLeft == 0) event.getPlayer().getInventory().setItemInMainHand(null);
                else itemInMainHand.setAmount(ironLeft);
            event.setCancelled(true);
        }

    }
}
