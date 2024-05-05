package hemok98.professionsSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.profileGuis.ProfileGui;
import hemok98.professionsSystem.professions.Profession;
import lib.utils.AbstractInventoryHolder;
import lib.utils.ItemChanger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessionSelectMenu extends AbstractInventoryHolder {
    private static Component title = Component.text( "              Профессии" ).color(TextColor.color(2773694));
    private Map<Integer, Profession> professionPlace;
    public ProfessionSelectMenu( Player requester) {
        super(title, 5, requester);
        ItemStack wallItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, wallItem);
            this.inventory.setItem(i+36, wallItem);
        }

        for (int i = 0; i < 4; i++) {
            this.inventory.setItem((i + 1) * 9, wallItem);
            this.inventory.setItem((i + 1) * 9 + 8, wallItem);
        }

        professionPlace = new HashMap<>();
        List<Profession> professionList = ProfessionManager.getInstance().getProfessionList();
        int i = 0;
        for (Profession profession : professionList) {
            int place = 0;
            if (i < 3) {
                place = 11 + i*2;
            } else {
                place = 28 + i*2;
            }
            professionPlace.put(place, profession);

            updateProfInfo();
            i++;
        }
        this.inventory.setItem(44, ItemChanger.changeName(new ItemStack(Material.BARRIER), "Назад",  16733525));
    }

    private void updateProfInfo(){
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(requester.getUniqueId());
        for (Map.Entry<Integer, Profession> entry : professionPlace.entrySet()) {
            Integer place = entry.getKey();
            Profession profession = entry.getValue();

            ItemStack itemStack = new ItemStack(profession.getDisplayedItem());
            ItemChanger.changeName( itemStack, profession.getName(), 16733695 );
            if (rpPlayer.getProfession() != null) {
                if ( profession.getClass().equals(rpPlayer.getProfession().getClass())) {
                    itemStack.addUnsafeEnchantment(Enchantment.MENDING, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
                    itemStack.setItemMeta(itemMeta);
                }
            }

            this.inventory.setItem(place, itemStack);
        }

        if (rpPlayer.getOldProfession() != null) {
            ItemStack itemStack = new ItemStack(rpPlayer.getOldProfession().getDisplayedItem());
            ItemChanger.changeName( itemStack, "Вторичная профессия", 16733695 );
            inventory.setItem(26, itemStack);
        }

    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.isLeftClick()) {
            if (event.isShiftClick()) {

                Profession profession = professionPlace.get(event.getRawSlot());
                if (profession != null) {
                    boolean success = ProfessionManager.getInstance().setPlayerProfession(PlayerManager.getInstance().getPlayer(requester.getUniqueId()), profession);
                    if (success) {
                        requester.playSound( requester.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.PLAYERS, 1.0F, 0.2F);
                        updateProfInfo();
                    }
                }

            } else {
                Profession profession = professionPlace.get(event.getRawSlot());
                if (profession != null) {
                    ProfessionMenu.display( requester, profession );
                }
            }
        }

        if (event.getRawSlot() == 44) ProfileGui.display(requester);
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player player){
        ProfessionSelectMenu professionSelectMenu = new ProfessionSelectMenu(player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ professionSelectMenu.open(); }, 1);
    }
}
