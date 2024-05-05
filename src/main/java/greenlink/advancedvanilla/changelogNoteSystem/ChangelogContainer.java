package greenlink.advancedvanilla.changelogNoteSystem;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import greenlink.advancedvanilla.AdvancedVanilla;
import greenlink.advancedvanilla.json.Json;
import lib.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChangelogContainer {
    private static ArrayList<ChangelogContainer> changelogContainers = null;
    private String[] info;
    private String date;
    private String subInfo;

    private static HashMap<UUID, Integer> lastChangelogNotifyMap = new HashMap<>();

    public String[] getInfo() {
        return info;
    }

    public String getDate() {
        return date;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public static ArrayList<ChangelogContainer> getСhangelogList(){

        if (changelogContainers == null) loadChanges();
        return changelogContainers;
    }

    public static void loadChanges(){
        try {
            JsonElement jsonElement = Utils.readConfig(AdvancedVanilla.getInstance(), "changelog.json");
            Type type = new TypeToken<ArrayList<ChangelogContainer>>() {}.getType();
            ArrayList<ChangelogContainer> fromFile = Json.GSON.fromJson(jsonElement, type);
            if ( fromFile == null) {
                changelogContainers = new ArrayList<>();
                AdvancedVanilla.getInstance().getLogger().info("changelog.json not found");
            } else {
                changelogContainers = fromFile;
            }
        } catch (Exception e){
            e.printStackTrace();
            changelogContainers = new ArrayList<>();
        }
    }

    public static int getLastChangeNumber(){
        return getСhangelogList().size()-1;
    }

    public static void displayChangesList(Player player){
        if (changelogContainers.size() == 0) return;

        player.sendMessage(Component.text(""));
        player.sendMessage( Component.text("Список изменений:").color(TextColor.color(5636095)) );
        player.sendMessage(Component.text(""));

        for (int i = 0; i < changelogContainers.size(); i++) {
            player.sendMessage( Component.text( "  " + changelogContainers.get(i).date ).color(TextColor.color(5635925)).clickEvent( ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/changelog " + i) ) );
        }
    }

    public static void displayLastChanges(Player player, int lastNotify){
        if (lastNotify >= changelogContainers.size()-1) return;
        if ( changelogContainers.size() == 0) return;
        ChangelogContainer lastChanges = changelogContainers.get(changelogContainers.size() - 1);
        player.sendMessage(Component.text(""));
        player.sendMessage( Component.text("Последние изменения от ").color(TextColor.color(5636095)).append( Component.text( lastChanges.date ).color(TextColor.color(5635925)) ) );
        player.sendMessage(Component.text(""));
        for (String str : lastChanges.info) {
            player.sendMessage( Component.text(str).color(TextColor.color(15658734)) );
        }

        if (lastNotify +1 > changelogContainers.size()-2 ) return;
        player.sendMessage(Component.text(""));
        player.sendMessage( Component.text("Другие изменения:").color(TextColor.color(5636095)) );
        player.sendMessage(Component.text(""));

        for (int i = lastNotify+1; i < changelogContainers.size()-1; i++) {
            player.sendMessage( Component.text( "  " + changelogContainers.get(i).date ).color(TextColor.color(5635925)).clickEvent( ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "/changelog " + i) ) );
        }
    }

    public static void displayChanges(Player player, int number){
        if ( changelogContainers.size() == 0 || number > changelogContainers.size()-1 ) return;

        player.sendMessage(Component.text(""));
        player.sendMessage( Component.text("Изменения от ").color(TextColor.color(5636095)).append( Component.text( changelogContainers.get(number).date ).color(TextColor.color(5635925)) ) );
        player.sendMessage(Component.text(""));
        for (String str : changelogContainers.get(number).info) {
            player.sendMessage( Component.text(str).color(TextColor.color(15658734)) );
        }
    }

    public static int getLastChangelogNotify(UUID uuid) {

        Integer integer = lastChangelogNotifyMap.get(uuid);
        if (integer == null) {

            return -1;
        }
        else return integer;
    }

    public static void setLastChangelogNotify(UUID uuid,  int lastChangelogNotify) {
        lastChangelogNotifyMap.put(uuid, lastChangelogNotify);
    }
}
