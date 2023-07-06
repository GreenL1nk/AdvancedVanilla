package greenlink.advancedvanilla.customrecipes;

import com.google.gson.reflect.TypeToken;
import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.profileGuis.ProfileGui;
import greenlink.advancedvanilla.json.Json;
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

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RecipesMenuGui extends AbstractInventoryHolder {
    private static RecipeView[] recipes;
    private static Component title = Component.text( "      Кастомные крафты" ).color(TextColor.color(2773694));

    private int offset;

    private static void load(){
        recipes = null;

        try {
            String result;
            Path path = AdvancedVanilla.getInstance().getDataFolder().toPath().resolve("recipes.json");
            if (Files.exists(path)) {
                String jsonStr = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

                Type type = new TypeToken<RecipeView[]>() {}.getType();
                RecipeView[] fromFile = Json.GSON.fromJson(jsonStr, type);
                if (fromFile == null) { Bukkit.getLogger().info("recipes.json is NULL."); }
                else {
                    recipes = fromFile;
                }

            } else {
                Bukkit.getLogger().info("recipes.json doesn`t exist");
            }
        } catch ( Exception e){
            Bukkit.getLogger().info("recipes.json cant load. " + e.toString());
        }

        if (recipes == null) {
            RecipeView test1View = new RecipeView(new Material[]{Material.IRON_NUGGET, Material.IRON_NUGGET, Material.IRON_NUGGET, Material.IRON_NUGGET, Material.IRON_NUGGET, Material.IRON_NUGGET, null, null, null}, Material.IRON_INGOT, 1, RecipeView.RecipeType.CRAFT);
            RecipeView test2View = new RecipeView(new Material[]{Material.RAW_IRON }, Material.IRON_NUGGET, 1, RecipeView.RecipeType.FURNACE);
            recipes = new RecipeView[] { test1View, test2View };
        }

    }
    private RecipesMenuGui(Player requester, int offset) {
        super(title, 5, requester);

        ItemStack panel = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, panel);
            this.inventory.setItem(i+36, panel);
        }
        for (int i = 0; i < 4; i++) {
            this.inventory.setItem((i+1)*9, panel);
            this.inventory.setItem((i+1)*9+8, panel);
        }
        this.inventory.setItem(44, new ItemStack(Material.RED_STAINED_GLASS_PANE));

        {
            ItemStack itemStack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.displayName(Component.text(".").color(TextColor.color(5889190)).decoration(TextDecoration.ITALIC, false));
                ArrayList<Component> lore = new ArrayList<>();
                lore.add(Component.text(" "));
                lore.add(Component.text("Листать вниз: ").color(TextColor.color(11064039)).append(
                        Component.text( "Shift + ЛКМ" ).color(TextColor.color(9290582))));
                lore.add(Component.text("Листать вверх: ").color(TextColor.color(11064039)).append(
                        Component.text( "Shift + ПКМ" ).color(TextColor.color(9290582))));

                itemMeta.lore(lore);
                itemStack.setItemMeta(itemMeta);
            }
            this.inventory.setItem(26, itemStack);
        }

        if (recipes == null) load();

        this.offset = offset;
        this.displayCrafts();
    }

    @Override
    protected void init() {    }

    private void displayCrafts( ){
        for (int i = 0; i < 21; i++) {
            this.inventory.setItem( 10 + i % 7 + (i/7)*9, null );
        }

        for (int i = 0; i < Math.min(21, recipes.length - offset*7) ; i++) {
            this.inventory.setItem( 10 + i % 7 + (i/7)*9, new ItemStack(recipes[i + offset*7 ].getResult()) );
        }
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int index = event.getRawSlot();
        if ( index == 44 ) {
            ProfileGui.display(((Player) event.getWhoClicked()));
            return;
        }

        if (index == 26 && event.isShiftClick() ){
            if (event.isLeftClick()) {
                int length = recipes.length / 7;
                if (recipes.length % 7 > 0) length++;
                length-=3;
                if (offset < length) offset++;
                this.displayCrafts();
                return;
            }

            if (event.isRightClick()) {
                if (offset > 0) offset--;
                this.displayCrafts();
                return;
            }
        }

        index-=10;
        if (index >= 27 ) return;
        if (index%9 >6 ) return;
        index = index % 9 + (index / 9)*7;

        index+= offset*7;

        if (index < recipes.length ) {
            RecipeGui.display(((Player) event.getWhoClicked()), recipes[index],offset);
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public static void display(Player player, int offset){
        RecipesMenuGui gui = new RecipesMenuGui(player, offset);
        Bukkit.getServer().getScheduler().runTaskLater(AdvancedVanilla.getInstance(),()->{ gui.open(); }, 1);
    }
}
