package hemok98.professionsSystem.professions;

import hemok98.professionsSystem.Requirement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public abstract class Profession {
    protected List<List<Requirement>> requirements;
    protected Material displayedItem;
    protected String name;
    protected int level;
    protected boolean isFrozen;

    public List<List<Requirement>> getRequirements() {
        return requirements;
    }

    public Material getDisplayedItem() {
        return displayedItem;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public boolean levelDown(){
        if (level > 0) level--;
        return level == 0;
    }

    public abstract void action(Event event);

    protected void requirementInfo(Requirement requirement, Player player){
        player.sendMessage(
                Component.text("\n" + requirement.getDescription() + ": ").color(TextColor.color(11184810)  ).
                        append( Component.text( requirement.getProgress() ).color(TextColor.color(9290582) ) ).
                        append( Component.text( "/" ).color(TextColor.color(11184810) ) ).
                        append( Component.text( requirement.getProgress() ).color(TextColor.color(9290582) ) )
        );
    }
}
