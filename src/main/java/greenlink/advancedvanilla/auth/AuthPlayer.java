package greenlink.advancedvanilla.auth;

import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.discord.DiscordManager;
import lib.utils.TimeUtils;
import lib.utils.Utils;

public class AuthPlayer {
    private final RpPlayer rpPlayer;
    private boolean isLinked = false;
    private long sessionExpireTime;
    private String address;
    private String code;
    private long codeExpireTime;
    private long discordID;

    public AuthPlayer(RpPlayer rpPlayer) {
        this.rpPlayer = rpPlayer;
    }

    public AuthPlayer(RpPlayer rpPlayer, String address, long discordID) {
        this.rpPlayer = rpPlayer;
        this.address = address;
        this.discordID = discordID;
    }

    public RpPlayer getRpPlayer() {
        return rpPlayer;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean isLinked) {
        this.isLinked = isLinked;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode(String inetAddress) {
        long currentTime = System.currentTimeMillis();
        if (codeExpireTime < currentTime || code == null) {
            String stringCode = Utils.generateRandomCode();
            while (DiscordManager.getInstance().getMap().containsKey(stringCode)) {
                stringCode = Utils.generateRandomCode();
            }
            code = stringCode;
            codeExpireTime = currentTime + TimeUtils.convertMinutesToMillis(15);
            address = inetAddress;
            DiscordManager.getInstance().getMap().put(code, this);
        }
        return code;
    }

    public long getDiscordID() {
        return discordID;
    }

    public void link(long discordID) {
        isLinked = true;
        this.discordID = discordID;
    }
}
