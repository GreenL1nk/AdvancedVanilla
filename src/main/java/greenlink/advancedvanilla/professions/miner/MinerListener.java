package greenlink.advancedvanilla.professions.miner;

import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class MinerListener extends Miner {
    public MinerListener(String name) {
        super(name);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (rpPlayer.getProfession() instanceof Miner) ((Miner) rpPlayer.getProfession()).onBreak(event);
            else {
                /*
                Делается если чел не имеет нужную профессию
                 */
        }

    }
}
