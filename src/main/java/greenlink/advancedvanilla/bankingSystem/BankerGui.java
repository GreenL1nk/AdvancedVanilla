package greenlink.advancedvanilla.bankingSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class BankerGui extends AbstractInventoryHolder {

    private static Component title = Component.text( "               Банкир" ).color(TextColor.color(2773694));
    public BankerGui(Player requester) {
        super(title, 5, requester);

        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
            this.inventory.setItem(i+36, new ItemStack(Material.BLACK_STAINED_GLASS));
        }

        for (int i = 0; i < 4; i++) {
            this.inventory.setItem((i + 1) * 9, new ItemStack(Material.BLACK_STAINED_GLASS));
            this.inventory.setItem((i + 1) * 9 + 8, new ItemStack(Material.BLACK_STAINED_GLASS));
        }
        int[] multiplyers = new int[]{1, 10 , 64};
        for (int i = 0; i < 3; i++) {
            ItemStack itemStack = new ItemStack(Material.SUNFLOWER, multiplyers[i]);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text("Положить деньги в банк: ").color(TextColor.color(11184810)).
                    append(Component.text("ЛКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));

            lore.add(Component.text("Снять деньги в кошелёк: ").color(TextColor.color(11184810)).
                    append(Component.text("ПКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));

            lore.add( Component.text(" ") );

            lore.add(Component.text("Сумма: ").color(TextColor.color(11184810)).
                    append(Component.text(multiplyers[i]).color(TextColor.color(16755200))).decoration(TextDecoration.ITALIC, false));
            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
            this.inventory.setItem(19+i*2, itemStack);
        }
        {
            ItemStack itemStack = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text("Положить ВСЕ деньги в банк: ").color(TextColor.color(11184810)).
                    append(Component.text("ЛКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));

            lore.add(Component.text("Снять ВСЕ деньги в кошелёк: ").color(TextColor.color(11184810)).
                    append(Component.text("ПКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));

            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
            this.inventory.setItem(25, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setOwningPlayer(requester);
                itemMeta.displayName(Component.text(requester.getName()).color(TextColor.color(972270)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(40, itemStack);
        }
    }

    @Override
    protected void init() {

    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getRawSlot();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(requester.getUniqueId());

        if (slot == 19 || slot == 21 || slot == 23) {
            int multiplyer = 1;
            if (slot == 21) multiplyer = 5;
            if (slot == 23) multiplyer = 64;

            if ( event.isLeftClick() ) {
                if ( rpPlayer.getPocketMoney() > multiplyer ) {
                    rpPlayer.setPocketMoney(rpPlayer.getPocketMoney() - multiplyer );
                    rpPlayer.setBankMoney(rpPlayer.getBankMoney() + multiplyer );
                } else {
                    requester.playSound( requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }

            } else {
                if ( rpPlayer.getBankMoney() > multiplyer ) {
                    rpPlayer.setBankMoney(rpPlayer.getBankMoney() - multiplyer );
                    rpPlayer.setPocketMoney(rpPlayer.getPocketMoney() + multiplyer );
                } else {
                    requester.playSound( requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }

        if (slot == 25) {
            if (event.isLeftClick()) {
                if (rpPlayer.getPocketMoney() > 0) {
                    //-pocket +bank
                    rpPlayer.setBankMoney(rpPlayer.getBankMoney() + rpPlayer.getPocketMoney() );
                    rpPlayer.setPocketMoney( 0 );
                } else requester.playSound(requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else {
                if (rpPlayer.getBankMoney() > 0) {
                    //-bank +pocket
                    rpPlayer.setPocketMoney(rpPlayer.getPocketMoney() + rpPlayer.getBankMoney() );
                    rpPlayer.setBankMoney( 0 );
                } else requester.playSound(requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
        BankerGui bankerGui = new BankerGui(player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ bankerGui.open(); }, 1);
    }
}
