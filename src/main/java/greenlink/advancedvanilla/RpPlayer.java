package greenlink.advancedvanilla;

import greenlink.advancedvanilla.compasSystem.Compass;
import greenlink.advancedvanilla.professions.ProfessionBase;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.professions.Professions;
import lib.utils.MyObservable;

import javax.annotation.Nullable;
import java.util.UUID;

public class RpPlayer extends MyObservable {
    private final UUID uuid;
    private ProfessionBase profession;
    private int money;
    private int pocketMoney;
    private int bankMoney;
    private ProfessionBase oldProfession;
    private long professionNextChangeTime;
    private Compass[] compasses;
    private int activeCompass;

    public RpPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profession = null;
        money = 100;
        pocketMoney = 100;
        this.oldProfession = null;
        compasses = new Compass[3];
        activeCompass = -1;
    }

    public RpPlayer(UUID uuid, ProfessionBase profession, ProfessionBase oldProfession) {
        this.uuid = uuid;
        this.profession = profession;
        money = 100;
        pocketMoney = 100;
        this.oldProfession = oldProfession;
        activeCompass = -1;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Nullable
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
        if (this.profession != null) setOldProfession(profession);
        this.profession = profession;
        this.profession.setRpPlayer(this);
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

    public boolean takeMoney(int takenMoney) {
        if (this.getMoney() + this.getPocketMoney() - takenMoney < 0) return false;

        int temp = Math.min(this.getMoney(), takenMoney);
        this.removeMoney(temp);
        removePocketMoney(takenMoney - temp);
        notifyObservers();
        return true;
    }

    private void removeMoney(int money) {
        this.money -= money;
        notifyObservers();
    }

    private void removePocketMoney(int money) {
        this.pocketMoney -= money;
        notifyObservers();
    }

    public void addMoney(int money){
        this.money+=money;
        notifyObservers();
    }

    public Compass[] getCompasses() {
        return compasses;
    }

    public int getActiveCompass() {
        return activeCompass;
    }

    public void setActiveCompass(int activeCompass) {
        this.activeCompass = activeCompass;
    }
}
