package greenlink.advancedvanilla.profileGuis;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.customrecipes.RecipesMenuGui;
import greenlink.advancedvanilla.professions.ProfessionSelectGUI;
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
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileGui extends AbstractInventoryHolder {
    private static Component title = Component.text( "              Основное" ).color(TextColor.color(2773694));
    private ProfileGui(Player requester) {
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
            ItemStack itemStack = new ItemStack(Material.COMPARATOR);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Настройки").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(16, itemStack);
        }

        {
            ItemStack itemStack = new ItemStack(Material.CRAFTING_TABLE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Крафты").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
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

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case  14 : { if (event.isLeftClick()) ProfessionSelectGUI.display(player);
                } break;
            case 29 : { if (event.isLeftClick()) RecipesMenuGui.display(player, 0);
            } break;
            case 16 : { if (event.isLeftClick()) SettingsGui.display(player);
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
        ProfileGui profileGui = new ProfileGui(player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ profileGui.open(); }, 1);
    }
}
