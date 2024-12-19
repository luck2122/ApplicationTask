package de.luck212.applicantTask.commands;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RankCommand implements CommandExecutor {
    private final Pattern pattern;
    private Matcher matcher;

    public RankCommand() {
        this.pattern = Pattern.compile("^[a-zA-Z]");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.isOp()){
            player.sendMessage(ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "noPermission"));
            return false;
        }
        if(strings.length == 0) {
            try{
                String secString = ApplicantTask.groupManager.getString("players", "expiration_date", player.getUniqueId());
                matcher = pattern.matcher(secString);
                boolean matchFound = matcher.find();
                int groupID = ApplicantTask.groupManager.getInt("players", "groupID", player.getUniqueId());


                String groupName = ApplicantTask.groupManager.getString("player_groups", "group_name", groupID);
                ChatColor color = ApplicantTask.groupManager.getColor(groupName);
                String message1 = ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "rankMessage1");


                if(matchFound) {
                    player.sendMessage(message1 + color + groupName + " " + ChatColor.GRAY +
                            ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "rankMessagePermanent") + " lifetime");
                    return false;
                }


                int sec = ApplicantTask.groupManager.getInt("players", "expiration_date", player.getUniqueId());
                int hours = Math.round((float) sec / 3600);
                int minutes = Math.round((float) (sec % 3600) / 60);
                int seconds = Math.round((float) (sec % 3600) % 60);
                player.sendMessage(message1 + color + groupName + " "+ ChatColor.GRAY +
                        ApplicantTask.fileUtils.getMessage(player.getUniqueId(),
                                "rankMessageNonPermanent") + hours + "h " + minutes + "m " + seconds + "s");

            }catch (Exception e){
                player.sendMessage(ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage"));
                e.printStackTrace();
            }
        }
        return false;
    }
}
