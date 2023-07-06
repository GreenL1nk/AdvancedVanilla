package greenlink.advancedvanilla.profileGuis;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.tradeSystem.SidebarInfoSystem;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;

public class SettingsGui extends AbstractInventoryHolder {
    private static Component title = Component.text( "              Настройки" ).color(TextColor.color(2773694));
    private SettingsGui( Player requester) {
        super(title, 5, requester);
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS);
        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, item);
            this.inventory.setItem(i+36, item);
        }

        for (int i = 0; i < 4; i++) {
            this.inventory.setItem((i+1)*9, item);
            this.inventory.setItem((i+1)*9+8, item);
        }
        this.inventory.setItem(44, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        this.init();
    }

    @Override
    protected void init() {
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(requester.getUniqueId());
        ItemStack itemStack;
        if ( rpPlayer.isDisplaySidebarInfo() ) {
            itemStack = new ItemStack(Material.REDSTONE_TORCH);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text("Информация о персонаже").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text(" "));
            lore.add(Component.text("Выключить отображение: ").color(TextColor.color(11064039)).append(
                    Component.text( "Shift + ЛКМ" ).color(TextColor.color(9290582))
            ).decoration(TextDecoration.ITALIC, false) );
            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
        } else {
            itemStack = new ItemStack(Material.LEVER);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text("Информация о персонаже").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text(" "));
            lore.add(Component.text("Включить отображение: ").color(TextColor.color(11064039)).append(
                    Component.text( "Shift + ЛКМ" ).color(TextColor.color(9290582))
            ).decoration(TextDecoration.ITALIC, false) );
            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
        }

        this.inventory.setItem(20, itemStack);
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);

        if ( event.getRawSlot() == 20 && event.isLeftClick() && event.isShiftClick() ) {
            RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(requester.getUniqueId());
            if (rpPlayer.isDisplaySidebarInfo()) {
                requester.getScoreboard().clearSlot( DisplaySlot.SIDEBAR );
                rpPlayer.setDisplaySidebarInfo(false);
                rpPlayer.deleteObserver(SidebarInfoSystem.getInstance());
            } else {
                SidebarInfoSystem.addSidebarToPlayer(requester);
                rpPlayer.setDisplaySidebarInfo(true);
            }
            this.init();
        }

        if (event.getRawSlot() == 44) {
            ProfileGui.display(requester);
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player player){
        SettingsGui gui = new SettingsGui(player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ gui.open(); }, 1);
    }
}
