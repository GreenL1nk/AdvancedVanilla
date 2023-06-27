package lib.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        return IntStream.range(0, 5)
                .mapToObj(i -> String.valueOf(characters.charAt(random.nextInt(characters.length()))))
                .collect(Collectors.joining());
    }
}
