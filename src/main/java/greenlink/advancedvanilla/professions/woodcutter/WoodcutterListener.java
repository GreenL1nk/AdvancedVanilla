package greenlink.advancedvanilla.professions.woodcutter;

import com.jeff_media.customblockdata.CustomBlockData;
import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

public class WoodcutterListener extends Woodcutter {

    public WoodcutterListener() {
        super("");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (rpPlayer.getProfession() instanceof Woodcutter) {
            ((Woodcutter) rpPlayer.getProfession()).onBreak(event);
        } else {

        }

        CustomBlockData customBlockData = new CustomBlockData(event.getBlock(), AdvancedVanilla.getInstance());

        Bukkit.broadcastMessage(String.valueOf(customBlockData.has(woodSpaceKey, PersistentDataType.BOOLEAN)));

        customBlockData.set(woodSpaceKey, PersistentDataType.BOOLEAN, true);


    }
}
