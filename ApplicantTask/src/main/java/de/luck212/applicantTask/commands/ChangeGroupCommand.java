package de.luck212.applicantTask.commands;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChangeGroupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(!player.isOp()){
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "noPermission"));
            return false;
        }

        if(strings.length != 4) {
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "usageToChangeGroup"));
            return false;
        } else {
            try{
                String newGroupName = strings[1];
                String newColor = strings[2];
                String newValue = strings[3];

                ApplicantTask.asyncMySQL.prepare("UPDATE player_groups SET group_name = '" + newGroupName +
                        "', color = '" + newColor + "', value = '" + newValue + "' WHERE group_name = '" + strings[0] + "'").executeUpdate();

                player.sendMessage(ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "succesfullyChangedGroup"));
            }catch (Exception e) {
                player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage"));
                e.printStackTrace();
            }
        }
        return false;
    }
}
