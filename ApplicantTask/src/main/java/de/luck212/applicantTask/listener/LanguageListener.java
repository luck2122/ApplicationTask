package de.luck212.applicantTask.listener;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LanguageListener implements Listener {

    @EventHandler
    public void onLanguageChange(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!(event.getView().getTitle().equalsIgnoreCase(ApplicantTask.fileUtils.getMessage(player.getUniqueId(),"languageInventoryTitle")))){
            return;
        }
        event.setCancelled(true);

        String currentItemName = event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName();

        if(currentItemName.equalsIgnoreCase("german")){
            updateLanguage(player, "german");
        } else if(currentItemName.equalsIgnoreCase("english")) {
            updateLanguage(player, "english");
        }
    }


    private void updateLanguage(Player player, String language) {
        ApplicantTask.groupManager.setString(player.getUniqueId(), "players", "language", language);

        player.sendMessage(ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "changedLanguage"));
        player.closeInventory();
    }
}
