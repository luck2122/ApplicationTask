package de.luck212.applicantTask.sql;

import de.luck212.applicantTask.ApplicantTask;

import java.sql.PreparedStatement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//This Class is from a friend of mine
public class AsyncMySQL {
    private ExecutorService executorService;
    private ApplicantTask plugin;
    private MySQL mySQL;

    public AsyncMySQL(ApplicantTask owner, String host, int port, String username, String password, String database) {
        try {
            mySQL = new MySQL(host, port, username, password, database);
            System.out.println("sql connected");
            executorService = Executors.newCachedThreadPool();
            plugin = owner;
        } catch (Exception exception) {
        }
    }

    public void update(String statement) {
        executorService.execute(() -> mySQL.queryUpdate(statement));
    }

    public PreparedStatement prepare(String query) {
        try {
            return mySQL.getConnection().prepareStatement(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}
