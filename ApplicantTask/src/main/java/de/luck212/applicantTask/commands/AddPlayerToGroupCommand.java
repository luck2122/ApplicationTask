package de.luck212.applicantTask.commands;

import de.luck212.applicantTask.ApplicantTask;
import de.luck212.applicantTask.scoreboard.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddPlayerToGroupCommand implements CommandExecutor {
    private final Pattern pattern;

    public AddPlayerToGroupCommand() {
        this.pattern = Pattern.compile("^[a-zA-Z]");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(!player.isOp()){
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "noPermission"));
            return false;
        }

        if(strings.length == 6){
            try {
                for(int i = 0; i < strings.length; i++){
                    if(i == 0 || i == 1) continue;
                    Matcher matcher = pattern.matcher(strings[i]);
                    boolean matchFound = matcher.find();
                    if(matchFound) {
                        player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "usageToAddPlayerToGroup"));
                        return false;
                    }
                }
                Player addPlayer = Bukkit.getPlayer(strings[0]);
                int sec = changeTimeToSec(strings);
                player.sendMessage(ApplicantTask.groupManager.addPlayerToGroup(addPlayer, strings[1], sec));

                new ScoreBoard(addPlayer);
            }catch (Exception e){
                player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage"));
                e.printStackTrace();
            }

            return false;
        } else if(strings.length == 2){
            try{
                Player addPlayer = Bukkit.getPlayer(strings[0]);
                player.sendMessage(ApplicantTask.groupManager.addPlayerToGroup(addPlayer, strings[1]));

                new ScoreBoard(addPlayer);
            }catch (Exception e){
                player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage"));
                e.printStackTrace();
            }
        }else {
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "usageToAddPlayerToGroup"));
        }
        return false;
    }

        public int changeTimeToSec(String[] strings) {
            return (((24*Integer.parseInt(strings[2]))*60)*60)
            + ((Integer.parseInt(strings[3])*60)*60)
            + ((Integer.parseInt(strings[4])*60))
                    + Integer.parseInt(strings[5]);
        }
}
