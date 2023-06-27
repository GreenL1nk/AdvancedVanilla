package greenlink.advancedvanilla.professions.fisherman;

import greenlink.advancedvanilla.professions.Level;
import greenlink.advancedvanilla.professions.ProfessionBase;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Fisherman extends ProfessionBase {
    public Fisherman(String name, Level... levels) {
        super(name, levels);
    }

    protected void onFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Entity caught = event.getCaught();
            if (caught instanceof Item) {
                checkToRequirements(((Item) caught).getItemStack());
            }
        }
    }

    private void checkToRequirements(ItemStack itemStack) {
        Arrays.stream(getRpPlayer().getProfession().getCurrentRequirements())
                .forEach(requirements -> requirements.isRequirement(itemStack, getRpPlayer()));
    }
}
