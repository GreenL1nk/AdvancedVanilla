package greenlink.advancedvanilla.customrecipes;

import lib.utils.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class RecipesFixListener extends AbstractListener {
    private HashMap<UUID, Boolean> alreadyReloaded = new HashMap<>();
    private ArrayList<NamespacedKey> recipes = new ArrayList<>();
    public RecipesFixListener(@NotNull JavaPlugin plugin) {
        super(plugin);

        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if (next instanceof Keyed ) recipes.add(((Keyed) next).getKey());

        }

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.undiscoverRecipes( player.getDiscoveredRecipes() );
            player.discoverRecipes(recipes);
            alreadyReloaded.put(player.getUniqueId(), true);
        }
    }

//    @EventHandler
//    public void fix(PlayerJoinEvent event){
//        Player player = event.getPlayer();
//        if (alreadyReloaded.get(event.getPlayer().getUniqueId()) != null) {
//            return;
//        }
//
//        player.undiscoverRecipes( player.getDiscoveredRecipes() );
//        player.discoverRecipes(recipes);
//        alreadyReloaded.put(player.getUniqueId(), true);
//    }
}
