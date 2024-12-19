package de.luck212.applicantTask.scheduler;

import de.luck212.applicantTask.ApplicantTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RankScheduler {

    private final ApplicantTask plugin;
    private boolean isRunning;
    private int sec;
    private int taskID;
    private final Pattern pattern;
    private Matcher matcher;
    private boolean matchFound;


    /**
     * Constructor for RankScheduler
     * @param plugin to start the Bukkit Scheduler
     * Only works if the Server is running and the Player is online
     */
    public RankScheduler(ApplicantTask plugin) {
        this.plugin = plugin;
        isRunning = false;
        sec = 0;
        pattern = Pattern.compile("^[a-zA-Z]");
    }

    public void startUpdateRank() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                try {
                    if(sec == 120) {
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            String secString = ApplicantTask.groupManager.getString("players", "expiration_date", player.getUniqueId());
                            matcher = pattern.matcher(secString);
                            matchFound = matcher.find();
                            if(matchFound)
                                continue;
                            int secUpdate = Integer.parseInt(secString) - sec;

                            if(secUpdate <= 0) {
                                int groupID = ApplicantTask.groupManager.getInt("player_groups", "groupID");
                                ApplicantTask.asyncMySQL.prepare("UPDATE players SET groupID" +
                                        " = '" + groupID +"' WHERE UUID = '" + player.getUniqueId() + "'").executeUpdate();
                            }else {
                                ApplicantTask.asyncMySQL.prepare("UPDATE players SET expiration_date" +
                                        " = '" + secUpdate + "' WHERE UUID = '" + player.getUniqueId() + "'").executeUpdate();
                            }
                            sec = 0;
                        }
                    }
                    sec++;
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 0, 20);
    }
    public void stopUpdateRank() {
        isRunning = false;
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
