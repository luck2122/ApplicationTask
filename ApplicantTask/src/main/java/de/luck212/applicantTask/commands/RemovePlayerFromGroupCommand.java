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

public class RemovePlayerFromGroupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if(!player.isOp()){
            player.sendMessage(ChatColor.RED +ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "noPermission"));
            return false;
        }

        if(strings.length == 1){
            try {
                Player removePlayer = Bukkit.getPlayer(strings[0]);
                ApplicantTask.groupManager.removePlayerFromGroup(removePlayer.getUniqueId());
                player.sendMessage(ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "succesfullyRemovedGroup"));

                new ScoreBoard(removePlayer);
            }catch (Exception e){
                player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage"));
                e.printStackTrace();
            }

            return false;
        } else {
            player.sendMessage(ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "usageToRemovePlayerFromGroup"));
        }

        return false;
    }
}
