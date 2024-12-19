package de.luck212.applicantTask.commands;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateGroupCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.isOp()){
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "noPermission"));
            return false;
        }
        if(strings.length == 3){
            try {
                ApplicantTask.asyncMySQL.prepare("INSERT INTO player_groups (group_name, color, value) VALUES ('" +
                        strings[0] + "', '" + strings[1] + "', '" + strings[2] +"')").executeUpdate();

                player.sendMessage(ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "succesfullyCreatedGroup"));
            }catch (Exception e){
                player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "noPermission"));
                e.printStackTrace();
            }

            return false;
        } else {
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "usageToCreateGroup"));
        }
        return false;
    }
}
