package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FiredArrowsSystem extends AbstractListener {
    public FiredArrowsSystem(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void specialFireArrows(EntityShootBowEvent event) {
        if (event.getBow() != null) {
            event.getBow().removeEnchantment(Enchantment.ARROW_FIRE);
        }

        if (event.getProjectile().getType().equals(EntityType.ARROW)) {
            Arrow arrow = (Arrow) event.getProjectile();

            if (event.getArrowItem().getItemMeta() != null && event.getArrowItem().getItemMeta().getLore() != null) {
                int i = 0;
                int fireFlag = 0;
                // TODO: 12.06.2023 refactor to Component
                for (String loreString : event.getArrowItem().getItemMeta().getLore()) {

                    if (i == 0 && loreString.equals("§6special")) fireFlag++;
                    if (i == 1 && loreString.equals("§cfired")) fireFlag++;
                    i++;
                    if (i > 1) break;
                }
                if (fireFlag == 2) {
                    arrow.setFireTicks(2000);
                }
            }
        }
    }

    @EventHandler
    public void firedArrowByCampFire(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.CAMPFIRE)) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();

        if (!itemInMainHand.getType().equals(Material.ARROW)) return;

        ItemStack arrow = null;
        PlayerInventory inventory = event.getPlayer().getInventory();

        if (itemInMainHand.getAmount() == 1) {
            arrow = itemInMainHand;
        } else {
            System.out.println(inventory.getStorageContents().length);
            for (int i = 0; i < inventory.getStorageContents().length; i++) {
                ItemStack itemStack = inventory.getStorageContents()[i];

                //inventory.getItem(i)
                if (itemStack == null) {
                    //arrow = itemStack;
                    inventory.setItem(i, new ItemStack(Material.ARROW, 1));
                    arrow = inventory.getItem(i);
                    itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                    break;
                }
            }
        }

        if (arrow == null) {
            // TODO: 12.06.2023 refactor to Component
            event.getPlayer().sendActionBar(ChatColor.RED + "У вас нет места в инвенторе для горящей стрелы");
            return;
        }

        ItemMeta meta = arrow.getItemMeta();
        if (meta.getLore() != null) {
            return;
        }
        // TODO: 12.06.2023 refactor to Component
        List<String> loreList = new ArrayList<String>();
        loreList.add(ChatColor.GOLD + "special"); //This is the first line of lore
        loreList.add(ChatColor.RED + "fired"); //This is the second line of lore
        String date = String.valueOf(System.currentTimeMillis());
        loreList.add(ChatColor.AQUA + date);
        meta.setLore(loreList);
        arrow.setItemMeta(meta);
    }
}
