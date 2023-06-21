package greenlink.advancedvanilla.professions.miner;

import greenlink.advancedvanilla.professions.Level;
import greenlink.advancedvanilla.professions.ProfessionBase;
import greenlink.advancedvanilla.professions.requirements.ItemRequirement;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class Miner extends ProfessionBase {

    public Miner(String name, Level... levels) {
        super(name, levels);
    }

    protected void onBreak(BlockBreakEvent event) {
        if (haveSilkTouch(event.getPlayer())) return;
        checkToRequirements(event.getBlock().getType());
    }

    protected void onClick(InventoryClickEvent event) {

    }

    private void checkToRequirements(Material material) {
        Arrays.stream(getRpPlayer().getProfession().getCurrentRequirements())
                .forEach(requirements -> requirements.isRequirement(material, getRpPlayer()));
    }

    private boolean haveSilkTouch(Player player) {
        return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);
    }
}
