package de.luck212.applicantTask.listener;

import de.luck212.applicantTask.ApplicantTask;
import de.luck212.applicantTask.scoreboard.ScoreBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            try {
                if(!ApplicantTask.groupManager.exists(player.getUniqueId())){
                    ApplicantTask.asyncMySQL.prepare("INSERT INTO players (UUID, groupID, expiration_date, language)" +
                            " VALUES ('" + player.getUniqueId() + "', '" +
                            ApplicantTask.groupManager.getInt("player_groups", "groupID") + "', 'lifetime', 'german')").executeUpdate();


                    ChatColor groupColor = ApplicantTask.groupManager.getColor("Player");
                    setJoinMessage(event, groupColor, player, 1);

                }else {
                    int groupID = ApplicantTask.groupManager.getInt("players", "groupID", player.getUniqueId());
                    String groupName = ApplicantTask.groupManager.getString("player_groups", "group_name", groupID);
                    ChatColor groupColor = ApplicantTask.groupManager.getColor(groupName);


                    setJoinMessage(event, groupColor, player, groupID);
                    new ScoreBoard(player);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    private void setJoinMessage(PlayerJoinEvent event, ChatColor groupColor, Player player, int groupID) {
        event.setJoinMessage(ChatColor.GRAY + "[" + groupColor + ApplicantTask.groupManager.getString("player_groups", "group_name", groupID)
                + ChatColor.GRAY + "] " + groupColor + event.getPlayer().getName()
                + ChatColor.GRAY + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "joinMessage"));
    }
}
