package greenlink.advancedvanilla.professions.miner;

import greenlink.advancedvanilla.professions.Level;
import greenlink.advancedvanilla.professions.ProfessionBase;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class Miner extends ProfessionBase {

    public Miner(String name, Level... levels) {
        super(name, levels);
    }

    protected void onBreak(BlockBreakEvent event) {
        if (haveSilkTouch(event.getPlayer())) return;
        processRequirements(event.getBlock().getType());
    }

    private boolean haveSilkTouch(Player player) {
        return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);
    }
}
