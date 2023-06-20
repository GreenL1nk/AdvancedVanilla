package greenlink.advancedvanilla.tradeSystem;

import greenlink.advancedvanilla.PlayerManager;
import greenlink.advancedvanilla.RpPlayer;
import lib.utils.AbstractListener;
import lib.utils.MyObservable;
import lib.utils.MyObserver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

public class TestListener extends AbstractListener implements MyObserver {
    public TestListener(@NotNull JavaPlugin plugin) {
        super(plugin);
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
            Scoreboard playerScoreBoard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
            player.setScoreboard(playerScoreBoard);
            Objective objective = playerScoreBoard.registerNewObjective("Money", Criteria.DUMMY, Component.text(player.getName()).color(TextColor.color(972270)));
            objective.setDisplaySlot( DisplaySlot.SIDEBAR );
            Score moneyScore = objective.getScore("Money: ");
            moneyScore.setScore(rpPlayer.getMoney());
            Score pocketMoneyScore = objective.getScore("Pocket Money: ");
            pocketMoneyScore.setScore(rpPlayer.getPocketMoney());
            rpPlayer.addObserver(this);
        }
    }

    @Override
    public void onUpdate(MyObservable observable) {
        RpPlayer rpPlayer = (RpPlayer) observable;
        Player player = Bukkit.getServer().getPlayer(rpPlayer.getUuid());
        if (player == null) return;
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Money");
        if (objective == null) return;
        Score moneyScore = objective.getScore("Money: ");
        moneyScore.setScore(rpPlayer.getMoney());
        Score pocketMoneyScore = objective.getScore("Pocket Money: ");
        pocketMoneyScore.setScore(rpPlayer.getPocketMoney());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        Scoreboard playerScoreBoard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(playerScoreBoard);
        Objective objective = playerScoreBoard.registerNewObjective("Money", Criteria.DUMMY, Component.text(player.getName()).color(TextColor.color(972270)));
        objective.setDisplaySlot( DisplaySlot.SIDEBAR );
        Score moneyScore = objective.getScore("Money: ");
        moneyScore.setScore(rpPlayer.getMoney());
        Score pocketMoneyScore = objective.getScore("Pocket Money: ");
        pocketMoneyScore.setScore(rpPlayer.getPocketMoney());
        rpPlayer.addObserver(this);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        RpPlayer rpPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        rpPlayer.deleteObserver(this);
    }

}
