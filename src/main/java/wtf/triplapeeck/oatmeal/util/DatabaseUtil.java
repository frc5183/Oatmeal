package wtf.triplapeeck.oatmeal.util;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.sql.SQLException;

public class DatabaseUtil {
    private JdbcPooledConnectionSource conncectionSource;
    private ConfigParser.DatabaseConfiguration databaseConfiguration;
    public DatabaseUtil() throws SQLException {
        this.databaseConfiguration = ConfigParser.getDatabaseConfiguration();
        this.conncectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + this.databaseConfiguration.getAddress() + ":" +this.databaseConfiguration.getPort() + "/" + this.databaseConfiguration.getDatabase() + "?user="+ this.databaseConfiguration.getUsername() + "&password=" + this.databaseConfiguration.getPassword(), this.databaseConfiguration.getType());
        this.conncectionSource.setMaxConnectionsFree(this.databaseConfiguration.getMaxConnections());
    }

    public JdbcPooledConnectionSource getConncectionSource() {
        return conncectionSource;
    }

    public ConfigParser.DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }
}
