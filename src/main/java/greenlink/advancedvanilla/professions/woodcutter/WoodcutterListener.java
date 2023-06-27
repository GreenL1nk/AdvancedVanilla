package greenlink.advancedvanilla.professions.woodcutter;

import com.jeff_media.customblockdata.CustomBlockData;
import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;

public class WoodcutterListener extends Woodcutter {

    public WoodcutterListener() {
        super("");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        CustomBlockData customBlockData = new CustomBlockData(event.getBlock(), AdvancedVanilla.getInstance());
        if (customBlockData.has(woodSpaceKey)) {
            customBlockData.remove(woodSpaceKey);
            return;
        }

        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (rpPlayer.getProfession() instanceof Woodcutter) {
            ((Woodcutter) rpPlayer.getProfession()).onBreak(event);
        } else {

        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (Tag.LOGS.getValues().contains(block.getType())) {
            CustomBlockData customBlockData = new CustomBlockData(block, AdvancedVanilla.getInstance());
            customBlockData.set(woodSpaceKey, PersistentDataType.BOOLEAN, true);
        }
    }
}
