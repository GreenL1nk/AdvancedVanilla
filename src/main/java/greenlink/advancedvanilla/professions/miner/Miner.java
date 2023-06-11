package greenlink.advancedvanilla.professions.miner;

import greenlink.advancedvanilla.professions.ProfessionBase;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Miner extends ProfessionBase {

    public Miner(String name) {
        super(name);
    }

    protected void onBreak(BlockBreakEvent event){
        Bukkit.broadcastMessage(String.valueOf(event.getBlock()));
    }

    protected void onClick(InventoryClickEvent event){

    }
}
