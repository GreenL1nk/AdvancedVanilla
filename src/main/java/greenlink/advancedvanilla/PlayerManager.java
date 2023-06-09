package greenlink.advancedvanilla;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private HashMap<UUID, APlayer> players = new HashMap<>();
    private static PlayerManager instance;
    private PlayerManager(){};

    public static PlayerManager getInstance(){
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public APlayer getPlayer(@NotNull UUID uuid){
        return players.get(uuid);
    }
}