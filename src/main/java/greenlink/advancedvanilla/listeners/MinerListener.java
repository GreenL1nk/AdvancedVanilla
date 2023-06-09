package greenlink.advancedvanilla.listeners;

import greenlink.advancedvanilla.APlayer;
import greenlink.advancedvanilla.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class MinerListener extends Miner {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        APlayer aPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if ( aPlayer.getProfession()  instanceof Miner) ((Miner) aPlayer.getProfession()).onBreak(event);
            else {
                /*
                Делается если чел не имеет нужную профессию
                 */
        }

    }
}
