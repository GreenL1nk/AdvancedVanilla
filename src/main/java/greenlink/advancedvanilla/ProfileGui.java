package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionSelectGUI;
import greenlink.advancedvanilla.tradeSystem.TradingItem;
import greenlink.advancedvanilla.tradeSystem.VillagerTradeGui;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileGui extends AbstractInventoryHolder {
    private static Component title = Component.text( "              Основное" ).color(TextColor.color(2773694));
    public ProfileGui(Player requester) {
        super(title, 5, requester);

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

        {
            ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Туториал").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(10, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.EMERALD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Донат").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(12, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Профессии").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(14, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.REDSTONE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Настройки").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(16, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.SPYGLASS);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("В разработке...").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(29, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.BEACON);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("В разработке...").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(33, itemStack);
        }

    }

    @Override
    protected void init() {
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);

        switch (event.getSlot()) {
            case  14 : { if (event.isLeftClick()) ProfessionSelectGUI.display(((Player) event.getWhoClicked()));
                } break;

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
//        VillagerTradeGui openingGui = new VillagerTradeGui(title, player, items);
//        ArrayList<VillagerTradeGui> guisList = guis.get(profession);
//        if (guisList == null) guisList = new ArrayList<>();
//        guisList.add(openingGui);
//        guis.put( profession, guisList );
//        openingGui.profession = profession;
        ProfileGui profileGui = new ProfileGui(player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ profileGui.open(); }, 1);
    }
}
