package greenlink.advancedvanilla.tradeSystem;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import lib.utils.AbstractListener;
import lib.utils.MyObservable;
import lib.utils.MyObserver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

/**
 * Описывает поведение вывода информации в sidebar scoreboard игроку
 */
public class SidebarInfoSystem extends AbstractListener implements MyObserver {
    private static SidebarInfoSystem instance;

    public SidebarInfoSystem(@NotNull JavaPlugin plugin) {
        super(plugin);
        instance = this;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if ( PlayerManager.getInstance().getPlayer(player.getUniqueId()).isDisplaySidebarInfo() ) addSidebarToPlayer(player);
        }
    }

    /**
     * @param player игрок которому нужно отобразить информацию
     */
    public static void addSidebarToPlayer( Player player ){
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        Scoreboard playerScoreBoard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(playerScoreBoard);
        Objective objective = playerScoreBoard.registerNewObjective("profile", Criteria.DUMMY, Component.text(player.getName()).color(TextColor.color(972270)));
        objective.setDisplaySlot( DisplaySlot.SIDEBAR );
        {
            Score moneyScore = objective.getScore(" ");
            moneyScore.setScore(9);
        }

        {
            Team tempTeam = playerScoreBoard.registerNewTeam("money");
            tempTeam.color(NamedTextColor.GRAY);
            tempTeam.suffix( Component.text(rpPlayer.getBankMoney()).color(TextColor.color(9290582)) );
            tempTeam.addEntry("Деньги: ");
            Score moneyScore = objective.getScore("Деньги: ");
            moneyScore.setScore(8);
        }

        {
            Team tempTeam = playerScoreBoard.registerNewTeam("pocketMoney");
            tempTeam.color(NamedTextColor.GRAY);
            tempTeam.suffix( Component.text(rpPlayer.getPocketMoney()).color(TextColor.color(9290582)) );
            tempTeam.addEntry("Кошелёк: ");
            Score moneyScore = objective.getScore("Кошелёк: ");
            moneyScore.setScore(7);
        }
        {
            Team tempTeam = playerScoreBoard.registerNewTeam("profession");
            tempTeam.color(NamedTextColor.GRAY);
            if (rpPlayer.getProfession() != null) {
                tempTeam.suffix( Component.text(rpPlayer.getProfession().getName()).color(TextColor.color(9290582)) );
            } else tempTeam.suffix( Component.text("Не выбрана").color(TextColor.color(9290582)) );
            tempTeam.addEntry("Профессия: ");
            Score moneyScore = objective.getScore("Профессия: ");
            moneyScore.setScore(6);
        }

        rpPlayer.addObserver(instance);
    }

    /**
     * Обновляет данные в sidebar после тригерного изменения
     * @param observable игрок чьи данные изменились
     */
    @Override
    public void onUpdate(MyObservable observable) {
        RpPlayer rpPlayer = (RpPlayer) observable;
        if ( !rpPlayer.isDisplaySidebarInfo()) return;
        Player player = Bukkit.getServer().getPlayer(rpPlayer.getUuid());
        if (player == null) return;
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("profile");
        if (objective == null) return;

        Team pocketMoney = scoreboard.getTeam("pocketMoney");
        if (pocketMoney != null) {
            pocketMoney.suffix( Component.text(rpPlayer.getPocketMoney()).color(TextColor.color(9290582)) );
        }

        Team money = scoreboard.getTeam("money");
        if (money != null) {
            money.suffix( Component.text(rpPlayer.getBankMoney()).color(TextColor.color(9290582)) );
        }

        Team profession = scoreboard.getTeam("profession");
        if (profession != null) {
            if (rpPlayer.getProfession() != null) {
                profession.suffix( Component.text(rpPlayer.getProfession().getName()).color(TextColor.color(9290582)) );
            } else profession.suffix( Component.text("Не выбрана").color(TextColor.color(9290582)) );
        }
    }

    /**
     * Проверка при входе игрока нужно ли ему вывести информацию в sidebar
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if ( PlayerManager.getInstance().getPlayer(player.getUniqueId()).isDisplaySidebarInfo() ) addSidebarToPlayer(player);
    }

    /**
     * Singleton
     */
    public static SidebarInfoSystem getInstance() { return instance;}

    /**
     * Очищение игрока из списка тех кому надо выводить информацию при его ливе с сервера
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        rpPlayer.deleteObserver(this);
    }

    @EventHandler
    public void test(PlayerCommandPreprocessEvent event){
        if (event.getMessage().endsWith("hemok")) {

        }
    }

}
