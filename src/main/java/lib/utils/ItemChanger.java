package lib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemChanger {

    public static ItemStack getItem(Component name, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack changeName(ItemStack item, String str){
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.displayName(Component.text(str).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(itemMeta);
        }

        return item;
    }

    public static ItemStack changeName(ItemStack item, String name, Integer color){
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.displayName(Component.text(name).color(TextColor.color(color)).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack changeName(Material material, String name, Integer color){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.displayName(Component.text(name).color(TextColor.color(color)).decoration(TextDecoration.ITALIC, false));
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack setLore(ItemStack item, List<String> Lore, Integer color){
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> coloredLore = new ArrayList<>();
        for (String str : Lore) {
            coloredLore.add( Component.text(str).color(TextColor.color(color)).decoration(TextDecoration.ITALIC, false) );
        }
        itemMeta.lore(coloredLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, List<String> lore, int[] colors){
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> coloredLore = new ArrayList<>();
        for (int i = 0; i < lore.size(); i++) {
            coloredLore.add( Component.text( lore.get(i) ).color(TextColor.color(colors[i])).decoration(TextDecoration.ITALIC, false) );
        }

        itemMeta.lore(coloredLore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
