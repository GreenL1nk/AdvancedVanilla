package greenlink.advancedvanilla.professions.miner;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.Level;
import greenlink.advancedvanilla.professions.requirements.MineRequirements;
import greenlink.advancedvanilla.professions.requirements.Requirements;
import lib.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.stream.Stream;

public class MinerListener extends Miner {
    public MinerListener() {
        super("");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (rpPlayer.getProfession() instanceof Miner) {
            Level level = rpPlayer.getProfession().getLevels()[rpPlayer.getProfession().getCurrentLevel()];

            Arrays.stream(level.requirements())
                    .filter(requirements -> requirements instanceof MineRequirements)
                    .filter(requirements -> ((MineRequirements) requirements).getItemStack().getType().equals(event.getBlock().getType()))
                    .forEach(requirements -> ((MineRequirements) requirements).incrementCurrentProgress());

            ((Miner) rpPlayer.getProfession()).onBreak(event);
        }
        else {
            if (event.getBlock().getType() == Material.GOLD_ORE
                    || event.getBlock().getType() == Material.REDSTONE_ORE
                    || event.getBlock().getType() == Material.DIAMOND_ORE
                    || event.getBlock().getType() == Material.EMERALD_ORE) {
                //TODO выпадение с шёлком
                event.setDropItems(false);
                event.setExpToDrop(0);
            }
        }
    }

    @EventHandler
    public void onClickBlock(PlayerInteractEvent event){
        if (event.getClickedBlock() == null) return;
        if (event.getItem() != null
                && event.getItem().getType() != Material.IRON_PICKAXE
                && event.getItem().getType() != Material.GOLDEN_PICKAXE
                && event.getItem().getType() != Material.DIAMOND_PICKAXE
                && event.getItem().getType() != Material.NETHERITE_PICKAXE) return;
        if (!event.getAction().isLeftClick()) return;
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (!(rpPlayer.getProfession() instanceof Miner)) {
            if (Utils.getOreMaterials().contains(event.getClickedBlock().getType())) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1, false, false, true));
            }
        }
    }
}
