package lib.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static List<Material> ores = new ArrayList<>();

    public static List<Material> getOreMaterials() {
        if (ores.isEmpty()) ores = Arrays.stream(Material.values()).filter(material -> material.getKey().value().toLowerCase().contains("ore")).collect(Collectors.toList());
        return ores;
    }

}
