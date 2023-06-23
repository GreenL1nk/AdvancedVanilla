package greenlink.advancedvanilla.professions.woodcutter;

import com.jeff_media.customblockdata.CustomBlockData;
import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.professions.Level;
import greenlink.advancedvanilla.professions.ProfessionBase;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;

public class Woodcutter extends ProfessionBase {

    protected NamespacedKey woodSpaceKey = new NamespacedKey(AdvancedVanilla.getInstance(), "isBroken");

    public Woodcutter(String name, Level... levels) {
        super(name, levels);
    }

    protected void onBreak(BlockBreakEvent event) {
        CustomBlockData customBlockData = new CustomBlockData(event.getBlock(), AdvancedVanilla.getInstance());



    }
}
