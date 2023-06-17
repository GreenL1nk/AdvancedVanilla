package greenlink.advancedvanilla.professions.requirements;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public abstract class Requirement {

    @Nullable
    public abstract ArrayList<Component> loreComponents();
    @Nullable
    public abstract ItemStack getDisplayItem();

}
