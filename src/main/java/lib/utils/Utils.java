package lib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    private static JsonParser PARSER = new JsonParser();

    private static Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
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

    private static boolean isChild(Path parent, Path child) {
        return child.toAbsolutePath().toString().startsWith(parent.toAbsolutePath().toString());
    }

    @Nullable
    private static JsonElement readConfig(Path home, String fileName) {
        Path configPath = home.resolve(fileName);
        if (!isChild(home, configPath))
            return null;
        if (!Files.exists(configPath, new java.nio.file.LinkOption[0]))
            return null;
        try {
            return PARSER.parse(Files.newBufferedReader(configPath));
        } catch (IOException e) {
            return null;
        }
    }

    private static boolean writeConfig(Path home, String fileName, JsonElement content) {
        Path configPath = home.resolve(fileName);
        if (!isChild(home, configPath))
            return false;
        if (!Files.exists(configPath, new java.nio.file.LinkOption[0]))
            return false;
        try {
            GSON.toJson(content, Files.newBufferedWriter(configPath, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE }));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    public static JsonElement readConfig(World world, String fileName) {
        Path worldHome = world.getWorldFolder().toPath();
        return readConfig(worldHome, fileName);
    }

    @Nullable
    public static JsonElement readConfig(Plugin plugin, String fileName) {
        Path configHome = plugin.getDataFolder().toPath();
        return readConfig(configHome, fileName);
    }

    public static boolean writeConfig(Plugin plugin, String fileName, JsonElement content) {
        Path configHome = plugin.getDataFolder().toPath();
        return writeConfig(configHome, fileName, content);
    }
}
