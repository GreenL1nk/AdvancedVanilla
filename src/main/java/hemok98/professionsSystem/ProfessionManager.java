package hemok98.professionsSystem;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import hemok98.professionsSystem.professions.Miner;
import hemok98.professionsSystem.professions.Profession;
import hemok98.professionsSystem.professions.Woodcutter;
import lib.utils.AbstractListener;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfessionManager extends AbstractListener {

    private List<Profession> professionList;
    private static ProfessionManager instance;

    public ProfessionManager(@NotNull JavaPlugin plugin) {
        super(plugin);
        professionList = new ArrayList<>();
        professionList.add(new Miner(true, Material.IRON_PICKAXE, "Шахтёр"));
        professionList.add(new Woodcutter(true, Material.IRON_AXE, "Дровосек"));
        instance = this;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(event.getPlayer().getUniqueId());
        professionAction(event, rpPlayer);
    }

    private void professionAction(Event event, RpPlayer rpPlayer){

        Profession mainProf = rpPlayer.getProfession();
        Profession secondProf = rpPlayer.getOldProfession();
        for (Profession profession : professionList) {
            boolean makeAction = false;

            if ( mainProf != null &&  mainProf.getClass().equals(profession.getClass()) ) {
                mainProf.action(event);
                makeAction = true;
            }
            if ( secondProf != null &&  secondProf.getClass().equals(profession.getClass()) ) {
                secondProf.action(event);
                makeAction = true;
            }
            if (!makeAction) profession.action( event );
        }
    }

    public boolean setPlayerProfession(RpPlayer rpPlayer, Profession settingProf){

        Profession playerProf = rpPlayer.getProfession();
        if (playerProf == null) {
            Profession newProf = professionFactory(settingProf);
            rpPlayer.setProfession(newProf);
            return true;
        } else {

            if (playerProf.getClass().equals( settingProf.getClass() )) {
                return false;
            } else {

                if ( rpPlayer.getProfessionTimeChange() + 12*60*60*1000 < System.currentTimeMillis() ) {
                    if (!playerProf.levelDown()) {
                        rpPlayer.setOldProfession(playerProf);
                    } else rpPlayer.setOldProfession(null);
                    Profession newProf = professionFactory(settingProf);
                    rpPlayer.setProfession(newProf);
                    return true;
                } else return false;

            }

        }
        //return false;
    }

    public List<Profession> getProfessionList() {
        return professionList;
    }

    public static ProfessionManager getInstance() {
        if (instance == null) throw new NullPointerException("Profession Manager is null, it must be initialized before using");
        return instance;
    }

    private Profession professionFactory(Profession profession){
        //todo Refactor to load from Config
        if (profession instanceof Miner) return new Miner(false, Material.IRON_PICKAXE, "Шахтёр");
        if (profession instanceof Woodcutter) return new Miner(false, Material.IRON_AXE, "Дровосек");
        return null;

    }

}
