package lib.utils;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static List<Material> ores = new ArrayList<>();

    public static List<Material> getOreMaterials() {
        if (ores.isEmpty()) ores = Arrays.stream(Material.values()).filter(material -> material.getKey().value().toLowerCase().contains("ore")).collect(Collectors.toList());
        return ores;
    }

    public static ItemStack getItem(Component name, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
