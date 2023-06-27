package greenlink.advancedvanilla.professions;


import greenlink.advancedvanilla.RpPlayer;
import greenlink.advancedvanilla.professions.requirements.Requirement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class ProfessionBase implements Listener {
    private final String name;
    private int numberCurrentLevel;
    private final Level[] levels;
    private RpPlayer rpPlayer;

    public ProfessionBase(String name, Level ... levels) {
        this.name = name;
        this.levels = levels;
        this.rpPlayer = null;
    }

    public String getName() {
        return name;
    }

    public int getNumberCurrentLevel() {
        return numberCurrentLevel;
    }

    public void setNumberCurrentLevel(int numberCurrentLevel) {
        this.numberCurrentLevel = numberCurrentLevel;
    }

    public Level[] getLevels() {
        return levels;
    }

    public Level getCurrentLevel() {
        return levels[numberCurrentLevel];
    }

    public void levelUP() {
        this.numberCurrentLevel++;
        getRpPlayer().getPlayer().sendMessage(Component
                .text("Ваш уровень профессии был повышен до ", NamedTextColor.GRAY)
                .append(Component.text(numberCurrentLevel, NamedTextColor.GREEN)));
    }

    public Requirement[] getCurrentRequirements() {
        if (levels.length < numberCurrentLevel) return new Requirement[0];
        return levels[numberCurrentLevel].requirements();
    }

    @Nullable
    public RpPlayer getRpPlayer() {
        return rpPlayer;
    }

    public void processRequirements(Object object) {
        if (rpPlayer != null
                && rpPlayer.getProfession() != null) {
            Arrays.stream(rpPlayer.getProfession().getCurrentRequirements()).forEach(requirement -> requirement.isRequirement(object, rpPlayer));
        }
    }

    public void setRpPlayer(RpPlayer rpPlayer) {
        this.rpPlayer = rpPlayer;
    }
}
