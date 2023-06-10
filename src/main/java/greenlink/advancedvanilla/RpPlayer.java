package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionBase;

import java.util.UUID;

public class RpPlayer {
    private final UUID uuid;
    private ProfessionBase profession;
    private ProfessionBase old_profession;

    public RpPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profession = null;
        this.old_profession = null;
    }

    public RpPlayer(UUID uuid, ProfessionBase profession, ProfessionBase old_profession) {
        this.uuid = uuid;
        this.profession = profession;
        this.old_profession = old_profession;
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

    public ProfessionBase getOld_profession() {
        return old_profession;
    }

    public void setOld_profession(ProfessionBase old_profession) {
        this.old_profession = old_profession;
    }
}
