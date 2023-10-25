package greenlink.advancedvanilla;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private HashMap<UUID, RpPlayer> players = new HashMap<>();
    private static PlayerManager instance;
    private final DatabaseConnector dataBase;
    private PlayerManager(){

        dataBase = DatabaseConnector.getInstance();
    }

    public static PlayerManager getInstance(){
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public RpPlayer getPlayer(@NotNull UUID uuid){
        RpPlayer rpPlayer = players.get(uuid);

        if (rpPlayer == null) {
            rpPlayer = dataBase.getPlayer(uuid);
            players.put(uuid, rpPlayer);
        }

        return rpPlayer;
    }

    public void saveRpPlayer(UUID uuid) {
        dataBase.savePlayer(players.get(uuid));
    }

    public void savePlayers() {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            saveRpPlayer(onlinePlayer.getUniqueId());
        }
    }
}