package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionBase;

import java.util.UUID;

public class APlayer {
    private UUID uuid;
    private ProfessionBase profession;

    public APlayer(UUID uuid) {
        this.uuid = uuid;
        profession = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProfessionBase getProfession() {
        return profession;
    }

    public void setProfession(ProfessionBase profession) {
        this.profession = profession;
    }
}
