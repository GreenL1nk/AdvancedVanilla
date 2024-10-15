package greenlink.advancedvanilla.listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractListener;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BlockListener extends AbstractListener {

    public static final NamespacedKey placedBlock = new NamespacedKey(AdvancedVanilla.getInstance(), "placedBlock");

    public BlockListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        CustomBlockData blockData = new CustomBlockData(event.getBlock(), AdvancedVanilla.getInstance());
        blockData.set(placedBlock, PersistentDataType.BOOLEAN, true);
    }

    public static boolean isPlacedBlock(Block block) {
        CustomBlockData blockData = new CustomBlockData(block, AdvancedVanilla.getInstance());
        return blockData.getOrDefault(placedBlock, PersistentDataType.BOOLEAN, false);
    }
}
