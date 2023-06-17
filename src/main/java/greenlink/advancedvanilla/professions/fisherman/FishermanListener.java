package greenlink.advancedvanilla.professions.fisherman;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

public class FishermanListener extends Fisherman {
    public FishermanListener() {
        super("");
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (rpPlayer.getProfession() instanceof Fisherman) {
            ((Fisherman) rpPlayer.getProfession()).onFish(event);
        }
        else {

        }
    }
}
