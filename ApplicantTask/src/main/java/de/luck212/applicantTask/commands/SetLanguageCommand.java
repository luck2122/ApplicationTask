package de.luck212.applicantTask.commands;

import de.luck212.applicantTask.ApplicantTask;
import de.luck212.applicantTask.scoreboard.ScoreBoard;
import de.luck212.applicantTask.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SetLanguageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        player.openInventory(languageInventory(player));
        return false;
    }

    private Inventory languageInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ApplicantTask.fileUtils.getMessage(player.getUniqueId(),"languageInventoryTitle"));
        ItemStack germanPaper = new ItemBuilder(Material.PAPER).setDisplayName("German").build();
        ItemStack englishPaper = new ItemBuilder(Material.PAPER).setDisplayName("English").build();
        inventory.addItem(germanPaper);
        inventory.addItem(englishPaper);

        new ScoreBoard(player);

        return inventory;
    }
}
