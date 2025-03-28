package greenlink.advancedvanilla.tradeSystem;

import com.google.gson.reflect.TypeToken;
import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.json.Json;
import lib.utils.AbstractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Листнер, ловящий открытие игроком жителей и вместо стандартного меню открывающий соответствующий профессии {@link VillagerTradeGui}
 */
public class VillagerTradingSystem extends AbstractListener {

    private HashMap<Villager.Profession, TradingItem[]> tradingItems; //мапа хранящая трейды для каждой профессии

    public VillagerTradingSystem(JavaPlugin plugin){
        super(plugin);
        HashMap<Villager.Profession, TradingItem[]> itemsMap = null;
        //попытка загрузить конфиг с трейдами для жителей
        try {
            String result;
            Path path = plugin.getDataFolder().toPath().resolve("tradingItems.json");
            if (Files.exists(path)) {
                String jsonStr = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

                Type type = new TypeToken< Map<Villager.Profession, TradingItem[]> >() {
                }.getType();
                Map<Villager.Profession, TradingItem[]> fromFile = Json.GSON.fromJson(jsonStr, type);
                if (fromFile == null) {
                    Bukkit.getLogger().info("tradingItems.json is NULL.");
                    itemsMap = null;
                }
                else {
                    itemsMap = new HashMap<>(fromFile);
                    tradingItems = itemsMap;
                }

            } else {
                Bukkit.getLogger().info("tradingItems.json doesn`t exist");
                itemsMap = null;
            }
        } catch (Exception e){
            itemsMap = null;
            Bukkit.getLogger().info("tradingItems.json cant load. " + e.toString());
        }

        //генерация стандартного конфига при провале попытки загрузить пользовательский
        if (itemsMap == null) {
            TradingItem tradingItem = new TradingItem(Material.IRON_INGOT, 1, 10, new int[]{10, 20, 30, 40}, true, true, new long[]{ 15000,20000,20000,20000});
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

    /**
     * Отслеживает профессию жителя и в зависимости от неё открывает {@link VillagerTradeGui} с нужными {@link VillagerTradingSystem#tradingItems}
     * @param event ивент открытия жителя
     */
    @EventHandler
    public void onVillagerOpen(PlayerInteractAtEntityEvent event){
        if ( event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Villager.Profession profession = ((Villager) event.getRightClicked()).getProfession();

            if (profession == Villager.Profession.MASON || profession == Villager.Profession.WEAPONSMITH) profession = Villager.Profession.TOOLSMITH;
            if (profession == Villager.Profession.SHEPHERD || profession == Villager.Profession.LEATHERWORKER) profession = Villager.Profession.FLETCHER;
            if (profession == Villager.Profession.BUTCHER ) profession = Villager.Profession.FARMER;

            TradingItem[] items = tradingItems.get(profession);

            if (items != null) {
                Component component = ((TextComponent)Component.text(profession.toString()).color(TextColor.color(2773694)));
                VillagerTradeGui.display(component, event.getPlayer(), items, profession);
            }
        }

    }


    /**
     * Защита от двойного открытия жителя из-за особенности работы бакита
     */
    @EventHandler
    public void onVillagerOpen(InventoryOpenEvent event){
        if (event.getInventory() instanceof  MerchantInventory) event.setCancelled(true);
    }

}
