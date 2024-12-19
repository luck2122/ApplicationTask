package de.luck212.applicantTask.listener;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener() {
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        try {
            Player player = event.getPlayer();
            int groupID = ApplicantTask.groupManager.getInt("players", "groupID", player.getUniqueId());
            String groupName = ApplicantTask.groupManager.getString("player_groups", "group_name", groupID);
            ChatColor groupColor = ApplicantTask.groupManager.getColor(groupName);


            event.setFormat(ChatColor.GRAY + "[" + groupColor + groupName + ChatColor.GRAY + "] "
                    + groupColor + event.getPlayer().getName() + ChatColor.GRAY + ": " + event.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
