package greenlink.advancedvanilla.compasSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class CompassGui extends AbstractInventoryHolder {

    private static Component title = Component.text( "               Компасы" ).color(TextColor.color(2773694));
    private CompassGui(Player requester) {
        super(title, 5, requester);

        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
            this.inventory.setItem(i+36, new ItemStack(Material.BLACK_STAINED_GLASS));
        }

        for (int i = 0; i < 4; i++) {
            this.inventory.setItem((i+1)*9, new ItemStack(Material.BLACK_STAINED_GLASS));
            this.inventory.setItem((i+1)*9+8, new ItemStack(Material.BLACK_STAINED_GLASS));
        }

        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(requester.getUniqueId());
        for (int i = 0; i < rpPlayer.getCompasses().length; i++) {
            this.inventory.setItem(20+i*2, getDisplayerCompas(rpPlayer.getCompasses()[i], i+1, rpPlayer.getActiveCompass() == i) );
        }

    }

    private @NotNull ItemStack getDisplayerCompas(@Nullable Compass compass, int index, boolean isActive){
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.displayName(Component.text("Компас " + (index) ).color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
            ArrayList<Component> lore = new ArrayList<>();
            lore.add( Component.text(" ") );

            if (compass == null) {
                lore.add(Component.text("Точка не задана").color(TextColor.color(14824757)).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("Сохранить текущую точку: ").color(TextColor.color(11064039)).append(
                        Component.text( "Shift + ЛКМ" ).color(TextColor.color(9290582))
                ).decoration(TextDecoration.ITALIC, false) );
            } else {
                lore.add(Component.text("X: ").color(TextColor.color(972270)).append(Component.text( compass.getDestinationX() ).color(TextColor.color(9290582))).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("Z: ").color(TextColor.color(972270)).append(Component.text( compass.getDestinationZ() ).color(TextColor.color(9290582))).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("Мир: ").color(TextColor.color(972270)).append(Component.text( compass.getWorld() ).color(TextColor.color(9290582))).decoration(TextDecoration.ITALIC, false));

                lore.add( Component.text(" ") );

                if (!isActive) lore.add(Component.text("Выбрать текущим: ").color(TextColor.color(11064039)).append(
                        Component.text( "Shift + ЛКМ" ).color(TextColor.color(9290582))
                ).decoration(TextDecoration.ITALIC, false) );
                else lore.add(Component.text("Выключить: ").color(TextColor.color(11064039)).append(
                        Component.text( "Shift + ЛКМ" ).color(TextColor.color(9290582))
                ).decoration(TextDecoration.ITALIC, false) );

                lore.add(Component.text("Очистить запись: ").color(TextColor.color(11064039)).append(
                        Component.text( "Shift + ПКМ" ).color(TextColor.color(9290582))
                ).decoration(TextDecoration.ITALIC, false) );
            }
            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
        }

        if (isActive) itemStack.addUnsafeEnchantment(Enchantment.MENDING, 1);

        return itemStack;
    }

    @Override
    protected void init() {

    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        Compass[] compasses = rpPlayer.getCompasses();
        Location playerLocation = player.getLocation();
        int index = -1;
        if (event.getRawSlot() == 20) index = 0;
        if (event.getRawSlot() == 22) index = 1;
        if (index != -1){
            if (compasses[index] == null ){
                /*
                Compass don`t initialized
                 */


                if (event.isRightClick() && event.isShiftClick()){
                    compasses[index] = new Compass( playerLocation.getBlockX(), playerLocation.getBlockZ(), playerLocation.getWorld().getEnvironment().toString() );
                    this.inventory.setItem(20+index*2, getDisplayerCompas(compasses[index], index+1, rpPlayer.getActiveCompass() == index ) );
                }

            } else {
                /*
                Compass is initialized
                 */

                if (event.isRightClick() && event.isShiftClick()) {
                    compasses[index] = null;
                    this.inventory.setItem(20+index*2, getDisplayerCompas(null, index+1, rpPlayer.getActiveCompass() == index ) );
                }

                if (event.isLeftClick() && event.isShiftClick() ) {

                    if (rpPlayer.getActiveCompass() != index ){
                        int prevActivCompass = rpPlayer.getActiveCompass();
                        rpPlayer.setActiveCompass(index);
                        this.inventory.setItem(20+index*2, getDisplayerCompas(compasses[index] , index+1, true ) );
                        if (prevActivCompass != -1) this.inventory.setItem(20+prevActivCompass*2, getDisplayerCompas(compasses[prevActivCompass] , prevActivCompass+1, false ) );
                        CompasListener.startCompass(player);
                    } else {
                        int prevActivCompass = rpPlayer.getActiveCompass();
                        rpPlayer.setActiveCompass(-1);
                        this.inventory.setItem(20+prevActivCompass*2, getDisplayerCompas(compasses[prevActivCompass] , prevActivCompass+1, false ) );
                        CompasListener.endCompass(player);
                    }


                }
            }
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
        CompassGui compasGui = new CompassGui(player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ compasGui.open(); }, 1);
    }
}
