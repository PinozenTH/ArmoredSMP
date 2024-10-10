package com.pinont.armoredsmp.scoreboard;

import com.pinont.armoredsmp.Core;
import com.pinont.piXLib.api.scoreboard.Board;
import com.pinont.piXLib.api.utils.Common;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class Health {

    private static final Core plugin = Core.getInstance();

    private static final Scoreboard healthScoreboard = createHealth();

    public static Scoreboard createHealth() {
        Scoreboard scoreboard;
        if (Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard().getObjective("asmp_health") != null) {
            return plugin.getServer().getScoreboardManager().getMainScoreboard();
        }
        scoreboard = new Board("asmp_health", Criteria.DUMMY, Common.colorize("&c❤")).setDisplaySlot(DisplaySlot.PLAYER_LIST).setRenderType(RenderType.HEARTS).getScoreboard();
        return  scoreboard;
    }

    public static void setHealthScore(Player player) {
        if (player.getScoreboard().getObjective("asmp_health") == null && plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("asmp_health") != null) {
            Objects.requireNonNull(player.getScoreboard().getObjective("asmp_health")).setDisplaySlot(DisplaySlot.BELOW_NAME);
            player.setScoreboard(healthScoreboard);
            return;
        }
        updateHealth(player, player.getHealth());
    }

    public static void updateHealth(Player player, double health) {
        new Board("asmp_health").setPlayer(player).setScore(health);

    }

    public static void damageHealth(Player player, double damage) {
        // calculate new health
    }
}
