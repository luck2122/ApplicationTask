package de.luck212.applicantTask.groups;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.UUID;

public class GroupManager {
    private int groupID;
    private ChatColor color;
    private String playerName;


    /**
     * Constructor for the GroupManager class
     * The Parameter value is the value of the group in the Tablist e.g 0000001 would be on top of the Tablist
     */
    public GroupManager() {
        try {
            if(!exists("Player")) {
                ApplicantTask.asyncMySQL.prepare("INSERT INTO player_groups (group_name, color, value)" +
                        " VALUES ('Player', 'WHITE', '1000000')").executeUpdate();
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String addPlayerToGroup(Player player, String group, int seconds) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT groupID" +
                    " FROM player_groups WHERE group_name = '" + group + "'").executeQuery();
            while (resultSet.next()) {
                groupID = resultSet.getInt("groupID");
            }
            if(isPlayerInGroup(player.getUniqueId(), groupID)) {
                return ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "alreadyInGroup");
            }
            ApplicantTask.asyncMySQL.prepare("UPDATE players SET groupID = '" +
                             groupID + "', expiration_date = '" + seconds + "' WHERE UUID = '" + player.getUniqueId() + "'")
                    .executeUpdate();

            return ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "succesfullyAddedGroup");
        }catch (Exception exception) {
            exception.printStackTrace();
            return ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage");
        }
    }

    public String addPlayerToGroup(Player player, String group) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT groupID" +
                    " FROM player_groups WHERE group_name = '" + group + "'").executeQuery();
            while (resultSet.next()) {
                groupID = resultSet.getInt("groupID");
            }

            if(!exists(groupID)){
                return ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "groupDoesNotExist");
            }

            if(isPlayerInGroup(player.getUniqueId(), groupID)) {
                return ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "alreadyInGroup");
            }

            ApplicantTask.asyncMySQL.prepare("UPDATE players SET groupID = '" +
                            groupID + "', expiration_date = 'lifetime' WHERE UUID = '" + player.getUniqueId() + "'")
                    .executeUpdate();

            return ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "succesfullyAddedGroup");
        }catch (Exception exception) {
            exception.printStackTrace();
            return ChatColor.RED + ApplicantTask.fileUtils.getMessage(player.getUniqueId(), "errorMessage");
        }
    }

    public boolean exists(UUID uuid) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT * FROM players WHERE UUID = '" + uuid + "'").executeQuery();
            return resultSet.next();
        }catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean exists(String group) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT * FROM player_groups" +
                    " WHERE group_name = '" + group + "'").executeQuery();
            return resultSet.next();
        }catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean exists(int groupID) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT * FROM" +
                    " player_groups WHERE groupID = '" + groupID + "'").executeQuery();
            return resultSet.next();
        }catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public ChatColor getColor(String groupName) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT color FROM" +
                    " player_groups WHERE group_name = '" + groupName + "'").executeQuery();
            while (resultSet.next()) {
               color = ChatColor.valueOf(resultSet.getString("color"));
            }
            return color;
        }catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

    }

    public String getString(String table, String column, UUID uuid) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT " + column +" FROM " +
                    table + " WHERE UUID = '" + uuid + "'").executeQuery();
            while(resultSet.next()){
                return resultSet.getString(column);
            }
            return null;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public String getString(String table, String column, int groupID) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT " + column +" FROM " +
                    table + " WHERE groupID = '" + groupID + "'").executeQuery();
            while(resultSet.next()){
                return resultSet.getString(column);
            }
            return null;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public final void setString(UUID uuid, String table, String string, String newString) {
        try {
            ApplicantTask.asyncMySQL.prepare("UPDATE " + table + " SET " + string + " = '" +
                    newString + "' WHERE UUID = '" + uuid + "'").executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public int getInt(String table, String column, UUID uuid) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT " + column +" FROM " +
                    table + " WHERE UUID = '" + uuid + "'").executeQuery();
            while(resultSet.next()){
                return resultSet.getInt(column);
            }
            return -1;
        } catch (Exception exception){
            exception.printStackTrace();
            return -1;
        }
    }

    public int getInt(String table, String column){
        try{
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT " + column + " FROM " + table).executeQuery();
            while(resultSet.next()){
                return resultSet.getInt(column);
            }
            return -1;
        } catch (Exception exception){
            exception.printStackTrace();
            return -1;
        }
    }

    public String removePlayerFromGroup(UUID uuid) {
        try {
            if(isPlayerInGroup(uuid, 1)) {
                return ChatColor.RED + ApplicantTask.fileUtils.getMessage(uuid, "alreadyInGroup");
            }
            ApplicantTask.asyncMySQL.prepare("UPDATE players SET groupID = '1', " +
                    "expiration_date = 'lifetime' WHERE UUID = '" + uuid + "'").executeUpdate();

            return ChatColor.GREEN + ApplicantTask.fileUtils.getMessage(uuid, "succesfullyRemovedGroup");
        }catch (Exception exception) {
            exception.printStackTrace();
            return ChatColor.RED + ApplicantTask.fileUtils.getMessage(uuid, "errorMessage");
        }
    }

    private boolean isPlayerInGroup(UUID uuid, int groupID) {
        try {
            ResultSet resultSet = ApplicantTask.asyncMySQL.prepare("SELECT * FROM players WHERE UUID = '" +
                    uuid + "' AND groupID = '" + groupID + "'").executeQuery();
            return resultSet.next();
        }catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
