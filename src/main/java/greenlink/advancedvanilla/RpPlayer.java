package greenlink.advancedvanilla;

import greenlink.advancedvanilla.compasSystem.Compass;
import hemok98.professionsSystem.professions.Profession;
import lib.utils.MyObservable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class RpPlayer extends MyObservable {
    private final UUID uuid;
    private Profession profession;
    private int pocketMoney;
    private int bankMoney;
    private Profession oldProfession;
    private long professionTimeChange;
    Player player;
    private Compass[] compasses;
    private int activeCompass;
    private boolean displaySidebarInfo;

    public RpPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profession = null;
        pocketMoney = 0;
        bankMoney = 0;
        this.oldProfession = null;
        compasses = new Compass[3];
        activeCompass = -1;
        this.displaySidebarInfo = true;
        professionTimeChange = 0;
    }

    public void setProfessionTimeChange(long professionTimeChange) {
        this.professionTimeChange = professionTimeChange;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getProfessionTimeChange() {
        return professionTimeChange;
    }

    @Nullable
    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
        this.professionTimeChange = System.currentTimeMillis();
        notifyObservers();
    }

    @Nullable
    public Profession getOldProfession() {
        return oldProfession;
    }

    public void setOldProfession(Profession oldProfession) {

        this.oldProfession = oldProfession;
    }

    public int getPocketMoney() {
        return pocketMoney;
    }

    public int getBankMoney() {
        return bankMoney;
    }

    public boolean takeMoney(int takenMoney) {
        if (this.getPocketMoney() + this.getBankMoney() - takenMoney < 0) return false;

        int temp = Math.min(this.getPocketMoney(), takenMoney);
        this.removeMoney(temp);
        removePocketMoney(takenMoney - temp);
        notifyObservers();
        return true;
    }

    private void removeMoney(int money) {
        this.pocketMoney -= money;
        notifyObservers();
    }

    private void removePocketMoney(int money) {
        this.bankMoney -= money;
        notifyObservers();
    }

    public void addMoney(int money){
        this.pocketMoney +=money;
        notifyObservers();
    }

    @Nullable
    public Player getPlayer() {
        if (player == null) player = Bukkit.getPlayer(uuid);
        return player;
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

    public boolean isDisplaySidebarInfo() {
        return displaySidebarInfo;
    }

    public void setDisplaySidebarInfo(boolean displaySidebarInfo) {
        this.displaySidebarInfo = displaySidebarInfo;
    }

    public void setPocketMoney(int pocketMoney) {
        this.pocketMoney = pocketMoney;
        notifyObservers();
    }

    public void setBankMoney(int bankMoney) {
        this.bankMoney = bankMoney;
        notifyObservers();
    }
}
