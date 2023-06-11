package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionBase;

import java.util.UUID;

public class RpPlayer {
    private final UUID uuid;
    private ProfessionBase profession;
    private ProfessionBase old_profession;
    private int money;
    private int pocketMoney;
    private int bankMoney;
    public RpPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profession = null;
        this.old_profession = null;
        money = 100;
        pocketMoney = 100;
    }

    public RpPlayer(UUID uuid, ProfessionBase profession, ProfessionBase old_profession) {
        this.uuid = uuid;
        this.profession = profession;
        this.old_profession = old_profession;
        money = 100;
        pocketMoney = 100;
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
