package de.luck212.applicantTask.scoreboard;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoard{
    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;


    public ScoreBoard(Player player) {
        this.player = player;
        if(player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        this.scoreboard = player.getScoreboard();

        if(this.scoreboard.getObjective("display") != null) {
            this.scoreboard.getObjective("display").unregister();
        }

        this.objective = this.scoreboard.registerNewObjective("display", "dummy", "Applicant Task");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        createScoreBoard();
    }


    public void createScoreBoard() {
        setScore(ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "scoreBoardTop"), 12);
        setScore(createScoreBoardScore(), 11);
    }

    private String createScoreBoardScore() {
        int groupID = ApplicantTask.groupManager.getInt("players", "groupID", player.getUniqueId());
        String groupName = ApplicantTask.groupManager.getString("player_groups", "group_name", groupID);
        ChatColor color = ApplicantTask.groupManager.getColor(groupName);
        return color + groupName;
    }

    public void setScore(String scoreName, int score) {
        this.objective.getScore(scoreName).setScore(score);
    }
}
