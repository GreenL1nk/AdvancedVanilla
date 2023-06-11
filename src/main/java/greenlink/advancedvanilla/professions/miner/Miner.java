package greenlink.advancedvanilla.professions.miner;

import greenlink.advancedvanilla.professions.Level;
import greenlink.advancedvanilla.professions.ProfessionBase;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Miner extends ProfessionBase {

    public Miner(String name, Level... levels) {
        super(name, levels);
    }

    protected void onBreak(BlockBreakEvent event){

    }

    protected void onClick(InventoryClickEvent event){

    }
}
