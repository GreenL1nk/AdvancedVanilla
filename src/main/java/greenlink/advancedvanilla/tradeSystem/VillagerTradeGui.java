package greenlink.advancedvanilla.tradeSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractInventoryHolder;
import lib.utils.MyObservable;
import lib.utils.MyObserver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class VillagerTradeGui extends AbstractInventoryHolder implements MyObserver {
    private TradingItem[] items;
    private Villager.Profession profession;
    private static HashMap< Villager.Profession, ArrayList<VillagerTradeGui> > guis = new HashMap<>();
    private VillagerTradeGui(Component title, Player requester, TradingItem[]  items) {
        super(title, 6, requester);
        this.items = items;
        for (TradingItem item : items) {
            item.addObserver(this);
        }
        this.init();
    }

    @Override
    protected void init() {
        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS));
            this.inventory.setItem(i+45, new ItemStack(Material.BLACK_STAINED_GLASS));
        }
        for (int i = 0; i < 5; i++) {
            this.inventory.setItem((i+1)*9, new ItemStack(Material.BLACK_STAINED_GLASS));
            this.inventory.setItem((i+1)*9+8, new ItemStack(Material.BLACK_STAINED_GLASS));
        }
        for (int i = 0; i < items.length; i++) {
            this.updateDisplayItem(i);
        }
    }

    @Override
    public void click(InventoryClickEvent event) {
//        Bukkit.broadcast(Component.text("test1 "));
        int slot = event.getSlot();
        if ( (slot >= 20 && slot < 25) || (slot >= 29 && slot < 34) ) {
//            Bukkit.broadcast(Component.text("test2 "));
            slot-=20;
            slot = (slot/9 * 5) + slot % 9;
            //System.out.println(slot + " " + this.items.length);
            if (slot < this.items.length) {
                if ( event.isLeftClick() )
                    if ( items[slot].tryBuyitem(((Player) event.getWhoClicked()), !event.isShiftClick() )) {
                        requester.playSound( requester.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 0.2F);
                    } else {
                        requester.playSound( requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                if ( event.isRightClick() )
                    if ( items[slot].trySellItem(((Player) event.getWhoClicked()), !event.isShiftClick() )) {
                        requester.playSound( requester.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    } else {
                        requester.playSound( requester.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
            }
        }

        event.setCancelled(true);
    }

    @Override
    public void close(InventoryCloseEvent event) {
        for (TradingItem item : this.items) {
            item.deleteObserver(this);
        }
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Component title, Player player, TradingItem[] items, Villager.Profession profession){
        VillagerTradeGui openingGui = new VillagerTradeGui(title, player, items);
        ArrayList<VillagerTradeGui> guisList = guis.get(profession);
        if (guisList == null) guisList = new ArrayList<>();
        guisList.add(openingGui);
        guis.put( profession, guisList );
        openingGui.profession = profession;
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ openingGui.open(); }, 1);
    }

    @Override
    public void onUpdate(MyObservable observable) {
        //Bukkit.broadcast(Component.text("enter onUpdate"));
        for (int i = 0; i < items.length; i++) {
            if (items[i] == observable) {
                //Bukkit.broadcast(Component.text("execute onUpdate"));
                updateDisplayItem(i);
                break;
            }
        }
    }

    private void updateDisplayItem(int index){
        //System.out.println("index = " + index + " ");
        try {
            ItemStack itemStack = new ItemStack(items[index].getMaterial());
            ArrayList<Component> lore = new ArrayList<>();

            if (items[index].isCanBuy()) {
                lore.add(Component.text("Купить предмет: ").color(TextColor.color(11184810)).
                        append(Component.text("ЛКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));
            }

            if (items[index].isCanSell()) {
                lore.add(Component.text("Продать предмет: ").color(TextColor.color(11184810)).
                        append(Component.text("ПКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));
            }

            lore.add(Component.text(" "));
            lore.add(Component.text("Колличество: ").color(TextColor.color(11184810)).
                    append(Component.text(items[index].getCount()).color(TextColor.color(972270))).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text(" "));

            if (items[index].isCanBuy()) {
                lore.add(Component.text("Покупка: ").color(TextColor.color(11184810)).
                        append(Component.text(items[index].getNowBuyPrice()).color(TextColor.color(16755200))).decoration(TextDecoration.ITALIC, false));
            }

            if (items[index].isCanSell()) {
                lore.add(Component.text("Продажа: ").color(TextColor.color(11184810)).
                        append(Component.text(items[index].getNowBuyPrice() - 1).color(TextColor.color(16755200))).decoration(TextDecoration.ITALIC, false));
            }

            TextComponent color = Component.text("Лимит скупки: ").color(TextColor.color(11184810));
            int leftForLevelChange = items[index].getLeftForLevelChange();
            if (leftForLevelChange > 0) {
                color = color.append(Component.text("▲").color(TextColor.color(11278368)));
                leftForLevelChange = items[index].getNowAmplitude() - leftForLevelChange;
            } else if (leftForLevelChange < 0) {
                color = color.append(Component.text("▼").color(TextColor.color(11278368)));
                leftForLevelChange = items[index].getNowAmplitude() + leftForLevelChange;
            } else leftForLevelChange = items[index].getNowAmplitude();


            color = color.append(Component.text(leftForLevelChange).color(TextColor.color(9290582))).decoration(TextDecoration.ITALIC, false);
            lore.add(color);

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);
            this.inventory.setItem(20 + (index % 5) + 9 * (index / 5), itemStack);
        } catch (Exception e){
            System.out.println(index);
            e.printStackTrace();
        }
    }
}
