package greenlink.advancedvanilla.customrecipes;

import greenlink.advancedvanilla.AdvancedVanilla;
import lib.utils.AbstractInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class RecipeGui extends AbstractInventoryHolder {
    private static Component title = Component.text( "      Кастомные крафты" ).color(TextColor.color(2773694));
    private int offset;
    private RecipeGui( Player requester, RecipeView recipeView, int offset) {
        super(title, 5, requester);
        this.offset = offset;

        ItemStack panel = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 44; i++) {
            this.inventory.setItem(i, panel);
        }
        this.inventory.setItem(44, new ItemStack(Material.RED_STAINED_GLASS_PANE) );

        if (recipeView.getRecipeType() == RecipeView.RecipeType.CRAFT) {

            for (int i = 0; i < 9; i++) {
                if (recipeView.getFrom()[i] != null) inventory.setItem( 10 + i %3 + (i /3)*9, new ItemStack(recipeView.getFrom()[i]) );
                else inventory.setItem( 10 + i %3 + (i /3)*9, null );
            }
            inventory.setItem(23, new ItemStack(Material.CRAFTING_TABLE));
            inventory.setItem(25, new ItemStack( recipeView.getResult(), recipeView.getResultCount() ));
        }

        if (recipeView.getRecipeType() == RecipeView.RecipeType.FURNACE) {
            inventory.setItem( 11, new ItemStack( recipeView.getFrom()[0] ) );
            inventory.setItem( 29, new ItemStack(Material.COAL) );
            inventory.setItem( 22, new ItemStack(Material.FURNACE) );
            inventory.setItem( 24, new ItemStack(recipeView.getResult()) );
        }

    }

    @Override
    protected void init() {

    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        event.setCancelled(true);
        int index = event.getRawSlot();
        if ( index == 44 ) {
            RecipesMenuGui.display(((Player) event.getWhoClicked()), this.offset);
            return;
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player player, RecipeView recipeView, int offset ){
        RecipeGui recipeGui = new RecipeGui(player, recipeView, offset);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ recipeGui.open(); }, 1);
    }
}
