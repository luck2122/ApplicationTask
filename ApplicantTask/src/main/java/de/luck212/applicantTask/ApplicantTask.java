package de.luck212.applicantTask;

import de.luck212.applicantTask.commands.*;
import de.luck212.applicantTask.groups.GroupManager;
import de.luck212.applicantTask.listener.ChatListener;
import de.luck212.applicantTask.listener.LanguageListener;
import de.luck212.applicantTask.listener.PlayerJoinListener;
import de.luck212.applicantTask.scheduler.RankScheduler;
import de.luck212.applicantTask.sql.AsyncMySQL;
import de.luck212.applicantTask.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApplicantTask extends JavaPlugin {
    private final String DB_URL = "";
    private final String DB_USER = "";
    private final String DB_PASSWORD = "";
    public static AsyncMySQL asyncMySQL;
    public static GroupManager groupManager;
    public static FileUtils fileUtils;
    public RankScheduler rankScheduler;

    @Override
    public void onEnable() {
        asyncMySQL = new AsyncMySQL(this, DB_URL, 3306, DB_USER, DB_PASSWORD, "");
        createTables();
        fileUtils = new FileUtils();
        rankScheduler = new RankScheduler(this);
        rankScheduler.startUpdateRank();
        groupManager = new GroupManager();


        PluginManager pluginManager = Bukkit.getPluginManager();


        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new LanguageListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);

        getCommand("creategroup").setExecutor(new CreateGroupCommand());
        getCommand("language").setExecutor(new SetLanguageCommand());
        getCommand("remove").setExecutor(new RemovePlayerFromGroupCommand());
        getCommand("addgroup").setExecutor(new AddPlayerToGroupCommand());
        getCommand("rang").setExecutor(new RankCommand());
        getCommand("change").setExecutor(new ChangeGroupCommand());
    }

    private void createTables(){
        try {
            asyncMySQL.update("CREATE TABLE IF NOT EXISTS player_groups (" +
                    "    groupID INT AUTO_INCREMENT PRIMARY KEY," +
                    "    group_name VARCHAR(20) NOT NULL UNIQUE," +
                    "    color VARCHAR(20)," +
                    "    value INT)");


            asyncMySQL.update("CREATE TABLE IF NOT EXISTS players (" +
                    "UUID VARCHAR(255) PRIMARY KEY UNIQUE," +
                    "groupID INT," +
                    "language VARCHAR(25)," +
                    "expiration_date VARCHAR(255))");
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        asyncMySQL.getMySQL().closeConnection();
        rankScheduler.stopUpdateRank();
    }
}
