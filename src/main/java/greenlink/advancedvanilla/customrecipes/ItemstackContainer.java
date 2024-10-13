package greenlink.advancedvanilla.customrecipes;

import lib.utils.ItemChanger;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemstackContainer {
    private Material material;
    private int count;
    private HashMap<Enchantments,Integer> enchantsments = new HashMap<>();
    private String name;

    public ItemstackContainer(){
    }

    public ItemstackContainer(ItemStack item){
        this.count = item.getAmount();
        this.material = item.getType();
        Set<Map.Entry<Enchantment, Integer>> set = item.getEnchantments().entrySet();
        for (Map.Entry<Enchantment, Integer> entry : set) {
            enchantsments.put( enchToEnum(entry.getKey()), entry.getValue());
        }
        name = item.getItemMeta().getDisplayName();
    }

    private static Enchantments enchToEnum(Enchantment base){
        if (base == Enchantment.POWER){
            return Enchantments.POWER;
        }

        if (base == Enchantment.UNBREAKING) {
            return Enchantments.UNBREAKING;
        }

        if (base == Enchantment.PROTECTION) {
            return Enchantments.PROTECTION;
        }

        if (base == Enchantment.FORTUNE) {
            return Enchantments.FORTUNE;
        }

        if (base == Enchantment.FLAME) {
            return Enchantments.FLAME;
        }

        if (base == Enchantment.INFINITY) {
            return Enchantments.INFINITY;
        }

        return null;
    }

    private static Enchantment enchFromEnum(Enchantments base) {
        return switch (base) {
            case UNBREAKING -> Enchantment.UNBREAKING;
            case PROTECTION -> Enchantment.PROTECTION;
            case POWER -> Enchantment.POWER;
            case FORTUNE -> Enchantment.FORTUNE;
            case INFINITY -> Enchantment.INFINITY;
            case FLAME -> Enchantment.FLAME;
        };

    }

    public ItemStack toItemStack(){
        ItemStack itemStack = new ItemStack(this.material, this.count);
        for (Map.Entry<Enchantments, Integer> entry : this.enchantsments.entrySet()) {
            itemStack.addUnsafeEnchantment( enchFromEnum(entry.getKey()), entry.getValue() );
        }
        ItemChanger.changeName(itemStack, this.name);

        return itemStack;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public HashMap<Enchantments, Integer> getEnchantsments() {
        return enchantsments;
    }

    public String getName() {
        return name;
    }
}
