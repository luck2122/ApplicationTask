package de.luck212.applicantTask.utils;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class FileUtils {

    public File folder = new File("plugins/ApplicantTask");
    public File textGerman = new File(folder, "textGerman.yml");
    public File textEnglish = new File(folder, "textEnglish.yml");

    public FileUtils() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!textGerman.exists()) {
            try {
                textGerman.createNewFile();
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(textGerman);
                fileConfiguration.addDefault("joinMessage", " hat den Server betreten.");
                fileConfiguration.addDefault("noPermission", " Dazu hast du keine Rechte.");
                fileConfiguration.addDefault("errorMessage", " Es ist ein Fehler aufgetreten.");
                fileConfiguration.addDefault("usageToCreateGroup", " Bitte benutze /creategroup <Gruppenname> <Farbe> <Wert>.");
                fileConfiguration.addDefault("changedLanguage", " Du hast deine Sprache zu deutsch gewechselt.");
                fileConfiguration.addDefault("scoreBoardTop", " Dein Rang:");
                fileConfiguration.addDefault("succesfullyCreatedGroup", " Du hast erfolgreich die Gruppe erstellt.");
                fileConfiguration.addDefault("usageToRemovePlayerFromGroup", "Bitte benutze /remove <Spielername>.");
                fileConfiguration.addDefault("succesfullyRemovedGroup", "Du hast erfolgreich den Spieler aus der Gruppe entfern.");
                fileConfiguration.addDefault("succesfullyAddedGroup", "Du hast den Spieler erfolgreich zur Gruppe hinzugefügt.");
                fileConfiguration.addDefault("alreadyInGroup", "Dieser Spieler ist bereits in dieser Gruppe.");
                fileConfiguration.addDefault("languageInventory", "Waehle eine Sprache.");
                fileConfiguration.addDefault("rankMessage1", "Du hast den Rang: ");
                fileConfiguration.addDefault("rankMessagePermanent", "Dieser Rang ist ");
                fileConfiguration.addDefault("rankMessageNonPermanent", "Die Laufzeit des Ranges betraegt ");
                fileConfiguration.addDefault("groupDoesNotExist", "Diese Gruppe existiert nicht.");
                fileConfiguration.addDefault("succesfullyChangedGroup", "Erfolgreich Gruppe geaendert.");
                fileConfiguration.addDefault("languageInventoryTitle", "Sprache auswaehlen.");
                fileConfiguration.addDefault("usageToChangeGroup", "Bitte benutze /change <Gruppenname> <Neuer Gruppen name> <Neue Farbe> <Neuer Wert>.");
                fileConfiguration.addDefault("usageToAddPlayerToGroup", "Bitte benutze /addgroup <Spieler name>" +
                        " <Gruppen Name> <Tage> <Stunden> <Minuten> <Sekunden>.\n" + "Oder /addgroup <Spieler name> <Gruppen name> lifetime");
                fileConfiguration.options().copyDefaults(true);
                fileConfiguration.save(textGerman);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if (!textEnglish.exists()) {
            try {
                textEnglish.createNewFile();
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(textEnglish);
                fileConfiguration.addDefault("joinMessage", " has joined the server");
                fileConfiguration.addDefault("noPermission", " You don't have permission to use this command.");
                fileConfiguration.addDefault("errorMessage", " An error occurred while connecting to the database.");
                fileConfiguration.addDefault("usageToCreateGroup", " Please use /creategroup <group name> <color> <value>.");
                fileConfiguration.addDefault("changedLanguage", " You changed your language to english.");
                fileConfiguration.addDefault("scoreBoardTop", " Your Rank:");
                fileConfiguration.addDefault("succesfullyCreatedGroup", " You have succesfully created the Group.");
                fileConfiguration.addDefault("usageToRemovePlayerFromGroup", " Please use /remove <Playername>.");
                fileConfiguration.addDefault("succesfullyRemovedGroup", "You succesfully removed the Player from the Group.");
                fileConfiguration.addDefault("succesfullyAddedGroup", "You succesfully added the Player to the Group.");
                fileConfiguration.addDefault("alreadyInGroup", "This Player is already in the Group.");
                fileConfiguration.addDefault("languageInventory", "Select a language.");
                fileConfiguration.addDefault("groupDoesNotExist", "This Group does not exist.");
                fileConfiguration.addDefault("rankMessage1", "You have the rank: ");
                fileConfiguration.addDefault("rankMessagePermanent", "This rank is ");
                fileConfiguration.addDefault("rankMessageNonPermanent", "This rank lasts for ");
                fileConfiguration.addDefault("languageInventoryTitle", "Select a language.");
                fileConfiguration.addDefault("usageToChangeGroup", "Please use /change <Group name> <New Group name> <New color> <New value>.");
                fileConfiguration.addDefault("succesfullyChangedGroup", "Succesfully changed group.");
                fileConfiguration.addDefault("usageToAddPlayerToGroup", "Please use /addgroup <Player name> <Group Name>" +
                        " <Days> <Hours> <Minutes> <Seconds>.\n" + "Or use /addgroup <Spieler name> <Gruppen name> lifetime");
                fileConfiguration.options().copyDefaults(true);
                fileConfiguration.save(textEnglish);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public String getMessage(UUID uuid, String confMessage) {
        String languageString = ApplicantTask.groupManager.getString("players", "language", uuid);
         if(languageString.equalsIgnoreCase("German")) {
             return germanConf(confMessage);
         }else if(languageString.equalsIgnoreCase("English")) {
             return englishConf(confMessage);
         }
         //Can be optimized, but is currently hardcode due to the small example
        return null;
    }

    private String englishConf(String confMessage) {
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(textEnglish);
        return fileConfiguration.getString(confMessage);
    }

    private String germanConf(String confMessage) {
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(textGerman);
        return fileConfiguration.getString(confMessage);
    }

}
