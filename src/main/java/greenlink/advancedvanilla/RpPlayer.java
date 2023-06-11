package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionBase;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.professions.Professions;

import java.util.UUID;

public class RpPlayer {
    private final UUID uuid;
    private ProfessionBase profession;
    private ProfessionBase old_profession;
    private int money;
    private int pocketMoney;
    private int bankMoney;
    private ProfessionBase oldProfession;
    private long professionNextChangeTime;

    public RpPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profession = null;
        this.old_profession = null;
        money = 100;
        pocketMoney = 100;
        this.oldProfession = null;
    }

    public RpPlayer(UUID uuid, ProfessionBase profession, ProfessionBase oldProfession) {
        this.uuid = uuid;
        this.profession = profession;
        this.old_profession = old_profession;
        money = 100;
        pocketMoney = 100;
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
        if (professionNextChangeTime != 0 && professionNextChangeTime < currentTime) return false;
//        professionChangeTime = currentTime + 43200000L; //12ч
        professionNextChangeTime = currentTime + 60000;
        this.profession = profession;
        return true;
    }

    public ProfessionBase getOldProfession() {
        return oldProfession;
    }

    public void setOldProfession(ProfessionBase oldProfession) {
        this.oldProfession = oldProfession;
    }

    public int getMoney() {
        return money;
    }

    public int getPocketMoney() {
        return pocketMoney;
    }

    public int getBankMoney() {
        return bankMoney;
    }

    public boolean takeMoney(int takenMoney){
        if ( this.getMoney() + this.getPocketMoney() - takenMoney < 0 ) return false;

        int temp = Math.min(this.getMoney(), takenMoney);
        this.removeMoney(temp);
        removePocketMoney(takenMoney-temp);
        return true;
    }

    private void removeMoney(int money){
        this.money -= money;
    }

    private void removePocketMoney(int money){
        this.pocketMoney -= money;
    }
}
