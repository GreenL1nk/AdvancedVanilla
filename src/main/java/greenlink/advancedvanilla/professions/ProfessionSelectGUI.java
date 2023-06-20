package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.ProfileGui;
import greenlink.advancedvanilla.RpPlayer;
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

import java.util.HashMap;

public class ProfessionSelectGUI extends AbstractInventoryHolder {
    HashMap<Integer, Professions> indexProfessions = new HashMap<>();
    RpPlayer rpPlayer;
    public ProfessionSelectGUI(Player requester) {
        super(Component.text(""), 6, requester);
        rpPlayer = PlayerManager.getInstance().getPlayer(requester.getUniqueId());
        this.init();
    }

    @Override
    protected void init() {
        int index = 0;
        for (Professions profession : Professions.values()) {
            this.inventory.setItem(index, profession.getDisplayItem());
            indexProfessions.put(index, profession);
            index++;
        }

        {
            ItemStack itemStack = new ItemStack(Material.OAK_DOOR);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text("Назад").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false) );
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(53, itemStack);
        }
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int rawSlot = event.getRawSlot();
        if (indexProfessions.containsKey(rawSlot)) {
            if (rpPlayer.getProfession() == null) rpPlayer.setProfession(indexProfessions.get(rawSlot));
            ProfessionsGUI.display(requester, rpPlayer);
        }

        if (rawSlot == 53 ) {
            ProfileGui.display(((Player) event.getWhoClicked()));
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player requester) {
        ProfessionSelectGUI professionSelectGUI = new ProfessionSelectGUI(requester);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(), professionSelectGUI::open, 1);
    }
}
