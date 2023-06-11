package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionBase;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.professions.Professions;

import java.util.UUID;

public class RpPlayer {
    private final UUID uuid;
    private ProfessionBase profession;
    private ProfessionBase oldProfession;
    private long professionChangeTime;

    public RpPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profession = null;
        this.oldProfession = null;
    }

    public RpPlayer(UUID uuid, ProfessionBase profession, ProfessionBase oldProfession) {
        this.uuid = uuid;
        this.profession = profession;
        this.oldProfession = oldProfession;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProfessionBase getProfession() {
        return profession;
    }

    public boolean setProfession(Professions professions) {
        ProfessionBase profession = ProfessionManager.getInstance().getProfession(professions);
        long currentTime = System.currentTimeMillis();
        if (profession == null) return false;
        if (professionChangeTime < currentTime) return false;
//        professionChangeTime = currentTime + 43200000L; //12ч
        professionChangeTime = currentTime + 60000;
        this.profession = profession;
        return true;
    }

    public ProfessionBase getOldProfession() {
        return oldProfession;
    }

    public void setOldProfession(ProfessionBase oldProfession) {
        this.oldProfession = oldProfession;
    }
}
