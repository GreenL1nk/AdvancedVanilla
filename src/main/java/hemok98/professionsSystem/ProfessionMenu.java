package hemok98.professionsSystem;

import greenlink.advancedvanilla.AdvancedVanilla;
import hemok98.professionsSystem.professions.Profession;
import lib.utils.AbstractInventoryHolder;
import lib.utils.ItemChanger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ProfessionMenu extends AbstractInventoryHolder {
    private int level;
    private Profession profession;
    private static final int[] levelPosition = new int[]{29, 30, 32, 33};
    private static final int [][] requirementsPosition = {{13},{12,14},{11,13,15}, {10,12,14,16}, {2,6,10,13,16}, {2,6,10,12,14,16}, {2,4,6,10,12,14,16}, {2,3,5,6,10,12,14,16}};

    public ProfessionMenu(Profession profession, Player requester) {
        super(Component.text( "             " + profession.getName() ).color(TextColor.color(2773694)), 5, requester);
        level = profession.getLevel();
        this.profession = profession;
        ItemStack wallItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++) {

            this.inventory.setItem(i+36, wallItem);
        }

        for (int i = 0; i < 5; i++) {
            this.inventory.setItem((i ) * 9, wallItem);
            this.inventory.setItem((i) * 9 + 8, wallItem);
        }

        this.inventory.setItem(28, wallItem);

        this.inventory.setItem(34, wallItem);




        for (int i = 0; i < level; i++) {
            this.inventory.setItem(levelPosition[i], new ItemStack(Material.LIME_STAINED_GLASS_PANE, i+1));
        }

        for (int i = level; i < 4; i++) {
            this.inventory.setItem(levelPosition[i], new ItemStack(Material.RED_STAINED_GLASS_PANE, i+1));
        }

        updateReq();
        this.inventory.setItem(44, ItemChanger.changeName(new ItemStack(Material.BARRIER), "Назад",  16733525));

    }

    private void updateReq(){
        if (level < 4) {

            for (int i = 1; i < 8; i++) {
                this.inventory.setItem((i ) , new ItemStack(Material.AIR));
                this.inventory.setItem((i+9) , new ItemStack(Material.AIR));
            }

            List<Requirement> requirements = profession.getRequirements().get(level);
            int[] position = requirementsPosition[requirements.size() - 1];

            for (int i = 0; i < requirements.size(); i++) {
                Requirement requirement = requirements.get(i);
                ItemStack itemStack = new ItemStack(requirement.getMaterial());
                String temp = requirement.getProgress() + "/" + requirement.getNeededAmount();
                if (profession.isFrozen() ) temp = requirement.getNeededAmount() + "";
                ItemChanger.setLore(itemStack, List.of( "", requirement.getDescription(), temp ), new int[]{11184810, 11184810, 9290582});

                this.inventory.setItem( position[i], itemStack );
            }
        }

        ItemStack item = new ItemStack(profession.getDisplayedItem(), level+1);
        ItemChanger.changeName(item, "Информация об уровне", 11184810 );
        ItemChanger.setLore(item, List.of( "", "Уровень выше", "" ), new int[]{11184810, 11184810, 9290582});
        //lore.add(Component.text("Купить предмет: ").color(TextColor.color(11184810)).
        //                        append(Component.text("ЛКМ").color(TextColor.color(16777045))).decoration(TextDecoration.ITALIC, false));
        this.inventory.setItem(31, item);
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);

        switch (event.getRawSlot()) {
            case 31 : {

                if (event.isLeftClick()) {
                    if (level < 3) {
                        level++;
                        updateReq();
                    }
                } else {
                    if (level > 0){
                        level--;
                        updateReq();
                    }
                }

            }
            break;
            case 44 :
                ProfessionSelectMenu.display(requester);
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player player, Profession profession){
        ProfessionMenu professionSelectMenu = new ProfessionMenu(profession, player);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ professionSelectMenu.open(); }, 1);
    }
}
