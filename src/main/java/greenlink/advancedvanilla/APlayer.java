package greenlink.advancedvanilla;

import greenlink.advancedvanilla.listeners.IProfessionBase;

import java.util.UUID;

public class APlayer {
    private UUID uuid;
    private IProfessionBase profession;

    public APlayer(UUID uuid) {
        this.uuid = uuid;
        profession = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public IProfessionBase getProfession() {
        return profession;
    }

    public void setProfession(IProfessionBase profession) {
        this.profession = profession;
    }
}
