package greenlink.advancedvanilla.tradeSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class VillagerTradingSystem extends AbstractListener {

    private HashMap<Villager.Profession, TradingItem[]> tradingItems;

    public VillagerTradingSystem(JavaPlugin plugin){
        super(plugin);
        /*
        todo loading from config
         */
        {
            TradingItem tradingItem = new TradingItem(Material.IRON_INGOT, 1, 10, new int[]{10, 20, 30, 40}, true, false, new long[]{ 60000,60000,60000,60000});
            tradingItems = new HashMap<>();
            TradingItem[] items = {tradingItem};
            tradingItems.put(Villager.Profession.TOOLSMITH, items);
            Bukkit.getScheduler().runTaskTimer(AdvancedVanilla.getInstance(), ()-> {
                for (TradingItem item : items) {
                    item.timeCheck();
                }
                }, 0,20);
        }
    }
    @EventHandler
    public void onVillagerOpen(PlayerInteractAtEntityEvent event){
        //System.out.println(event.getRightClicked().getClass());
        if ( event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Villager.Profession profession = ((Villager) event.getRightClicked()).getProfession();
            profession = Villager.Profession.TOOLSMITH;
            Component component = ((TextComponent)Component.text("Test").color(TextColor.color(2773694)));
            VillagerTradeGui.display(component, event.getPlayer(), tradingItems.get(profession), profession);
        }

    }

    @EventHandler
    public void onVillagerOpen(InventoryOpenEvent event){
        if (event.getInventory() instanceof  MerchantInventory) event.setCancelled(true);
    }

}
