package greenlink.advancedvanilla.listeners;

import lib.utils.AbstractListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.bukkit.enchantments.Enchantment.*;

/**
 * Листнер блокирующий использование некоторых зачарований или ограничивающий максимально получаемый уровень этих зачарований
 */
public class EnchantmentsFixListener extends AbstractListener {

    private static final List<Enchantment> DISSALLOWED_ENCHANTS = Arrays.asList(RIPTIDE, ARROW_FIRE, ARROW_INFINITE, FIRE_ASPECT, LOYALTY); //недопустимые к получению зачарования
    private final int sharpnessMax = 4;
    private final int powerMax = 4;

    public EnchantmentsFixListener(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    /**
     * Блокирует накладывание ненужных чар при использовании стола зачарований
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void enchantmentFix(EnchantItemEvent event) {
        for (Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
            if (DISSALLOWED_ENCHANTS.contains(entry.getKey())) {
                event.getEnchantsToAdd().remove(entry.getKey());
            }
        }
    }

    /**
     * Отключает работу "Ледоходов"
     */
    @EventHandler
    public void IceWalkerFix(EntityBlockFormEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            ItemStack boots = ((Player) event.getEntity()).getInventory().getBoots();
            if (boots != null) boots.removeEnchantment(FROST_WALKER);
        }
    }

    /**
     * Отключает работу "Тягуна"
     */
    @EventHandler
    public void riptideFix(PlayerMoveEvent event) {
        if (event.getPlayer().isRiptiding()) {
            event.setCancelled(true);

            if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT)) {
                event.getPlayer().getInventory().getItemInMainHand().removeEnchantment(RIPTIDE);
            }

            if (event.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.TRIDENT)) {
                event.getPlayer().getInventory().getItemInOffHand().removeEnchantment(RIPTIDE);
            }
        }
    }

    /**
     * Отключение ванильной работы чара "Flame"
     */
    @EventHandler
    public void flameFix(EntityShootBowEvent event) {
        if (event.getBow() != null) {
            event.getBow().removeEnchantment(ARROW_FIRE);
        }

        if (event.getProjectile().getType().equals(EntityType.ARROW)) {
            Arrow arrow = (Arrow) event.getProjectile();
            arrow.setFireTicks(-1);
        }
    }

    /**
     * Ограничение максимального уровня определённых чар при совмещении их в наковальни
     */
    // TODO: 12.06.2023 Check - isWorking?
    public void anvilFix1(InventoryClickEvent event) {
        if (!event.getInventory().getType().equals(InventoryType.ANVIL)) return;
        //System.out.println(event.getRawSlot());
        if (event.getRawSlot() == 2) {
            if (event.getCurrentItem() != null) {
                Integer sharpLvl = event.getCurrentItem().getEnchantments().get(DAMAGE_ALL);

                if (sharpLvl != null && sharpLvl > sharpnessMax) {
                    event.getCurrentItem().addEnchantment(DAMAGE_ALL, sharpnessMax);
                }
            }
        }
    }

    /**
     * Ограничение максимального уровня определённых чар при совмещении их в наковальни
     */
    @EventHandler
    public void anvilFix2(PrepareAnvilEvent event) {
        if (event.getResult() == null) return;
        Integer sharpLvl = event.getResult().getEnchantments().get(DAMAGE_ALL);
        if (sharpLvl != null && sharpLvl > sharpnessMax) {
            event.getResult().addEnchantment(DAMAGE_ALL, sharpnessMax);
        }

        Integer powerLvl = event.getResult().getEnchantments().get(ARROW_FIRE);
        if (powerLvl != null && powerLvl > powerMax) {
            event.getResult().addEnchantment(ARROW_FIRE, powerMax);
        }
    }

}
